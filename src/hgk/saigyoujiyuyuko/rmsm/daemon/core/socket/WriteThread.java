package hgk.saigyoujiyuyuko.rmsm.daemon.core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import hgk.saigyoujiyuyuko.rmsm.daemon.core.container.Container;
import hgk.saigyoujiyuyuko.rmsm.daemon.var.Var;

public class WriteThread implements Runnable{
	private Socket socket;
	
	public WriteThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		//第一次连接发送给客户端旧的输出
		try {
			this.socket.getOutputStream().write(Var.oldOutput.getBytes("UTF-8"));
		} catch (IOException e1) {Var.loggerModel.info(e1.getMessage(), Var.ERROR);e1.printStackTrace();}
		
		//与container的 reader合并
		Container container = Var.container;
		BufferedReader bReader =container.getBR();
		
		//写服务器的输出
		try {	
			
			for(String line="";(line=bReader.readLine()) != null;) {

				System.out.println(line);
				
				//记录旧的输出
				Var.oldOutput = Var.oldOutput + line + "[end]";
				
				//发送给服务端
				if (this.socket != null) {
					this.socket.getOutputStream().write((line + "[end]").getBytes("GBK"));
				}
				
			}
			
		} catch (IOException e) {}
			
		
	}

}
