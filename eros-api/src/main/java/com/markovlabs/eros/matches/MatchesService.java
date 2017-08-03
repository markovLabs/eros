package com.markovlabs.eros.matches;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.DatingMapping.DATING_MAPPING;
import static com.markovlabs.eros.model.tables.EventStories.EVENT_STORIES;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jooq.DSLContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.markovlabs.eros.matches.Matches.Match;
import com.markovlabs.eros.model.enums.EventStoriesLabel;
import com.markovlabs.eros.model.tables.records.EventStoriesRecord;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Try;

public final class MatchesService {
	
	private final DSLContext erosDb;

	public MatchesService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public Matches getMatches(Long mappingId, List<Long> daterIds, long eventId) {
		String mapping = getMatchMapping(mappingId).getMapping();
		Map<Tuple2<Long, String>, Long> storyIdByEventIdAndStoryLabel = getStoryIdByEventIdAndStoryLabel();
		return getMatches(mapping, i -> daterIds.get(i), label -> storyIdByEventIdAndStoryLabel.get(Tuple.of(eventId, label)));
	}
	
	private Map<Tuple2<Long, String>, Long> getStoryIdByEventIdAndStoryLabel() {
		return erosDb.selectFrom(EVENT_STORIES).fetch()
				.stream()
				.map(this::toEntry)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	private Map.Entry<Tuple2<Long, String>, Long> toEntry(EventStoriesRecord record) {
		return Maps.immutableEntry(Tuple.of(record.getEventId(), record.getLabel().toString()), record.getStoryId());
	}
	
	private  void assertMappingIsValid(String mapping) {
		getMatches(mapping, i -> (long) i, label -> assertLabelAndGetAnyId(label));
	}
	
	private long assertLabelAndGetAnyId(String label) {
		EventStoriesLabel.valueOf(label);
		return 0;
	}
	
	private Matches getMatches(String mapping, Function<Integer, Long> daterIdMapping, Function<String, Long> storyIdMapping) {
		Function<JsonNode, Entry<Long, Match>> toDaterIdAndMatch = json -> toDaterIdAndMatch(json, daterIdMapping, storyIdMapping);
		ListMultimap<Long, Match> matchList = LinkedListMultimap.create();
		Try.of(() -> new ObjectMapper().readTree(mapping))
				.andThen(root -> Streams.stream(root.fields())
						.map(Entry::getValue)
						.flatMap(round -> Streams.stream(round.fields())
								.map(Entry::getValue)
								.map(toDaterIdAndMatch))
						.forEach(daterIdAndMatch -> matchList.put(daterIdAndMatch.getKey(), daterIdAndMatch.getValue())))
				.getOrElseThrow(MatchMappingAccessException::new);
		return new Matches(matchList);
	}
	
	private Entry<Long, Match> toDaterIdAndMatch(JsonNode json, Function<Integer, Long> daterIdMapping, Function<String, Long> storyIdMapping) {
		String[] matchPair = json.get("mapping").asText().split(":");
		String storyLabel = json.get("story_id").asText();
		long storyId = storyIdMapping.apply(storyLabel);
		String daterRank = String.valueOf(matchPair[0].charAt(1));
		String matchedRank = String.valueOf(matchPair[1].charAt(1));
		long daterId = daterIdMapping.apply(Integer.parseInt(daterRank) - 1);
		long matchedId = daterIdMapping.apply(Integer.parseInt(matchedRank) - 1);
		return Maps.immutableEntry(daterId, new Match(matchedId, storyId));
	}

	public List<MatchMapping> getMatchMappings() {
		List<MatchMapping> matchMappings = erosDb.selectFrom(DATING_MAPPING).fetch().map(MatchMapping::of);
		matchMappings.forEach(matchMapping -> assertMappingIsValid(matchMapping.getMapping()));
		return matchMappings;
	}

	public MatchMapping getMatchMapping(long id) {
		return Try.of(() -> erosDb.selectFrom(DATING_MAPPING).where(DATING_MAPPING.ID.equal(id)).fetchOne())
				.map(MatchMapping::of)
				.peek(matchMapping -> assertMappingIsValid(matchMapping.getMapping()))
				.getOrElseThrow(MatchMappingAccessException::new);

	}

	public MatchMapping addMatchMapping(MatchMapping matchMapping) {
		assertMappingIsValid(matchMapping.getMapping());
		return addRecord(erosDb, DATING_MAPPING, matchMapping);
	}
	
	public static class StoryLabelNotValidException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public StoryLabelNotValidException(String label){
			super(label + " not valid!");
		}
	}
	
	public static class MatchMappingAccessException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public MatchMappingAccessException(Throwable e){
			super(e);
		}
	}

}
