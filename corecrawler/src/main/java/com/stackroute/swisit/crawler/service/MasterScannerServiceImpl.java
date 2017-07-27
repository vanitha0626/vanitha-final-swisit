package com.stackroute.swisit.crawler.service;

import java.io.File;
/*-------Importing Libraries------*/
import java.io.IOException;
import java.util.*;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.stackroute.swisit.crawler.domain.CrawlerResult;
import com.stackroute.swisit.crawler.domain.SearcherResult;
import com.stackroute.swisit.crawler.domain.Term;
import com.stackroute.swisit.crawler.exception.DOMNotCreatedException;
import com.stackroute.swisit.crawler.exception.DocumentNotScannedException;
import com.stackroute.swisit.crawler.publisher.KafkaPublisherImpl;
import com.stackroute.swisit.crawler.repository.Neo4jRepository;

/* MasterScannerServiceImpl implements MasterScannerService that receives 
 * the searcher result and iterates through it to scan documents documents of each 
 * link received by passing the links to respective services
 * */
@PropertySource("classpath:application.yml")
@Service
public class MasterScannerServiceImpl implements MasterScannerService{
	
	@Autowired
	private Environment environment;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private DOMCreatorService domCreatorService;

	@Autowired
	public void setDomCreatorService(DOMCreatorService domCreatorService) {
		this.domCreatorService = domCreatorService;
	}
	

	/* Overriding method to scan for documents from searcher service result 
	 * to parse for keywords and structure and accordingly provide the intensity
	 * arguments- searcher result array of objects
	 * returns- string 
	 * */
	@Override
	public String scanDocument(SearcherResult searcherResult) throws JsonParseException, JsonMappingException, IOException {
		/* get the values from the application.yml */
		String producertopic = environment.getProperty("topic-toproducer");
		String brokerid = environment.getProperty("brokerid");
		try {
			if(searcherResult == null) 
				throw new DocumentNotScannedException("Document scanning failed");
		}catch (DocumentNotScannedException e) {
			logger.error("Exception" +e);
		}
			DOMCreatorServiceImpl domCreatorService = new DOMCreatorServiceImpl();
			Document document = null;
			try {
				document = domCreatorService.constructDOM(searcherResult.getLink());
				CrawlerResult crawlerResult = new  CrawlerResult();
				crawlerResult.setLink(searcherResult.getLink());
				crawlerResult.setTitle(searcherResult.getTitle());
				crawlerResult.setSnippet(searcherResult.getSnippet());
				crawlerResult.setConcept(searcherResult.getConcept());
				crawlerResult.setDocument(document.toString());
				KafkaPublisherImpl kafkaPublisherImpl = new KafkaPublisherImpl();
				kafkaPublisherImpl.publishMessage(producertopic,crawlerResult,brokerid);
			} catch (DOMNotCreatedException e) {
				e.printStackTrace();
			}

		
		return "sucess";
	}

}
