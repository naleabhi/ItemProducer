package com.item.storage.itemProducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.item.storage.itemProducer.model.Item;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ItemProducerServiceImpl implements ItemProducerService{

    Logger logger= LoggerFactory.getLogger(ItemProducerServiceImpl.class);
    @Autowired
    private KafkaTemplate itemProducerTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Override
    public void sendItemProducerRecord(Item itemRecord) throws JsonProcessingException {
        String item= mapper.writeValueAsString(itemRecord);

        ProducerRecord<Integer, String> itemProducerRecord= new ProducerRecord<>(topic, item);

        //This is the Asynchronous call because we are not waiting for response. To wait for response will use get method and it will become synchronous call
        ListenableFuture<SendResult<Integer, String>>listenableFuture =itemProducerTemplate.send(itemProducerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                failure(ex, item);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {

                success(result,item);

            }
        });

    }

    private void success(SendResult<Integer, String> result,String data) {
        logger.info("Item is successfully sent in Partition: {}, Data: {} and Offset: {}", result.getRecordMetadata().partition(),data, result.getRecordMetadata().offset());
    }

    private void failure(Throwable ex, String item) {
        logger.info("Error occurred while sending the item Data: {} and exception: {}", item, ex.toString());
    }
}
