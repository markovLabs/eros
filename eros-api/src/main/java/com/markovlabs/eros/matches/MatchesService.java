package com.markovlabs.eros.matches;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.DatingMapping.DATING_MAPPING;
import static com.markovlabs.eros.model.tables.EventStories.EVENT_STORIES;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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

import javaslang.control.Try;

public final class MatchesService {
	
	private final DSLContext erosDb;

	public MatchesService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public Matches getMatches(Long mappingId, List<Long> maleDaterIds, List<Long> femaleDaterIds, long eventId) {
		String mapping = getMatchMapping(mappingId).getMapping();
		Map<String, Long> storyIdByEventIdAndStoryLabel = getStoryIdByStoryLabel(eventId);
		return getMatches(mapping, findMapOf(maleDaterIds), findMapOf(femaleDaterIds), label -> storyIdByEventIdAndStoryLabel.get(label));
	}
	
	private Function<Integer, Optional<Long>> findMapOf(List<Long> list){
		return i -> (i >= list.size()) ? Optional.empty() : Optional.of(list.get(i));
	}
	
	private Map<String, Long> getStoryIdByStoryLabel(long eventId) {
		return erosDb.selectFrom(EVENT_STORIES).where(EVENT_STORIES.EVENT_ID.equal(eventId)).fetch()
				.stream()
				.map(this::toEntry)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	private Map.Entry<String, Long> toEntry(EventStoriesRecord record) {
		return Maps.immutableEntry(record.getLabel().toString(), record.getStoryId());
	}
	
	private  void assertMappingIsValid(String mapping) {
		getMatches(mapping, i -> Optional.of((long) i), i -> Optional.of((long) i), label -> assertLabelAndGetAnyId(label));
	}
	
	private long assertLabelAndGetAnyId(String label) {
		EventStoriesLabel.valueOf(label);
		return 0;
	}
	
	private Matches getMatches(String mapping, Function<Integer, Optional<Long>> maleDaterMapping, Function<Integer, Optional<Long>> femaleDaterMapping, Function<String, Long> storyIdMapping) {
		Function<JsonNode, Optional<Entry<Long, Match>>> findDaterIdAndMatch = json -> findDaterIdAndMatch(json, maleDaterMapping, femaleDaterMapping, storyIdMapping);
		ListMultimap<Long, Match> matchList = LinkedListMultimap.create();
		Try.of(() -> new ObjectMapper().readTree(mapping))
				.andThen(root -> Streams.stream(root.fields())
						.map(Entry::getValue)
						.flatMap(round -> Streams.stream(round.iterator())
								.map(findDaterIdAndMatch))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.forEach(daterIdAndMatch -> matchList.put(daterIdAndMatch.getKey(), daterIdAndMatch.getValue())))
				.getOrElseThrow(MatchMappingAccessException::new);
		return new Matches(matchList);
	}
	
	private Optional<Entry<Long, Match>> findDaterIdAndMatch(JsonNode json, Function<Integer, Optional<Long>> maleDaterMapping, Function<Integer, Optional<Long>> femaleDaterMapping, Function<String, Long> storyIdMapping) {
		String[] matchPair = json.get("mapping").asText().split(":");
		String storyLabel = json.get("story_id").asText();
		Optional<Long> foundStoryId = Optional.ofNullable(storyIdMapping.apply(storyLabel));
		String daterRank = String.valueOf(matchPair[0].charAt(1));
		String genderDaterRank = String.valueOf(matchPair[0].charAt(0));
		String matchedRank = String.valueOf(matchPair[1].charAt(1));
		String genderMatchedRank = String.valueOf(matchPair[1].charAt(0));
		Optional<Long> foundDaterId = findDaterId(genderDaterRank, daterRank, maleDaterMapping, femaleDaterMapping);
		Optional<Long> foundMatchedId = findDaterId(genderMatchedRank, matchedRank, maleDaterMapping, femaleDaterMapping);
		return getMatchEntry(foundDaterId, foundMatchedId, foundStoryId);
	}

	private Optional<Entry<Long, Match>> getMatchEntry(Optional<Long> foundDaterId, Optional<Long> foundMatchedId, Optional<Long> foundStoryId) {
		if(foundDaterId.isPresent() && foundMatchedId.isPresent() && foundStoryId.isPresent()){
			return Optional.of(Maps.immutableEntry(foundDaterId.get(), new Match(foundMatchedId.get(), foundStoryId.get())));
		}
		return Optional.empty();
	}

	private Optional<Long> findDaterId(String genderDaterRank, String daterRank, Function<Integer, Optional<Long>> maleDaterMapping, Function<Integer, Optional<Long>> femaleDaterMapping) {
		if(genderDaterRank.equals("F")) {
			return femaleDaterMapping.apply(Integer.parseInt(daterRank) - 1);
		} else {
			return maleDaterMapping.apply(Integer.parseInt(daterRank) - 1);
		}
		
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
