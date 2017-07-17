package com.markovlabs.eros.questions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.tables.records.ProfileAnswersRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "dater_id", "question_id", "answer" })
public class ProfileAnswer {

	private long id;
	private long daterId;
	private long questionId;
	private String answer;
	
	public ProfileAnswer(){}

	public long getId() {
		return id;
	}

	public ProfileAnswer setId(long id) {
		this.id = id;
		return this;
	}

	public long getDaterId() {
		return daterId;
	}

	public ProfileAnswer setDaterId(long daterId) {
		this.daterId = daterId;
		return this;
	}

	public long getQuestionId() {
		return questionId;
	}

	public ProfileAnswer setQuestionId(long questionId) {
		this.questionId = questionId;
		return this;
	}

	public String getAnswer() {
		return answer;
	}

	public ProfileAnswer setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	public static ProfileAnswer of(ProfileAnswersRecord profileAnswerRecord) {
		return new ProfileAnswer()
				.setId(profileAnswerRecord.getId())
				.setDaterId(profileAnswerRecord.getDaterId())
				.setQuestionId(profileAnswerRecord.getQuestionId())
				.setAnswer(profileAnswerRecord.getAnswer());
	}

}
