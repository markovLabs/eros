package com.markovlabs.eros.matches;

import java.util.List;
import static java.util.stream.Collectors.toList;

import com.google.common.collect.ListMultimap;

public final class Matches {
	
	private final ListMultimap<Long, Match> matchList;

	public Matches(ListMultimap<Long, Match> matchList) {
		this.matchList = matchList;
	}

	public List<Match> getMatchesForDater(long daterId) {
		return matchList.get(daterId);
	}
	
	public List<Long> getMatcheIdsForDater(long daterId) {
		return matchList.get(daterId).stream().map(Match::getMatchId).collect(toList());
	}
	
	public static final class Match {
		
		private final long matchId;
		private final String storyLabel;
		
		public Match(long matchId, String storyLabel){
			this.matchId = matchId;
			this.storyLabel = storyLabel;
		}

		public long getMatchId() {
			return matchId;
		}

		public String getStoryLabel() {
			return storyLabel;
		}
	}

}
