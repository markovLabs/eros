package com.markovlabs.eros.daters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.tables.records.ImageRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "dater_id", "name", "content"})
public class Image {

	private long id;
	private long daterId;
	private String name;
	private String content;

	public Image() {}

	public long getId() {
		return id;
	}

	public Image setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Image setName(String name) {
		this.name = name;
		return this;
	}

	public long getDaterId() {
		return daterId;
	}

	public Image setDaterId(long daterId) {
		this.daterId = daterId;
		return this;
	}
	
	public static Image of(ImageRecord record) {
		return new Image().setId(record.getId())
				.setName(record.getName())
				.setDaterId(record.getDaterId());
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
