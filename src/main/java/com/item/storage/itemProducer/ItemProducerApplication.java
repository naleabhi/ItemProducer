package com.item.storage.itemProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Component
public class ItemProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemProducerApplication.class, args);
	}

}
