package com.markovlabs.eros.matches;

import com.markovlabs.eros.daters.Dater;

public final class DaterMatchTO {
	
	private final Dater match;
	private final long storyId;
	
	public DaterMatchTO(Dater match, long storyId){
		this.match = match;
		this.storyId = storyId;
	}

	public Dater getMatch() {
		return match;
	}

	public long getStoryId() {
		return storyId;
	}

}
