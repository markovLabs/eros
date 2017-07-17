package com.markovlabs.eros.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.tables.records.DatingMappingRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "mapping"})
public final class MatchMapping {

	private long id;
	private String mapping;

	public MatchMapping() {}

	public long getId() {
		return id;
	}

	public MatchMapping setId(long id) {
		this.id = id;
		return this;
	}

	public MatchMapping setMapping(String mapping) {
		this.mapping = mapping;
		return this;
	}

	public String getMapping() {
		return mapping;
	}

	public static MatchMapping of(DatingMappingRecord record) {
		return new MatchMapping()
				.setId(record.getId())
				.setMapping(record.getMapping());
	}

}
