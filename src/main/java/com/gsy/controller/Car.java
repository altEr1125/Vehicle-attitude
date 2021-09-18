package com.gsy.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Car {
    CarHandshakeTool cht;
    String vin;

    public Car(String vinCode) {
        this.vin = vinCode;
        this.cht = new CarHandshakeTool();
        Connect conn = new Connect(cht, vin);
        Map<String, String> cipher_map = conn.get_param(vinCode);
        conn.conn_fh(cipher_map);
    }

    public void sendMessage(String message) throws Exception {
        String encryptMessage = this.cht.encrypt_sendMessage(message);
        Connect conn = new Connect(cht, vin);
        conn.sendMessage(encryptMessage);
    }
}
