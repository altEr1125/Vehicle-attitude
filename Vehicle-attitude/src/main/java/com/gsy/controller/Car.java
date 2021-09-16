package com.gsy.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
    public void sendMessage(String message) {
        Connect conn = new Connect(cht, vin);
        conn.sendMessage(message);
    }
}
