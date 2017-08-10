package com.markovlabs.eros.messages;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.tables.records.MessagesRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "from_dater_id", "to_dater_id", "event_id", "content", "time_received"})
public final class Message {
	
	private Long id;
	private Long fromDaterId;
	private Long toDaterId;
	private Long eventId;
	private String content;
	private String timeReceived;
	
	public Message(){}

	public Long getId() {
		return id;
	}

	public Message setId(long id) {
		this.id = id;
		return this;
	}

	public Long getFromDaterId() {
		return fromDaterId;
	}

	public Message setFromDaterId(long fromDaterId) {
		this.fromDaterId = fromDaterId;
		return this;
	}

	public Long getToDaterId() {
		return toDaterId;
	}

	public Message setToDaterId(long toDaterId) {
		this.toDaterId = toDaterId;
		return this;
	}

	public Long getEventId() {
		return eventId;
	}

	public Message setEventId(long eventId) {
		this.eventId = eventId;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Message setContent(String content) {
		this.content = content;
		return this;
	}
	@JsonProperty("time_received")
	private Message setTimeReceivedAsString(String timeReceived) {
		this.timeReceived = timeReceived;
		return this;
	}
	@JsonProperty("time_received")
	private String getTimeReceivedAsString() {
		return timeReceived;
	}
	@JsonIgnore
	private Message setTimeReceived(Timestamp timeReceived) {
		this.timeReceived = timeReceived.toString();
		return this;
	}
	@JsonIgnore
	private Timestamp getTimeReceived() {
		return Timestamp.valueOf(timeReceived);
	}
	
	public static Message of(MessagesRecord record) {
		return new Message()
				.setId(record.getId())
				.setFromDaterId(record.getFromDaterId())
				.setToDaterId(record.getToDaterId())
				.setEventId(record.getEventId())
				.setContent(record.getContent())
				.setTimeReceived(record.getTimeReceived());
	}

}
