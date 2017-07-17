package com.markovlabs.eros.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.enums.StoryAnswersAnswer;
import com.markovlabs.eros.model.tables.records.StoryAnswersRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "dater_id", "story_id", "answer"})
public final class StoryAnswer {

	private long id;
	private long daterId;
	private long storyId;
	private String answer;
	
	public StoryAnswer(){}

	public long getId() {
		return id;
	}

	public StoryAnswer setId(long id) {
		this.id = id;
		return this;
	}

	public long getStoryId() {
		return storyId;
	}

	public StoryAnswer setStoryId(long storyId) {
		this.storyId = storyId;
		return this;
	}
	@JsonProperty("answer")
	public String getStoryAnswer() {
		return answer;
	}
	@JsonIgnore
	public StoryAnswersAnswer getAnswer() {
		return StoryAnswersAnswer.valueOf(answer);
	}
	@JsonProperty("answer")
	public StoryAnswer setAnswer(String answer) {
		this.answer = answer;
		return this;
	}
	@JsonIgnore
	public StoryAnswer setAnswer(StoryAnswersAnswer answer) {
		this.answer = answer.toString();
		return this;
	}
	public long getDaterId() {
		return daterId;
	}

	public StoryAnswer setDaterId(long daterId) {
		this.daterId = daterId;
		return this;
	}

	public static StoryAnswer of(StoryAnswersRecord answerRecord) {
		return new StoryAnswer()
				.setId(answerRecord.getId())
				.setStoryId(answerRecord.getStoryId())
				.setAnswer(answerRecord.getAnswer().toString());
	}
}
