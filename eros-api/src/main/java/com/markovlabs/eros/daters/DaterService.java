package com.markovlabs.eros.daters;

import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import static com.markovlabs.eros.JOOQRecordUtility.updateRecord;
import static com.markovlabs.eros.model.tables.Dater.DATER;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record;

import javaslang.control.Try;
import static com.markovlabs.eros.model.tables.EventDaters.EVENT_DATERS;

public class DaterService {
	
	private final DSLContext erosDb;

	public DaterService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}
	
	public List<Dater> getDaters() {
		return erosDb.selectFrom(DATER).fetch().map(Dater::of);
	}

	public Dater getDater(long id) {
		return Try.of(() -> erosDb.selectFrom(DATER).where(DATER.ID.equal(id)).fetchOne())
				.map(Dater::of)
				.getOrElseThrow(e -> new DaterNotFoundException(e));
	}
	
	public Optional<Dater> getDater(Collection<Long> daterIds, long daterId){
		return daterIds.stream()
				.filter(id -> daterId == id)
				.map(id -> getDater(id))
				.findFirst();
	}

	public List<Dater> getDaters(String email, String pwd) {
		return erosDb.selectFrom(DATER)
				.where(DATER.EMAIL.eq(email).and(DATER.PWD.eq(pwd)))
				.fetch()
				.map(Dater::of);
	}
	
	public List<Dater> getDatersForEvent(long eventId) {
		return erosDb.select().from(DATER).join(EVENT_DATERS).on(EVENT_DATERS.DATER_ID.equal(DATER.ID))
				.where(EVENT_DATERS.EVENT_ID.equal(eventId))
				.fetch()
				.map(this::toDater);
	}
	
	private Dater toDater(Record record){
		Dater dater = new Dater();
		dater.setAcceptanceQuestionPageFlag(record.getValue(DATER.ACCEPTANCE_QUESTION_PAGE_FLAG));
		dater.setAcceptConsentPageFlag(record.getValue(DATER.ACCEPT_CONSENT_PAGE_FLAG));
		dater.setEmail(record.getValue(DATER.EMAIL));
		dater.setGender(record.getValue(DATER.GENDER));
		dater.setId(record.getValue(DATER.ID));
		dater.setLastName(record.getValue(DATER.LAST_NAME));
		dater.setMessagingEvaluationCompleted(record.getValue(EVENT_DATERS.MESSAGING_EVALUATION_COMPLETED_FLAG).intValue() == 1);
		dater.setName(record.getValue(DATER.NAME));
		dater.setProfileCreationPageFlag(record.getValue(DATER.PROFILE_CREATION_PAGE_FLAG));
		dater.setProfileEvaluationCompleted(record.getValue(EVENT_DATERS.PROFILE_EVALUATION_COMPLETED_FLAG).intValue() == 1);
		dater.setProfileName(record.getValue(DATER.PROFILE_NAME));
		dater.setPwd(record.getValue(DATER.PWD));
		dater.setRejected(record.getValue(DATER.REJECTED));
		return dater;
	}

	public Dater getDaterForEvent(long eventId, long daterId) {
		return getDatersForEvent(eventId).stream()
				.filter(d -> d.getId() == daterId)
				.findFirst().orElseThrow(DaterNotFoundException::new);
	}
	
	public Dater updateDater(Dater dater) {
		return updateRecord(erosDb, DATER, dater);
	}

	public Dater addDater(Dater dater) {
		return addRecord(erosDb, DATER, dater);
	}
	
	public static class DaterNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public DaterNotFoundException(String message, Throwable e){
			super(message, e);
		}
		public DaterNotFoundException(Throwable e){
			super(e);
		}
		public DaterNotFoundException(String message){
			super(message);
		}
		public DaterNotFoundException(){
			super();
		}
	}

}
