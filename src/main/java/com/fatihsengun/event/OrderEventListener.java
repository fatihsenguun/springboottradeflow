package com.fatihsengun.event;

import com.fatihsengun.kafka.KafkaProducerService;
import com.fatihsengun.kafka.OrderEventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {

    @Autowired
    private KafkaProducerService kafkaProducerService;
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedEvent(OrderEventModel event){
        try{
            kafkaProducerService.sendMessage("order-create",event);
        }catch (Exception e){
            System.err.println("Kafka error"+e.getMessage());
        }
    }

}
