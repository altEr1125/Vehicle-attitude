package com.gsy.controller;
import com.carsimu.Car_data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Gen_data {
    //生成正常数据
    //conn_error表示第几次握手失败，-1表示成功
    //flag=-1,1,2,3,4分别表示生成全部合法数据、生成1、2、3、4号数据
    public List<String> gen_data(int conn_error,int flag) {
        List<String> sendmsg = new ArrayList<String>();
        int  t = (int) System.currentTimeMillis();
        Car_data cd = new Car_data(t);
        //log.info(cd.getVinCode());
        String vinCode = cd.getVinCode();
        Car car = new Car(vinCode,conn_error);
        log.info(cd.getVinCode());

        if(flag==-1)
            for(int i = 1;i<=4;i++) {
                sendmsg.add(cd.sendMessage(i,vinCode));
                System.out.println("车辆:" + vinCode + "发送的消息:" + sendmsg);
            }
        else {
            sendmsg.add(cd.sendMessage(flag,vinCode));
            System.out.println("车辆:" + vinCode + "发送的消息:" + sendmsg);
        }

        return sendmsg;
    }

    //生成非正常数据
    //conn_error表示第几次握手失败，-1表示成功
    //flag=-1,1,2,3,4分别表示生成全部合法数据、生成1、2、3、4号数据
    //异常信息目前只有车速、经纬度、obd
    public List<String> gen_data_error(int conn_error,int flag,int car_speed,int addr,int obd_error) {
        List<String> sendmsg = new ArrayList<String>();
        int  t = (int) System.currentTimeMillis();
        Car_data cd = new Car_data(t);
        //log.info(cd.getVinCode());
        String vinCode = cd.getVinCode();
        Car car = new Car(vinCode,conn_error);
        log.info(cd.getVinCode());
        String msg;
        if(flag==-1)
            for(int i = 1;i<=4;i++) {
                msg = cd.sendMessage_error(i,vinCode,car_speed,addr,obd_error);
                sendmsg.add(msg);
                System.out.println("车辆:" + vinCode + "发送的消息:" + msg);
            }
        else {
            msg = cd.sendMessage_error(flag,vinCode,car_speed,addr,obd_error);
            sendmsg.add(msg);
            System.out.println("车辆:" + vinCode + "发送的消息:" + msg);
        }

        return sendmsg;
    }
}
