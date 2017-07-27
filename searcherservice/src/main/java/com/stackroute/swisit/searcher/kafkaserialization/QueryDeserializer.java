package com.stackroute.swisit.searcher.kafkaserialization;
/*------ Import Libraries -----*/
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.searcher.domain.SearcherJob;

/*---- Deserializing the searcherJob bean ----*/
public class QueryDeserializer implements Deserializer<SearcherJob>{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		
	}
	/*--- Deserializing message of SearcherJob ----*/
	@Override
	public SearcherJob deserialize(String topic, byte[] data) {
		ObjectMapper o=new ObjectMapper();
		SearcherJob c=null;
		try{
			logger.debug(data.toString());
			c=o.readValue(data,SearcherJob.class);
			} catch(Exception e){
			    logger.error("Error "+e);
		}
		return c;
	}
	@Override
	public void close() {
		
	}
}