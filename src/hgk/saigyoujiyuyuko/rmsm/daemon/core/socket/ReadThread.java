package hgk.saigyoujiyuyuko.rmsm.daemon.core.socket;

import java.io.IOException;
import java.net.Socket;

import hgk.saigyoujiyuyuko.rmsm.daemon.var.Var;

public class ReadThread implements Runnable {
	private Socket socket;
	
	//thread class var
	private WriteThread writeThread;
	
	public ReadThread(Socket socket,WriteThread writeThread) {
		this.socket = socket;
		
		this.writeThread = writeThread;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		//获取客户端信息
		String ip = socket.getInetAddress().getHostAddress();
		int port = socket.getPort();
		String connectionInfo = ip + ":" + port;
		
		try {
			
			int in;
			String message = "";
			while ((in =socket.getInputStream().read()) != -1) {
				//获取客户端发送的数据
				//转换字符串 + 编码
				message = message + (char)in;
				message = new String(message.getBytes("UTF-8"));
				
				
				/**
				 * 信息类型判断
				 */
				
				//如果头信息是验证的话
				if (message.contains("[auth]") == true && message.contains("[end]") == true) {
					//去掉标记
					message = message.replace("[auth]", "");
					message = message.replace("[end]", "");
					
					//验证密码
					if (message.equals(Var.password) == false) {
						this.socket.getOutputStream().write("Authentication failed! Please check your password![end]".getBytes("UTF-8"));
						Var.loggerModel.info("Authentication failed[" + connectionInfo + "]", Var.WARN);
						socket.close();
					}else {
						message = "";
						
						this.socket.getOutputStream().write("Authentication Successful![end]".getBytes("UTF-8"));
						Var.loggerModel.info("Authentication Successful[" + connectionInfo + "]", Var.WARN);
					}
					
				}
				
				//普通信息
				if (message.contains("[auth]") == false && message.contains("[end]") == true) {
					//清除头信息
					message = message.replace("[end]", "");
					
					//发送到容器
					Var.container.send(message);
					//this.socket.getOutputStream().write((message + "[end]").getBytes("UTF-8"));
					//System.out.println(message);
					
					//重置
					message = "";
				}
				
				
				/**
				 * 结束了
				 */
			}
			
			//循环结束就代表客户端断开连接
			try {
				Var.loggerModel.info("Connection closed by client [" + connectionInfo + "]",Var.INFO); 
				
				new Thread(this.writeThread).stop();
				this.socket.close();
				
				new Thread(this).stop();
			} catch (IOException e1) {}
			
		} catch (IOException e) {}
		
	}

}
