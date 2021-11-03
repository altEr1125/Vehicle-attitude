package com.socket;

import com.gsy.controller.CarHandshakeTool;

import static java.lang.Thread.sleep;

public class Tcp_conn {
    String send_msg_ip = "192.168.12.134";
    int send_msg_port = 10010;
/*String send_msg_ip = "222.128.13.25";
    int send_msg_port = 10086;*/

    CarHandshakeTool cht;
    String vin;
    public Tcp_conn() {
/*        this.cht = cht;
        this.vin = vin;*/
    }
    public void sendMessage(byte[] array) throws InterruptedException {
        TcpClient c1 = new TcpClient() {
            @Override
            public void onReceive(SocketTransceiver st, String s) {
                System.out.println("Client Receive: " + s);
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnect(SocketTransceiver st) {
                // System.out.println("Client" + iCounter + " Disconnect");
            }

            @Override
            public void onConnect(SocketTransceiver transceiver) {
                System.out.println("Client Connect");
            }

            @Override
            public void onConnectFailed() {
                System.out.println("Client Connect Failed");
            }
        };
        c1.connect(send_msg_ip, send_msg_port);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (c1.isConnected()) {
            c1.getTransceiver().send(array);
            try {
                sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("send error" + array);
        }
    }


    public void sendMessage(String message) {
        System.out.println(message);
        TcpClient c1 = new TcpClient() {
            @Override
            public void onReceive(SocketTransceiver st, String s) {
                System.out.println("Client Receive: " + s);
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnect(SocketTransceiver st) {
                // System.out.println("Client" + iCounter + " Disconnect");
            }

            @Override
            public void onConnect(SocketTransceiver transceiver) {
                 System.out.println("Client Connect");
            }

            @Override
            public void onConnectFailed() {
                 System.out.println("Client Connect Failed");
            }
        };
        c1.connect(send_msg_ip, send_msg_port);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (c1.isConnected()) {
            c1.getTransceiver().send(message);
            try {
                sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("send error" + message);
        }
    }
}
