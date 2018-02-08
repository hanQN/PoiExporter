package com.gempoll.store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientDemo implements Runnable {
	
	private Socket socket;
	BufferedReader reader;
	private PrintWriter writer;
	public SocketClientDemo(){
		try {
			socket = new Socket("192.168.80.120", 3306);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GB2312"));
			writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("working..........");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			//这里可以读取所有行String
			String line, buffer  ="";
			while(!((line = reader.readLine())==null)){
				buffer += line;
				System.err.println(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("problem");
		}finally{
			try {
				if(socket!=null) socket.close();
				if(reader!=null) reader.close();
				if(writer!=null) writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new Thread(new SocketClientDemo()).start();
	}
}
