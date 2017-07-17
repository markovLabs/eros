package com.markovlabs.eros.daters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markovlabs.eros.model.enums.DaterGender;
import com.markovlabs.eros.model.tables.records.DaterRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "email", "pwd", "name", "gender", "accepted_consent", "accepted_profile_questions", "rejected"})
public class Dater {

	private long id;
	private String email;
	private String pwd;
	private String name;
	private String gender;
	private boolean acceptedConsent;
	private boolean acceptedProfileQuestions;
	private boolean rejected;

	public Dater() {}

	public String getEmail() {
		return email;
	}

	public Dater setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPwd() {
		return pwd;
	}

	public Dater setPwd(String pwd) {
		this.pwd = pwd;
		return this;
	}

	public String getName() {
		return name;
	}

	public Dater setName(String name) {
		this.name = name;
		return this;
	}
	@JsonProperty("gender")
	public String getGenderAsString() {
		return gender;
	}
	@JsonIgnore
	public DaterGender getGender() {
		return DaterGender.valueOf(gender);
	}
	@JsonProperty("gender")
	public Dater setGenderAsString(String gender) {
		this.gender = gender;
		return this;
	}
	@JsonIgnore
	public Dater setGender(DaterGender gender) {
		this.gender = gender.toString();
		return this;
	}
	@JsonProperty("accepted_consent")
	public boolean isAcceptedConsent() {
		return acceptedConsent;
	}
	@JsonProperty("accepted_consent")
	public Dater setAcceptedConsent(boolean acceptedConsent) {
		this.acceptedConsent = acceptedConsent;
		return this;
	}
	@JsonIgnore
	public Dater setAcceptConsentPageFlag(Byte acceptConsentPageFlag) {
		this.acceptedConsent = ((int) acceptConsentPageFlag) == 1;
		return this;
	}
	@JsonIgnore
	public Byte getAcceptConsentPageFlag() {
		return (acceptedConsent) ? (byte) 1 : 0;
	}
	@JsonProperty("accepted_profile_questions")
	public boolean isAcceptedProfileQuestions() {
		return acceptedProfileQuestions;
	}
	@JsonProperty("accepted_profile_questions")
	public Dater setAcceptedProfileQuestions(boolean acceptedProfileQuestions) {
		this.acceptedProfileQuestions = acceptedProfileQuestions;
		return this;
	}
	@JsonIgnore
	public Byte getAcceptanceQuestionPageFlag() {
		return (acceptedProfileQuestions) ? (byte) 1 : 0;
	}
	@JsonIgnore
	public Dater setAcceptanceQuestionPageFlag(Byte acceptanceQuestionPageFlag) {
		this.acceptedProfileQuestions = ((int) acceptanceQuestionPageFlag) == 1;
		return this;
	}
	@JsonProperty("rejected")
	public boolean isRejected() {
		return rejected;
	}
	@JsonProperty("rejected")
	public Dater setRejectedFlag(boolean rejected) {
		this.rejected = rejected;
		return this;
	}
	@JsonIgnore
	private Dater setRejected(Byte rejected) {
		this.rejected = ((int) rejected) == 1;
		return this;
	}
	@JsonIgnore
	private Byte getRejected() {
		return (rejected) ? (byte) 1 : 0;
	}

	public Dater setId(long id) {
		this.id = id;
		return this;
	}

	public long getId() {
		return id;
	}

	public static Dater of(DaterRecord daterRecord) {
		return new Dater()
				.setId(daterRecord.getId())
				.setEmail(daterRecord.getEmail())
				.setPwd(daterRecord.getPwd())
				.setName(daterRecord.getName())
				.setGender(daterRecord.getGender())
				.setAcceptConsentPageFlag(daterRecord.getAcceptConsentPageFlag())
				.setAcceptanceQuestionPageFlag(daterRecord.getAcceptanceQuestionPageFlag())
				.setRejected(daterRecord.getRejected());
	}
}
