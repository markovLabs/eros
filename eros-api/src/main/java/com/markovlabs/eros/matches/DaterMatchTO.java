package com.markovlabs.eros.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.daters.Dater;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "story_id", "match"})
public final class DaterMatchTO {
	
	private final Dater match;
	@JsonProperty("story_id") private final long storyId;
	
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
