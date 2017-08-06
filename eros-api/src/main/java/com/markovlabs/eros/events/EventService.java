package com.markovlabs.eros.events;

import java.util.List;
import java.util.Map;
import static com.markovlabs.eros.JOOQRecordUtility.addRecord;
import org.jooq.DSLContext;
import org.jooq.Field;

import com.markovlabs.eros.daters.Dater;
import com.markovlabs.eros.model.enums.DaterGender;
import com.markovlabs.eros.model.enums.EventStoriesLabel;
import com.markovlabs.eros.model.tables.records.EventDatersRecord;
import com.markovlabs.eros.model.tables.records.EventRecord;
import com.markovlabs.eros.model.tables.records.EventStoriesRecord;

import static com.markovlabs.eros.model.tables.Event.EVENT;
import static com.markovlabs.eros.model.tables.Dater.DATER;
import static com.markovlabs.eros.model.tables.EventDaters.EVENT_DATERS;
import static com.markovlabs.eros.model.tables.EventStories.EVENT_STORIES;

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

	public List<Long> getMaleDaterIdsForEvent(long eventId) {
		return getDaterIdsForEvent(eventId, DaterGender.MALE);
	}
	
	public List<Long> getFemaleDaterIdsForEvent(long eventId) {
		return getDaterIdsForEvent(eventId, DaterGender.FEMALE);
	}
	
	private List<Long> getDaterIdsForEvent(long eventId, DaterGender gender) {
		return erosDb.select(EVENT_DATERS.EVENT_ID, EVENT_DATERS.DATER_ID, DATER.ID, DATER.GENDER)
				.from(EVENT_DATERS)
				.join(DATER).on(DATER.ID.equal(EVENT_DATERS.DATER_ID))
				.where(EVENT_DATERS.EVENT_ID.equal(eventId).and(DATER.GENDER.equal(gender)))
				.fetch(EVENT_DATERS.DATER_ID);
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
		fieldValues.put("ID", eventId);
		record.fromMap(fieldValues);
		record.update();
		return getEvent(eventId);
	}
	
	public void updateDaterForEvent(long eventId, Dater dater) {
		Long eventDaterId = erosDb.select(EVENT_DATERS.EVENT_ID, EVENT_DATERS.DATER_ID, EVENT_DATERS.ID)
				.from(EVENT_DATERS)
				.where(EVENT_DATERS.EVENT_ID.equal(eventId).and(EVENT_DATERS.DATER_ID.equal(dater.getId())))
				.fetchOne(EVENT_DATERS.ID);
		EventDatersRecord eventDaterRecord = newEventDatersRecord(eventId, dater.getId());
		eventDaterRecord.setId(eventDaterId);
		eventDaterRecord.setProfileEvaluationCompletedFlag(dater.getProfileEvaluationCompletedFlag());
		eventDaterRecord.setMessagingEvaluationCompletedFlag(dater.getMessagingEvaluationCompletedFlag());
		for (Field<?> f : eventDaterRecord.fields()) {
			if (eventDaterRecord.getValue(f) == null) {
				eventDaterRecord.changed(f, false);
			}
		}
		eventDaterRecord.update();
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

	public List<EventStory> getEventStories() {
		return erosDb.selectFrom(EVENT_STORIES).fetch().map(this::toEventStory);
	}

	public EventStory getEventStory(long eventStoryId) {
		return getEventStories().stream().filter(eventStory -> eventStory.getId() == eventStoryId)
				.findFirst()
				.orElseThrow(EventStoryNotFoundException::new);
	}
	
	private EventStory toEventStory(EventStoriesRecord record) {
		EventStory eventStory = new EventStory();
		eventStory.setEventId(record.getEventId());
		eventStory.setLabel(record.getLabel().toString());
		eventStory.setStoryId(record.getStoryId());
		eventStory.setId(record.getId());
		return eventStory;
	}

	public EventStory addEventStory(EventStory eventStory) {
		EventStoriesRecord record = erosDb.newRecord(EVENT_STORIES);
		record.setEventId(eventStory.getEventId());
		record.setLabel(EventStoriesLabel.valueOf(eventStory.getLabel()));
		record.setStoryId(eventStory.getStoryId());
		record.insert();
		record.refresh();
		return toEventStory(record);
	}
	
	public void removeEventStory(long eventStoryId) {
		erosDb.deleteFrom(EVENT_STORIES).where(EVENT_STORIES.ID.equal(eventStoryId));
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
	public static class EventStoryNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public EventStoryNotFoundException(){
			super();
		}
	}
}
