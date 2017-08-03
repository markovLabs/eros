package com.markovlabs.eros.stories;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.markovlabs.eros.Deconstructable;
import com.markovlabs.eros.model.enums.StoryStoryType;
import com.markovlabs.eros.model.tables.records.StoryRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "content", "story_type", "question", "answer_a", "answer_b"})
public final class Story implements Deconstructable {

	private Long id = -1L;
	private String content;
	private String storyType;
	private String question;
	private String answerA;
	private String answerB;

	public Story() {}

	public Long getId() {
		return id == -1 ? null : id;
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
	public Story setStoryType(StoryStoryType storyType) {
		this.storyType = storyType.toString();
		return this;
	}
	@JsonIgnore
	public StoryStoryType getStoryType() {
		return storyType ==  null ? null : StoryStoryType.valueOf(storyType);
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
				.setQuestion(storyRecord.getQuestion())
				.setAnswerA(storyRecord.getAnswerA())
				.setAnswerB(storyRecord.getAnswerB());
	}

	@Override
	public Map<String, Object> asMap() {
		return getFieldValues().stream().filter(entry -> entry.getValue() != null)
		.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}

	private List<Map.Entry<String, Object>> getFieldValues() {
		return ImmutableList.<Map.Entry<String, Object>>builder()
				.add(Maps.immutableEntry("ANSWER_A", this.getAnswerA()))
				.add(Maps.immutableEntry("ANSWER_B", this.getAnswerB()))
				.add(Maps.immutableEntry("CONTENT", this.getContent()))
				.add(Maps.immutableEntry("QUESTION", this.getQuestion()))
				.add(Maps.immutableEntry("STORY_TYPE", this.getStoryType()))
				.add(Maps.immutableEntry("ID", this.getId()))
				.build();
	}
}
