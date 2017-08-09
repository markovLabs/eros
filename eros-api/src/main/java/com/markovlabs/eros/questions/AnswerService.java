package com.markovlabs.eros.questions;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.model.tables.EvaluationAnswers.EVALUATION_ANSWERS;
import static com.markovlabs.eros.model.tables.ProfileAnswers.PROFILE_ANSWERS;
import static com.markovlabs.eros.model.tables.StoryAnswers.STORY_ANSWERS;

import java.util.List;

import org.jooq.DSLContext;

import javaslang.control.Try;

public class AnswerService {

	private final DSLContext erosDb;

	public AnswerService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public List<EvaluationAnswer> getEvaluationAnswers(long eventId, long daterId, long matchId) {
		return erosDb.selectFrom(EVALUATION_ANSWERS)
				.where(EVALUATION_ANSWERS.EVENT_ID.eq(eventId)
						.and(EVALUATION_ANSWERS.DATER_ID.eq(daterId)
								.and(EVALUATION_ANSWERS.MATCH_ID.eq(matchId))))
				.fetch()
				.map(EvaluationAnswer::of);
	}

	public EvaluationAnswer addEvaluationAnswer(EvaluationAnswer answer) {
		return addRecord(erosDb, EVALUATION_ANSWERS, answer);
	}

	public EvaluationAnswer getEvaluationAnswer(long eventId, long daterId, long matchId, long answerId) {
		return Try.of(() -> erosDb.selectFrom(EVALUATION_ANSWERS)
						.where(EVALUATION_ANSWERS.EVENT_ID.eq(eventId)
								.and(EVALUATION_ANSWERS.DATER_ID.eq(daterId)
										.and(EVALUATION_ANSWERS.MATCH_ID.eq(matchId)
												.and(EVALUATION_ANSWERS.ID.eq(answerId)))))
						.fetchOne())
				.map(EvaluationAnswer::of)
				.getOrElseThrow(e -> new AnswerNotFoundException(e));
	}

	public List<StoryAnswer> getStoryAnswers(long daterId) {
		return erosDb.selectFrom(STORY_ANSWERS)
				.where(STORY_ANSWERS.DATER_ID.eq(daterId))
				.fetch()
				.map(StoryAnswer::of);
	}
	
	public List<StoryAnswer> getStoryAnswers(long daterId, long storyId) {
		return erosDb.selectFrom(STORY_ANSWERS)
				.where(STORY_ANSWERS.DATER_ID.equal(daterId).and(STORY_ANSWERS.STORY_ID.equal(storyId)))
				.fetch()
				.map(StoryAnswer::of);
	}

	public StoryAnswer getStoryAnswer(long daterId, long answerId) {
		return Try.of(() -> erosDb.selectFrom(STORY_ANSWERS)
						.where(STORY_ANSWERS.DATER_ID.eq(daterId)
								.and(STORY_ANSWERS.ID.eq(answerId)))
						.fetchOne())
				.map(StoryAnswer::of)
				.getOrElseThrow(e -> new AnswerNotFoundException(e));
	}

	public StoryAnswer addStoryAnswer(StoryAnswer storyAnswer) {
		return addRecord(erosDb, STORY_ANSWERS, storyAnswer);
	}

	public List<ProfileAnswer> getProfileAnswers(long daterId) {
		return erosDb.selectFrom(PROFILE_ANSWERS)
				.where(PROFILE_ANSWERS.DATER_ID.eq(daterId))
				.fetch()
				.map(ProfileAnswer::of);
	}

	public ProfileAnswer getProfileAnswer(long daterId, long profileId) {
		return Try.of(() -> erosDb.selectFrom(PROFILE_ANSWERS)
						.where(PROFILE_ANSWERS.DATER_ID.eq(daterId)
								.and(STORY_ANSWERS.ID.eq(profileId)))
						.fetchOne())
				.map(ProfileAnswer::of)
				.getOrElseThrow(e -> new AnswerNotFoundException(e));
	}

	public ProfileAnswer addProfileAnswer(ProfileAnswer profileAnswer) {
		return addRecord(erosDb, PROFILE_ANSWERS, profileAnswer);
	}

	public static class AnswerNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public AnswerNotFoundException(String message, Throwable e) {
			super(message, e);
		}

		public AnswerNotFoundException(Throwable e) {
			super(e);
		}

		public AnswerNotFoundException(String message) {
			super(message);
		}

		public AnswerNotFoundException() {
			super();
		}
	}

}
