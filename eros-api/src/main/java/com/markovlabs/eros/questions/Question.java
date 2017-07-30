package com.markovlabs.eros.questions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.markovlabs.eros.Deconstructable;
import com.markovlabs.eros.model.enums.QuestionPageType;
import com.markovlabs.eros.model.tables.records.QuestionRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "content", "page_type"})
public class Question implements Deconstructable {
	
	private Long id = -1L;
	private String content;
	private String pageType;
	
	public Question(){}

	public Long getId() {
		return id == -1 ? null : id;
	}

	public Question setId(long id) {
		this.id = id;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Question setContent(String content) {
		this.content = content;
		return this;
	}

	@JsonProperty("page_type")
	public String getPageTypeAsString() {
		return pageType;
	}
	@JsonProperty("page_type")
	public Question setPageTypeAsString(String pageType) {
		this.pageType = pageType;
		return this;
	}
	@JsonIgnore
	public QuestionPageType getPageType() {
		return pageType == null ? null : QuestionPageType.valueOf(pageType);
	}
	@JsonIgnore
	public Question setPageType(QuestionPageType pageType) {
		this.pageType = pageType.toString();
		return this;
	}
	public static Question of(QuestionRecord questionRecord){
		return new Question()
				.setId(questionRecord.getId())
				.setContent(questionRecord.getContent())
				.setPageType(questionRecord.getPageType());
	}

	@Override
	public Map<String, Object> asMap() {
		return getFieldValues().stream().filter(entry -> entry.getValue() != null)
		.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}

	private List<Map.Entry<String, Object>> getFieldValues() {
		return ImmutableList.<Map.Entry<String, Object>>builder()
				.add(Maps.immutableEntry("ID", this.getId()))
				.add(Maps.immutableEntry("CONTENT", this.getContent()))
				.add(Maps.immutableEntry("PAGE_TYPE", this.getPageType()))
				.build();
	}


}
