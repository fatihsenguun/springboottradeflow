package com.fatihsengun.kafka;

import com.fatihsengun.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private EmailSenderService emailSenderService;


    @KafkaListener(topics = "order-create", groupId = "tradeflow_group")
    public void consume(OrderEventModel event) {

        log.info("Mail Sending...");
        emailSenderService.sendOrderCreateMail(event);


    }

    @KafkaListener(topics = "order-status-changed", groupId = "tradeflow_group")
    public void updateConsume(OrderEventModel event) {

        log.info("Mail Sending...");
        emailSenderService.sendUpdateOrder(event);


    }
}
