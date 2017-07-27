package com.stackroute.swisit.crawler.publisher;

/*----------------- Importing Libraries ----------------*/
import com.fasterxml.jackson.core.JsonProcessingException;

import com.stackroute.swisit.crawler.domain.CrawlerResult;

/*--- Publisher interface that declares method to publish message via a messaging service ---*/
public interface Publisher {
	
	/*------------- Method to publish message via a messaging service--------------*/
	public void publishMessage(String topicName,CrawlerResult message,String brokerid) throws JsonProcessingException;
}
