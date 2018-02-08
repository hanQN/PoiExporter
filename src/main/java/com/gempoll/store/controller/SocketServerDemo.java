package com.gempoll.store.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.jfinal.ext.route.ControllerBind;

public class SocketServerDemo {
	private ServerSocket serverSocket;
	public SocketServerDemo(){
		try {
			serverSocket = new ServerSocket(10000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//创建监听主线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					Socket socket = null;
					try {
						socket = serverSocket.accept();
						//当监听到客户端连接后，创建新线程传输数据，这样可以实现多个客户端同时访问
						new Thread(new SocketHandler(socket)).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
}
