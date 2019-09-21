package com.dgsw.tcpcom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpClient {

	Socket socket = null;
	OutputStream os = null;
	OutputStreamWriter osw = null;
	BufferedWriter bw = null;

	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	private String ip = "";
	private int port = 1000; //포트 기본값은 1000으로 설정

	public TcpClient(String ip, int port) {
		
		super();
		
		this.ip = ip;
		this.port = port;
	}

	public void ClientRun(String data) {

		try {
			
			socket = new Socket(ip, port);
			os = socket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw); // 서버로 전송을 위한 OutputStream

			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr); // 서버로부터 Data를 받음

			bw.write(data);
			bw.newLine();
			bw.flush();

			String receiveData = "";
			receiveData = br.readLine(); // 서버로부터 데이터 한줄 읽음
			
			System.out.println("서버로부터 받은 데이터 : " + receiveData);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				
				bw.close();
				osw.close();
				os.close();
				br.close();
				isr.close();
				is.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}
}
