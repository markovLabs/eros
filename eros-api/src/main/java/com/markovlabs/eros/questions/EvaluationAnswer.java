package com.markovlabs.eros.questions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.tables.records.EvaluationAnswersRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "dater_id", "event_id", "match_id", "question_id", "answer" })
public class EvaluationAnswer {

	private long id;
	private long daterId;
	private long eventId;
	private long matchId;
	private long questionId;
	private String answer;
	
	public EvaluationAnswer(){}

	public long getId() {
		return id;
	}

	public EvaluationAnswer setId(long id) {
		this.id = id;
		return this;
	}

	public long getDaterId() {
		return daterId;
	}

	public EvaluationAnswer setDaterId(long daterId) {
		this.daterId = daterId;
		return this;
	}

	public long getEventId() {
		return eventId;
	}

	public EvaluationAnswer setEventId(long eventId) {
		this.eventId = eventId;
		return this;
	}

	public long getMatchId() {
		return matchId;
	}

	public EvaluationAnswer setMatchId(long matchId) {
		this.matchId = matchId;
		return this;
	}

	public long getQuestionId() {
		return questionId;
	}

	public EvaluationAnswer setQuestionId(long questionId) {
		this.questionId = questionId;
		return this;
	}

	public String getAnswer() {
		return answer;
	}

	public EvaluationAnswer setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	public static EvaluationAnswer of(EvaluationAnswersRecord evaluationAnswerRecord) {
		return new EvaluationAnswer()
				.setId(evaluationAnswerRecord.getId())
				.setDaterId(evaluationAnswerRecord.getDaterId())
				.setEventId(evaluationAnswerRecord.getEventId())
				.setMatchId(evaluationAnswerRecord.getMatchId())
				.setQuestionId(evaluationAnswerRecord.getQuestionId())
				.setAnswer(evaluationAnswerRecord.getAnswer());
	}

}
