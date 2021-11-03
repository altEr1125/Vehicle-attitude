package com.gsy.controller;

import com.gsy.util.Hex;
import com.socket.Tcp_conn;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class Car {
    CarHandshakeTool cht;
    String vin;

    public Car(String vinCode,int conn_error) {
        this.vin = vinCode;
        this.cht = new CarHandshakeTool();
        Connect conn = new Connect(cht, vin,conn_error);
        //不加密注释下面
/*        Map<String, String> cipher_map = conn.get_param(vinCode);
        if(conn_error!=1)
            conn.conn_fh(cipher_map,conn_error);*/
    }
}
