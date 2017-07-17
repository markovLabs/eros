package com.markovlabs.eros.questions;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.JOOQRecordUtility.updateRecord;
import static com.markovlabs.eros.model.tables.Question.QUESTION;
import java.util.List;
import org.jooq.DSLContext;
import javaslang.control.Try;

public class QuestionService {
	
	private final DSLContext erosDb;

	public QuestionService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public List<Question> getQuestions() {
		return erosDb.selectFrom(QUESTION).fetch().map(Question::of);
	}

	public Question getQuestion(long id) {
		return Try.of(() -> erosDb.selectFrom(QUESTION).where(QUESTION.ID.equal(id)).fetchOne())
				.map(Question::of)
				.getOrElseThrow(e -> new QuestionNotFoundException(e));

	}

	public Question updateQuestion(Question question) {
		return updateRecord(erosDb, QUESTION, question);
	}

	public Question addQuestion(Question question) {
		return addRecord(erosDb, QUESTION, question);
	}
	
	public static class QuestionNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public QuestionNotFoundException(String message, Throwable e){
			super(message, e);
		}
		public QuestionNotFoundException(Throwable e){
			super(e);
		}
		public QuestionNotFoundException(String message){
			super(message);
		}
		public QuestionNotFoundException(){
			super();
		}
	}

}
