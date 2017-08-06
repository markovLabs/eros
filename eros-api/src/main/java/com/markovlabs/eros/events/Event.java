package com.markovlabs.eros.events;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.markovlabs.eros.Builder;
import com.markovlabs.eros.Deconstructable;

@JsonDeserialize(builder = Event.EventBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "location", "date", "started", "ended", "mapping_id" })
public class Event implements Deconstructable {

	private final Long id;
	private final String name;
	private final String location;
	private final String date;
	private final Boolean started;
	private final Boolean ended;
	private final Long mappingId;

	public Event(EventBuilder builder) {
		this.id = builder.id;
		this.date = builder.date;
		this.location = builder.location;
		this.name = builder.name;
		this.started = builder.started;
		this.ended = builder.ended;
		this.mappingId = builder.mappingId;
	}

	public Long getId() {
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

	@JsonProperty("started")
	public boolean isStarted() {
		return started;
	}
	@JsonProperty("ended")
	public boolean isEnded() {
		return ended;
	}
	@JsonIgnore
	public Integer getStarted() {
		return started == null ? null : (started  ? 1 : 0);
	}
	@JsonIgnore
	public Integer getEnded() {
		return ended == null ? null : (ended  ? 1 : 0);
	}
	public Long getMappingId() {
		return mappingId;
	}

	public static class EventBuilder implements Builder<Event> {

		private Long id = null;
		private String name;
		private String location;
		private String date;
		private Boolean started = null;
		private Boolean ended = null;
		private Long mappingId =  null;

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

	@Override
	public Map<String, Object> asMap() {
		return getFieldValues().stream().filter(entry -> entry.getValue() != null)
		.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}

	private List<Map.Entry<String, Object>> getFieldValues() {
		return ImmutableList.<Map.Entry<String, Object>>builder()
				.add(Maps.immutableEntry("LOCATION", this.getLocation()))
				.add(Maps.immutableEntry("EVENT_DATE", this.getDate()))
				.add(Maps.immutableEntry("MAPPING_ID", this.getMappingId()))
				.add(Maps.immutableEntry("NAME", this.getName()))
				.add(Maps.immutableEntry("ID", this.getId()))
				.add(Maps.immutableEntry("STARTED", this.getStarted()))
				.add(Maps.immutableEntry("ENDED", this.getEnded()))
				.build();
	}
}
