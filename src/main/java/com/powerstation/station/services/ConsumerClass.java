package com.powerstation.station.services;

import com.powerstation.commonlibrary.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerClass {

    private ProvisioningServiceImpl provisioningService;
    private static final Logger logger = LoggerFactory.getLogger(ConsumerClass.class);
    private static final String PROVISION_TOPIC = "PROVISIONING";

    public ConsumerClass(ProvisioningServiceImpl provisioningService){
        this.provisioningService = provisioningService;
    }

    @KafkaListener(topics = PROVISION_TOPIC)
    public void  consume(OrderMessage message) throws InterruptedException {


        logger.info("MS_PROVISIONING: Consumed message "+message);

        provisioningService.getAvailableBank(message);


    }


}
