package com.socket;

import com.gsy.util.Hex;

import java.nio.ByteBuffer;

public class Tcp_sendmsg {
    public void sendMessage(String message) throws Exception {
/*        byte[] encryptMessage = this.cht.encrypt_sendMessage(message);
        Tcp_conn conn = new Tcp_conn(cht, vin);
        //17字节的空Vin码
        byte[] vinBytes = vin.getBytes();

        ByteBuffer buffer = ByteBuffer.allocate(12 + vinBytes.length + 4 + encryptMessage.length);
        //魔数
        buffer.putInt(62283748);
        //1个字节的版本
        buffer.put((byte) 1);
        //1个字节的序列化方式
        buffer.put((byte) 1);
        //1字节的指令类型
        buffer.put((byte) 1);
        //4字节的序列号
        buffer.putInt(10);
        buffer.put((byte) 0);
//        System.out.println("VinCode length:" + vinBytes.length);
        buffer.put(vinBytes);

        buffer.putInt(encryptMessage.length);
        buffer.put(encryptMessage);
        byte[] array = buffer.array();*/
//        System.out.println(Arrays.toString(array));
//        System.out.println(array.length);

        //不加密
        byte[] encryptMessage = Hex.decode(message);

        Tcp_conn conn = new Tcp_conn();
        ByteBuffer buffer = ByteBuffer.allocate(encryptMessage.length);
        buffer.put(encryptMessage);
        byte[] array = buffer.array();


//        System.out.println(Arrays.toString(array));
        conn.sendMessage(array);

    }
}
