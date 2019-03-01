package com.pp.frenchfries.QRCode.kafka.consumer;

import com.pp.frenchfries.QRCode.model.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = "${kafka.topic.qrcode}", groupId = "qr-group")
    public void receive(@Payload QRCode data,
                        @Headers MessageHeaders headers) {
        LOGGER.info("received data='{}'", data);

        headers.keySet().forEach(key -> {
            LOGGER.info("{}: {}", key, headers.get(key));
        });
    }

}
