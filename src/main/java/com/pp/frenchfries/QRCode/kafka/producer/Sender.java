package com.pp.frenchfries.QRCode.kafka.producer;

import com.pp.frenchfries.QRCode.model.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Value("${kafka.topic.qrcode}")
    private String jsonTopic;

    @Autowired
    private KafkaTemplate<String, QRCode> kafkaTemplate;

    public void send(QRCode payload) {
        System.out.println(jsonTopic);
        LOGGER.info("sending payload='{}'", payload.toString());
        kafkaTemplate.send(jsonTopic, payload.getQrCode().toString(), payload);
    }


}