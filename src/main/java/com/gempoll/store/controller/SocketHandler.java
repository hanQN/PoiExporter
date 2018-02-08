package com.gempoll.store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SocketHandler implements Runnable {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	SocketHandler(Socket socket){
		try {
			this.socket = socket;
			reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"GB2312"));
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("------welcome------");
			writer.println("------welcome------");
			writer.println("------welcome------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			//读取数据，这里只能读取一行String
			String line = reader.readLine();
			System.err.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//最后关闭socket
			try {
				if(socket!=null)socket.close();
				if(reader!=null)reader.close();
				if(writer!=null)writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new SocketServerDemo();
	}
}
