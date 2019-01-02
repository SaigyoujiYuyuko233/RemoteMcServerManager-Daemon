package hgk.saigyoujiyuyuko.rmsm.daemon.core.socket;

import java.io.IOException;
import java.net.Socket;

public class PingPacketThread implements Runnable{
	private Socket socket;
	
	//thread class var
	
	public PingPacketThread(Socket socket) {
		this.socket =socket;
	}
	
	@Override
	public void run() {
		//等验证成功再发
		try {Thread.sleep(600);} catch (InterruptedException e2) {e2.printStackTrace();}
		
		//发送心跳包
		try {
			
			while (this.socket.isClosed() == false) {
				this.socket.getOutputStream().write("[Ping]".getBytes("UTF-8"));
				Thread.sleep(600);
			}
			
		} catch (IOException | InterruptedException e) {}
		
	}

}
