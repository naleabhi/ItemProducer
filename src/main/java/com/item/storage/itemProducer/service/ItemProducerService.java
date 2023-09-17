package com.item.storage.itemProducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item.storage.itemProducer.model.Item;

public interface ItemProducerService {

    public void sendItemProducerRecord(Item itemrecord) throws JsonProcessingException;
}
