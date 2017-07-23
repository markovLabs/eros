package com.markovlabs.eros.events;

import java.util.List;
import java.util.Map;
import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import org.jooq.DSLContext;

import com.markovlabs.eros.model.tables.records.EventDatersRecord;
import com.markovlabs.eros.model.tables.records.EventRecord;

import static com.markovlabs.eros.model.tables.Event.EVENT;
import static com.markovlabs.eros.model.tables.EventDaters.EVENT_DATERS;
import javaslang.control.Try;

public class EventService {

	private final DSLContext erosDb;

	public EventService(DSLContext erosDb) {
		this.erosDb = erosDb;
	}

	public List<Event> getEvents() {
		return erosDb.selectFrom(EVENT).orderBy(EVENT.EVENT_DATE).fetch().map(Event.EventBuilder::of);
	}
	
	public Event getNextEvent() {
		return Try.of(() -> erosDb.selectFrom(EVENT).orderBy(EVENT.EVENT_DATE).limit(1).fetchOne())
				.map(Event.EventBuilder::of)
				.getOrElseThrow(e -> new EventNotFoundException(e));
	}

	public Event getEvent(long id) {
		return Try.of(() -> erosDb.selectFrom(EVENT).where(EVENT.ID.equal(id)).fetchOne())
				.map(Event.EventBuilder::of)
				.getOrElseThrow(e -> new EventNotFoundException(e));
	}

	public List<Long> getDaterIdsForEvent(long eventId) {
		return erosDb.select(EVENT_DATERS.DATER_ID)
				.from(EVENT_DATERS)
				.fetch()
				.map(record -> record.getValue(EVENT_DATERS.DATER_ID));
	}
	
	public Long getMappingId(long eventId) {
		return getEvent(eventId).getMappingId();
	}

	public Event addEvent(Event event) {
		return addRecord(erosDb, EVENT, event);
	}
	
	public Event updateEvent(long eventId, Event event) {
		EventRecord record = erosDb.newRecord(EVENT);
		Map<String, Object> fieldValues = event.asMap();
		fieldValues.put("Id", eventId);
		record.fromMap(fieldValues);
		record.update();
		return getEvent(eventId);
	}

	public void addDaterForEvent(long daterId, long eventId) {
		newEventDatersRecord(daterId, eventId).insert();
	}

	private EventDatersRecord newEventDatersRecord(long daterId, long eventId) {
		EventDatersRecord record = erosDb.newRecord(EVENT_DATERS);
		record.setDaterId(daterId);
		record.setEventId(eventId);
		return record;
	}
	
	public void removeDaterFromEvent(long daterId, long eventId) {
		newEventDatersRecord(daterId, eventId).delete();
	}
	
	public static class EventNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public EventNotFoundException(String message, Throwable e){
			super(message, e);
		}
		public EventNotFoundException(Throwable e){
			super(e);
		}
		public EventNotFoundException(String message){
			super(message);
		}
		public EventNotFoundException(){
			super();
		}
	}
}
