package com.fatihsengun.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "order-create", groupId = "tradeflow_group")
    public void consume(OrderEventModel event){

        log.info("kafka mesajı yakaladı");
        log.info("sipariş no: {}", event.getOrderId());


    }
}
