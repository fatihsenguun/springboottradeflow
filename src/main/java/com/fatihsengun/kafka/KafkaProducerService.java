package com.fatihsengun.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendMessage(String topic, Object event){
        log.info("Kafkaya mesaj gönderiliyor. Topic: {} | Data: {}",topic,event);

        kafkaTemplate.send(topic,event);
    }


}
