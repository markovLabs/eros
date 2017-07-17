package com.markovlabs.eros.stories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.enums.StoryLabel;
import com.markovlabs.eros.model.enums.StoryStoryType;
import com.markovlabs.eros.model.tables.records.StoryRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "content", "story_type", "label", "question", "answer_a", "answer_b"})
public final class Story {

	private long id;
	private String content;
	private String storyType;
	private String label;
	private String question;
	private String answerA;
	private String answerB;

	public Story() {}

	public long getId() {
		return id;
	}

	public Story setId(long id) {
		this.id = id;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Story setContent(String content) {
		this.content = content;
		return this;
	}
	@JsonProperty("story_type")
	public String getStoryTypeAsString() {
		return storyType;
	}
	@JsonProperty("story_type")
	public Story setStoryTypeAsString(String storyType) {
		this.storyType = storyType;
		return this;
	}
	@JsonIgnore
	private Story setStoryType(StoryStoryType storyType) {
		this.storyType = storyType.toString();
		return this;
	}
	@JsonIgnore
	private StoryStoryType getStoryType() {
		return StoryStoryType.valueOf(storyType);
	}
	@JsonProperty("label")
	public String getLabelAsString() {
		return label;
	}
	@JsonProperty("label")
	public Story setLabelAsString(String label) {
		this.label = label;
		return this;
	}
	@JsonIgnore
	private Story setLabel(StoryLabel label) {
		this.label = label.toString();
		return this;
	}
	@JsonIgnore
	private StoryLabel getLabel() {
		return StoryLabel.valueOf(label);
	}

	public String getQuestion() {
		return question;
	}

	public Story setQuestion(String question) {
		this.question = question;
		return this;
	}

	public String getAnswerA() {
		return answerA;
	}

	public Story setAnswerA(String answerA) {
		this.answerA = answerA;
		return this;
	}

	public String getAnswerB() {
		return answerB;
	}

	public Story setAnswerB(String answerB) {
		this.answerB = answerB;
		return this;
	}

	public static Story of(StoryRecord storyRecord) {
		return new Story()
				.setId(storyRecord.getId())
				.setContent(storyRecord.getContent())
				.setStoryType(storyRecord.getStoryType())
				.setLabel(storyRecord.getLabel())
				.setQuestion(storyRecord.getQuestion())
				.setAnswerA(storyRecord.getAnswerA())
				.setAnswerB(storyRecord.getAnswerB());
	}





}
