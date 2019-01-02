package hgk.saigyoujiyuyuko.rmsm.daemon.core.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import hgk.saigyoujiyuyuko.rmsm.daemon.var.Var;

public class ServerSocketEntrypoint implements Runnable{
	private String ip;
	private int port;
	protected ServerSocket serverSocket;
	
	/**
	 * @param ip
	 * @param port
	 * @param password
	 */
	
	public ServerSocketEntrypoint(String ip,int port) {
		this.ip = ip;
		this.port = port;
	}
	
	
	public void run() {
		try {
			
			//创建ServerSocket
			this.serverSocket = new ServerSocket();
			
			//绑定信息
			this.serverSocket.bind(new InetSocketAddress(this.ip, this.port));
			
			//成功提示
			Var.loggerModel.info("Server is running at [" + Var.ip + ":" + Var.port + "]",Var.INFO);
			
			//开始接受连接
			while (true) {
				
				//获得socket
				Socket socket = serverSocket.accept();
				
				//启动读写线程
				WriteThread writeThread = new WriteThread(socket);
				ReadThread readThread = new ReadThread(socket,writeThread);
				PingPacketThread pingPacketThread = new PingPacketThread(socket);
				
				new Thread(readThread,"ReadThread").start();
				new Thread(writeThread,"WriteThread").start();
				new Thread(pingPacketThread,"PingPacketThread").start();
			}
			
		} catch (IOException e) { e.printStackTrace(); Var.loggerModel.info(e.getMessage(), Var.ERROR); System.exit(-4);}
	}
	
}
