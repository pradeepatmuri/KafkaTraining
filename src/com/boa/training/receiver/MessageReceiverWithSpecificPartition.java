package com.boa.training.receiver;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class MessageReceiverWithSpecificPartition {
	
	public static void main(String[] args) {
		Properties props = new Properties();
		
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("group.id", "test-group");
		
		KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props);
		/*List<String> topicList = new ArrayList<>();
		topicList.add("my-topic");
		consumer.subscribe(topicList);*/
		List<TopicPartition> partitionList = new ArrayList<>();
		int partitionNumber = Integer.parseInt(args[0]);
		partitionList.add(new TopicPartition("new-topic", partitionNumber));
		consumer.assign(partitionList);
		System.out.println("Assigned to message topic");
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
			for(ConsumerRecord<String,String> record: records) {
				System.out.println("Message received with Key "+record.key()+" and value "+record.value()+" par "+record.partition());
			}
		}
		
 	}
}
