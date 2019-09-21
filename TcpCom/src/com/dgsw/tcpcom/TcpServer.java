package com.dgsw.tcpcom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TcpServer {

	ServerSocket server = null;
	int port = 5300;
	Socket socket = null;

	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	public void ServerRun() throws IOException {

		
		try {
			server = new ServerSocket(port);
			while (true) {
				System.out.println("-------접속 대기중------");
				socket = server.accept(); // 클라이언트가 접속하면 통신할 수 있는 소켓 반환
				System.out.println(socket.getInetAddress() + "로 부터 연결요청이 들어옴");

				is = socket.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				// 클라이언트로부터 데이터를 받기 위한 InputStream 선언

				String data = null;
				data = br.readLine();
				System.out.println("클라이언트로 부터 받은 데이터:" + data);

				if ("A".equals(data)) {
					sound();
				}

				receiveData(data, socket); // 받은 데이터를 그대로 다시 보내기
				System.out.println("****** 전송 완료 ****");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				is.close();
				server.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void receiveData(String data, Socket socket) {
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {
			os = socket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			// 클라이언트로부터 데이터를 보내기 위해 OutputStream 선언

			bw.write(data); // 클라이언트로 데이터 전송
			bw.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				bw.close();
				osw.close();
				os.close();
				socket.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * wav파일을 출력하는 메소드
	 */
	private void sound() {

		File file = new File(
				"E:\\dontopen\\DGSW\\SW_narusha\\2-1\\WorkSpace\\TcpCom\\src\\com\\dgsw\\tcpcom//A_Fin_L1A_01.wav");

		System.out.println("파일 존재 : " + file.exists());

		try {

			AudioInputStream stream = AudioSystem.getAudioInputStream(file);

			Clip clip = AudioSystem.getClip();

			clip.open(stream);
			clip.start();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
