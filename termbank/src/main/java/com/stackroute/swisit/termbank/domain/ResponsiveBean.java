package com.stackroute.swisit.termbank.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsiveBean {
	
	@Autowired
	@JsonProperty("adjective")
	Adjectives adjectiveBean;

	@Autowired
	@JsonProperty("noun")
	Nouns nounBean;

	public Adjectives getAdjectiveBean() {
		return adjectiveBean;
	}

	public void setAdjectiveBean(Adjectives adjectiveBean) {
		this.adjectiveBean = adjectiveBean;
	}

	public Nouns getNounBean() {
		return nounBean;
	}

	public void setNounBean(Nouns nounBean) {
		this.nounBean = nounBean;
	}

}
