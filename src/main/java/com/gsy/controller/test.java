package com.gsy.controller;

import com.carsimu.Car_data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
/*
1、生成vin码
2、vin码传入Car中，获取密钥
3、成功后生成数据
4、将数据传给服务器
 */
public class test {
    static int i = 0;

    public static void main(String[] args) throws IOException {
        for (; i < 20; i++) {
            new Thread(() -> {
                int f = Integer.parseInt(Thread.currentThread().getName());
                Car_data cd = new Car_data(f);
                //log.info(cd.getVinCode());
                Car car = new Car(cd.getVinCode());
                //log.info(cd.sendMessage(f / 4 == 0 ? 2 : f / 4));
                car.sendMessage(cd.sendMessage(f / 4 == 0 ? 2 : f / 4));
            }, String.valueOf(i)).start();
        }
    }
}
