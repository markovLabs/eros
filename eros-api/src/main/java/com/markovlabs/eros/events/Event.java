package com.markovlabs.eros.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.markovlabs.eros.Builder;

@JsonDeserialize(builder = Event.EventBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "location", "date", "started", "ended", "mapping_id" })
public class Event {

	private final long id;
	private final String name;
	private final String location;
	private final String date;
	private final boolean started;
	private final boolean ended;
	private final long mappingId;

	public Event(EventBuilder builder) {
		this.id = builder.id;
		this.date = builder.date;
		this.location = builder.location;
		this.name = builder.name;
		this.started = builder.started;
		this.ended = builder.ended;
		this.mappingId = builder.mappingId;
	}

	public long getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isEnded() {
		return ended;
	}
	@JsonIgnore
	public int getStarted() {
		return started ? 1 : 0;
	}
	@JsonIgnore
	public int getEnded() {
		return ended ? 1 : 0;
	}
	public long getMappingId() {
		return mappingId;
	}

	public static class EventBuilder implements Builder<Event> {

		private long id;
		private String name;
		private String location;
		private String date;
		private boolean started = false;
		private boolean ended = false;
		private long mappingId;

		public EventBuilder withId(long id) {
			this.id = id;
			return this;
		}

		public EventBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public EventBuilder withLocation(String location) {
			this.location = location;
			return this;
		}

		public EventBuilder withDate(String date) {
			this.date = date;
			return this;
		}
		@JsonIgnore
		public EventBuilder withStarted(int started) {
			this.started = started == 1;
			return this;
		}
		@JsonIgnore
		public EventBuilder withEnded(int ended) {
			this.ended = ended == 1;
			return this;
		}
		@JsonProperty("started")
		public EventBuilder started(boolean started) {
			this.started = started;
			return this;
		}
		@JsonProperty("ended")
		public EventBuilder ended(boolean ended) {
			this.ended = ended;
			return this;
		}

		public EventBuilder withMappingId(long mappingId) {
			this.mappingId = mappingId;
			return this;
		}

		@Override
		public Event build() {
			return new Event(this);
		}

		public static Event of(com.markovlabs.eros.model.tables.records.EventRecord eventRow) {
			return new EventBuilder()
					.withId(eventRow.getId())
					.withName(eventRow.getName())
					.withLocation(eventRow.getLocation())
					.withDate(eventRow.getEventDate())
					.started(eventRow.getStarted().intValue() == 1)
					.ended(eventRow.getEnded().intValue() == 1)
					.withMappingId(eventRow.getMappingId())
					.build();
		}
	}
}
