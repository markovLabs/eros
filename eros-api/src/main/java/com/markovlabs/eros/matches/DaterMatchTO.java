package com.markovlabs.eros.matches;

import com.markovlabs.eros.daters.Dater;

public final class DaterMatchTO {
	
	private final Dater match;
	private final String storyLabel;
	
	public DaterMatchTO(Dater match, String storyLabel){
		this.match = match;
		this.storyLabel = storyLabel;
	}

	public Dater getMatch() {
		return match;
	}

	public String getStoryLabel() {
		return storyLabel;
	}

}
