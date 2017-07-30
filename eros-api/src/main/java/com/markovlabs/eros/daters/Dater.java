package com.markovlabs.eros.daters;

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
import com.markovlabs.eros.model.enums.DaterGender;
import com.markovlabs.eros.model.tables.records.DaterRecord;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "email", "pwd", "profile_name", "name", "last_name", "gender", "accepted_consent", "accepted_profile_questions", "profile_created", "rejected"})
public class Dater implements Deconstructable {

	private Long id = -1L;
	private String email;
	private String pwd;
	private String profileName;
	private String name;
	private String lastName;
	private String gender;
	private Boolean acceptedConsent;
	private Boolean acceptedProfileQuestions;
	private Boolean rejected;
	private Boolean profileCreated;

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
		return gender == null ? null : DaterGender.valueOf(gender);
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
		return acceptedConsent == null ? null : ((acceptedConsent) ? (byte) 1 : 0);
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
		return acceptedProfileQuestions == null ? null : ((acceptedProfileQuestions) ? (byte) 1 : 0);
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
	public Dater setRejected(Byte rejected) {
		this.rejected = ((int) rejected) == 1;
		return this;
	}
	@JsonIgnore
	public Byte getRejected() {
		return (rejected == null) ? null : ((rejected) ? (byte) 1 : 0);
	}

	public Dater setId(long id) {
		this.id = id;
		return this;
	}

	public Long getId() {
		return id == -1 ? null : id;
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
				.setRejected(daterRecord.getRejected())
				.setLastName(daterRecord.getLastName())
				.setProfileName(daterRecord.getProfileName())
				.setProfileCreationPageFlag(daterRecord.getProfileCreationPageFlag());
	}
	@Override
	public Map<String, Object> asMap() {
		return getFieldValues().stream().filter(entry -> entry.getValue() != null)
		.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
	}

	private List<Map.Entry<String, Object>> getFieldValues() {
		return ImmutableList.<Map.Entry<String, Object>>builder()
				.add(Maps.immutableEntry("ACCEPTANCE_QUESTION_PAGE_FLAG", this.getAcceptanceQuestionPageFlag()))
				.add(Maps.immutableEntry("ACCEPT_CONSENT_PAGE_FLAG", this.getAcceptConsentPageFlag()))
				.add(Maps.immutableEntry("EMAIL", this.getEmail()))
				.add(Maps.immutableEntry("GENDER", this.getGender()))
				.add(Maps.immutableEntry("LAST_NAME", this.getLastName()))
				.add(Maps.immutableEntry("NAME", this.getName()))
				.add(Maps.immutableEntry("ID", this.getId()))
				.add(Maps.immutableEntry("PROFILE_NAME", this.getProfileName()))
				.add(Maps.immutableEntry("PWD", this.getPwd()))
				.add(Maps.immutableEntry("REJECTED", this.getRejected()))
				.add(Maps.immutableEntry("PROFILE_CREATION_PAGE_FLAG", this.getProfileCreationPageFlag()))
				.build();
	}

	public String getLastName() {
		return lastName;
	}

	public Dater setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getProfileName() {
		return profileName;
	}

	public Dater setProfileName(String profileName) {
		this.profileName = profileName;
		return this;
	}

	public Boolean isProfileCreated() {
		return profileCreated;
	}
	
	@JsonIgnore
	public Byte getProfileCreationPageFlag() {
		return (this.profileCreated == null) ? null : (this.profileCreated ? (byte) 1 : 0);
	}
	@JsonIgnore
	public Dater setProfileCreationPageFlag(Byte profileCreatedFlag) {
		this.profileCreated = ((int) profileCreatedFlag) == 1;
		return this;
	}

	public Dater setProfileCreated(Boolean profileCreated) {
		this.profileCreated = profileCreated;
		return this;
	}
}
