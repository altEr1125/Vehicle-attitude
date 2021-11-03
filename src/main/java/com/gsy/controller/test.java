package com.gsy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carsimu.Car_data;
import com.socket.Tcp_sendmsg;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
/*
1、生成vin码
2、vin码传入Car中，获取密钥
3、成功后生成数据
4、将数据传给服务器
 */

public class test {
    static int i = 0;
    static int f = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        Gen_data gd = new Gen_data();

        //正常车辆
        /*for (; i < 10 ; i++) {
            new Thread(() -> {
                List<String> sendmsg = gd.gen_data(-1,-1);
                Tcp_sendmsg tcp_sendmsg = new Tcp_sendmsg();
                for(String tmp:sendmsg)
                for(int j = 0;j<sendmsg.size();j++){
                    try {
                        tcp_sendmsg.sendMessage(sendmsg.get(j));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, String.valueOf(i)).start();
            Thread.sleep(200);
        }*/

        //超速
        for (; i < 1 ; i++) {
            new Thread(() -> {
                List<String> sendmsg = gd.gen_data_error(-1,-1,1,0,0);
                Tcp_sendmsg tcp_sendmsg = new Tcp_sendmsg();
                for(int j = 0;j<sendmsg.size();j++){
                    try {
                        tcp_sendmsg.sendMessage(sendmsg.get(j));
                        System.out.println("send:" + sendmsg.get(j));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, String.valueOf(i)).start();
            Thread.sleep(200);
        }

        //超出区域
        /*for (; i < 1 ; i++) {
            new Thread(() -> {
                List<String> sendmsg = gd.gen_data_error(-1,-1,0,1,0);
                Tcp_sendmsg tcp_sendmsg = new Tcp_sendmsg();
                for(String tmp:sendmsg)
                    for(int j = 0;j<sendmsg.size();j++){
                        try {
                            tcp_sendmsg.sendMessage(sendmsg.get(j));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }, String.valueOf(i)).start();
            Thread.sleep(200);
        }*/
 /*       //缺少握手
        //缺少第一次握手
        for (; i < 1 ; i++) {
            new Thread(() -> {
                gd.gen_data_error(1,-1,0,0,0);
            }, String.valueOf(i)).start();
            Thread.sleep(200);
        }
        //缺少第二次握手
        for (; i < 1 ; i++) {
            new Thread(() -> {
                gd.gen_data_error(2,-1,0,0,0);
            }, String.valueOf(i)).start();
            Thread.sleep(200);
        }*/
    }
}
