package com.item.storage.itemProducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item.storage.itemProducer.model.Item;
import com.item.storage.itemProducer.service.ItemProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemProducerController {

    @Autowired
    private ItemProducerService itemProducerService;

    @RequestMapping(name = "hello",method = RequestMethod.GET, value = "hello")
    public String testing()
    {
        return "Tested Ok!";
    }

    @RequestMapping(name = "publishItem",method = RequestMethod.POST, value = "publishItem")
    public ResponseEntity<Item> publishingItem(@RequestBody Item itemRecord) throws JsonProcessingException {
        itemProducerService.sendItemProducerRecord(itemRecord);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemRecord);
    }
}
