package com.stackroute.swisit.crawler.domain;

/*---------------Importing Libraries--------------*/
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*--------Crawler Result Domain Class which is output to Intent Parser Service--------*/
@Component
@JsonIgnoreProperties(ignoreUnknown=true)
public class CrawlerResult extends ResourceSupport {

	/*-------------Private variables of domain class------------*/

	@JsonProperty("link")
	@NotNull
	private String link;

	@JsonProperty("title")
	@NotNull
	private String title;

	@JsonProperty("snippet")
	@NotNull
	private String snippet;
	
	@JsonProperty("document")
	@NotNull
	private String document;
	
	@JsonProperty("concept")
	@NotNull
	private String concept;
	
	@JsonProperty("lastindexedof")
	private Date lastindexedof;

	/*-----------Default Constructor of Crawler Result Class------------*/
	public CrawlerResult() {
		super();
	}

	/*----------Parameterized Constructor of Crawler Result Class---------*/
	public CrawlerResult(String link, String title, String snippet, String document,
			Date lastindexedof) {
		super();
		this.link = link;
		this.title = title;
		this.snippet = snippet;
		this.document=document;
		this.lastindexedof = lastindexedof;
	}

	/*------------- Getters and setters for fields -----------*/

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getLastindexedof() {
		return lastindexedof;
	}
	
	public void setLastindexedof(Date lastindexedof) {
		this.lastindexedof = lastindexedof;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getSnippet() {
		return snippet;
	}
	
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	
	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
	
	

}
