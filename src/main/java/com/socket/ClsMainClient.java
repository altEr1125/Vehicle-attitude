package com.socket;

public class ClsMainClient {
	public static String tcp_thread(String url,int port,String sendMessage) {
		final String[] s_receive = new String[1];
		TcpClient c1 = new TcpClient() {

			@Override
			public void onReceive(SocketTransceiver st, String s) {
				s_receive[0] = s;
				System.out.println("Client1 Receive: " + s);
			}

			@Override
			public void onDisconnect(SocketTransceiver st) {
				System.out.println("Client1 Disconnect");
			}

			@Override
			public void onConnect(SocketTransceiver transceiver) {
				System.out.println("Client1 Connect");
			}

			@Override
			public void onConnectFailed() {
				System.out.println("Client1 Connect Failed");
			}
		};

		c1.connect(url, port);
		delay();
		while (true) {
			if (c1.isConnected()) {
				c1.getTransceiver().send(sendMessage);
			} else {
				break;
			}
			delay();
		}
		return s_receive[0];
	}

	static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}