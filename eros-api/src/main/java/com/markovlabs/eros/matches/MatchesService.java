package com.markovlabs.eros.matches;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.DatingMapping.DATING_MAPPING;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import org.jooq.DSLContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.markovlabs.eros.matches.Matches.Match;

import javaslang.control.Try;

public final class MatchesService {
	
	private final DSLContext erosDb;

	public MatchesService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public Matches getMatches(Long mappingId, List<Long> daterIds) {
		String mapping = getMatchMapping(mappingId).getMapping();
		return getMatches(mapping, i -> daterIds.get(i));
	}
	
	private  void assertMappingIsValid(String mapping) {
		getMatches(mapping, i -> (long) i);
	}
	
	private Matches getMatches(String mapping, Function<Integer, Long> daterIdMapping) {
		Function<JsonNode, Entry<Long, Match>> toDaterIdAndMatch = json -> toDaterIdAndMatch(json, daterIdMapping);
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
	
	private Entry<Long, Match> toDaterIdAndMatch(JsonNode json, Function<Integer, Long> daterIdMapping) {
		String[] matchPair = json.get("mapping").asText().split(":");
		String storyLabel = json.get("story_id").asText();
		String daterRank = String.valueOf(matchPair[0].charAt(1));
		String matchedRank = String.valueOf(matchPair[1].charAt(1));
		long daterId = daterIdMapping.apply(Integer.parseInt(daterRank) - 1);
		long matchedId = daterIdMapping.apply(Integer.parseInt(matchedRank) - 1);
		return Maps.immutableEntry(daterId, new Match(matchedId, storyLabel));
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
	
	public static class MatchMappingAccessException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public MatchMappingAccessException(Throwable e){
			super(e);
		}
	}

}
