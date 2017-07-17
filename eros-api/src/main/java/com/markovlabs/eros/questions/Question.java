package com.markovlabs.eros.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.enums.QuestionPageType;
import com.markovlabs.eros.model.tables.records.QuestionRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "content", "page_type"})
public class Question {
	
	private long id;
	private String content;
	private String pageType;
	
	public Question(){}

	public long getId() {
		return id;
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
		return QuestionPageType.valueOf(pageType);
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


}
