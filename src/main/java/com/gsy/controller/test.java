package com.gsy.controller;

import com.carsimu.Car_data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
/*
1������vin��
2��vin�봫��Car�У���ȡ��Կ
3���ɹ�����������
4�������ݴ���������
 */
public class test {
    static int i = 0;

    public static void main(String[] args) throws IOException {
        for (; i < 1; i++) {
            new Thread(() -> {
                int f = Integer.parseInt(Thread.currentThread().getName());
                Car_data cd = new Car_data(f);
                //log.info(cd.getVinCode());
                String vinCode = cd.getVinCode();
                Car car = new Car(vinCode);
                //log.info(cd.sendMessage(f / 4 == 0 ? 2 : f / 4));
                try {
                    car.sendMessage(cd.sendMessage(f / 4 == 0 ? 2 : f / 4));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
