package com.stackroute.swisit.documentparser.threadconsumer;

/*-------------- Importing Libraries ---------------*/
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.core.env.Environment;

import com.stackroute.swisit.documentparser.domain.CrawlerResult;
import com.stackroute.swisit.documentparser.service.MasterParserService;

public class KafkaConsumerThread extends Thread {
	private String topicName;
	private String brokerid;
	private KafkaConsumer<String, CrawlerResult> kafkaConsumer;
	private MasterParserService masterScannerService;

	public KafkaConsumerThread(String brokerid, String topicName, MasterParserService masterScannerService ){
		this.brokerid = brokerid;
		this.topicName = topicName;
		this.masterScannerService = masterScannerService;
	}

	public void run() {
		Properties configProperties = new Properties();
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerid);
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.stackroute.swisit.documentparser.serialization.CrawlerDeserializer");
		configProperties.put("group.id", "group-1");

		/*Figure out where to start processing messages from*/

		kafkaConsumer = new KafkaConsumer<String, CrawlerResult>(configProperties);
		kafkaConsumer.subscribe(Arrays.asList(topicName));

		/*Start processing messages*/

		while (true) {
			ConsumerRecords<String, CrawlerResult> records = kafkaConsumer.poll(1000);
			for (ConsumerRecord<String, CrawlerResult> record : records) {
				CrawlerResult crawlerResult = new CrawlerResult();
				crawlerResult = record.value();
				try {
					masterScannerService.parseDocument(crawlerResult);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	public KafkaConsumer<String,CrawlerResult> getKafkaConsumer() {
		return this.kafkaConsumer;
	}
}
