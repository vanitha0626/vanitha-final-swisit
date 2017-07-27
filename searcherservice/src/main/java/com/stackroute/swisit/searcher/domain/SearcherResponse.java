package com.stackroute.swisit.searcher.domain;

/*------ Import Libraries ------*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
@Document
/*---- Handling the response from Google Api -----*/
public class SearcherResponse {
	
	private String queries;

	/*---- Set array of values from google api json -----*/
	@JsonProperty("queries")
    public String getNestedObject(Map<String, Object> queries) {
        
        ArrayList<Object> al = (ArrayList<Object>) queries.get("request");
        Map<Object,Object> map = (Map<Object,Object>)al.get(0);
        setQueries((String) map.get("searchTerms"));
        System.out.println(map.get("SearchTerms"));
        return (String) map.get("searchTerms");
    }
	/*---- Set array of values from google api json -----*/
	@JsonProperty("items")
	private SearcherResult[] s;

    /*------- Getters and Setters for properties ------*/
    public String getQueries() {
        return queries;
    }
    public void setQueries(String queries) {
        this.queries = queries;
    }
	public SearcherResult[] getS() {
		return s;
	}
	public void setS(SearcherResult[] s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return "Responsive [s=" + Arrays.toString(s) + "]";
	}
}
