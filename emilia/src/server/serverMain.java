package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverMain {
	int port; // ������ PORT ��ȣ
	ServerSocket serverSocket; // ���� ����

	public static void main(String[] args) {
		serverMain chatServer = new serverMain(8888);
		chatServer.start();
	}

	public serverMain(int port) {
		this.port = port;
	}

	public void start() {
		Socket socket = null;
		ServerThread thread = null;

		try {
			System.out.println("���� Start.");
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("���� ���۽� ����.");
		}

		while (true) {
			try {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress().getHostAddress() + " ����.");
				thread = new ServerThread(socket);
				new Thread(thread).start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("�϶��");
			}
		}
	}

}
