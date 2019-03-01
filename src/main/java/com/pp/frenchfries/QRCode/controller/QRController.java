package com.pp.frenchfries.QRCode.controller;

import com.pp.frenchfries.QRCode.kafka.producer.Sender;
import com.pp.frenchfries.QRCode.model.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class QRController {

    @Autowired
    private Sender sender;


    @PostMapping(path = "/send/qr/{code}")
    public void sendFoo(@PathVariable String code) {
        System.out.println("Before send");
        System.out.println(code);
        this.sender.send(new QRCode(code));
        System.out.println("After send");
    }

}
