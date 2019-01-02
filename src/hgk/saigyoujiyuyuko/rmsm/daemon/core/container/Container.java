package hgk.saigyoujiyuyuko.rmsm.daemon.core.container;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hgk.saigyoujiyuyuko.rmsm.daemon.var.Var;


public class Container implements Runnable{
	Process process = null;
	BufferedReader bReader = null;
	BufferedWriter bWriter = null;
	
	@Override
	public void run() {
		try {
			
			/**
			 * 判断操作系统 选择相应的shell
			 */
			
			String osName = System.getProperties().getProperty("os.name");
			
			if (osName.contains("indows")) {
				this.process = new ProcessBuilder("cmd").start();
			}
			
			if (osName.contains("inux")) {
				this.process = new ProcessBuilder("/bin/bash").start();
			}
			
			
			/**
			 * 创建流
			 */
			
			this.bReader =new BufferedReader(new InputStreamReader(this.process.getInputStream(),"GBK"));
			this.bWriter =new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream(), "UTF-8"));
			
			//启动命令
			this.bWriter.write("cd Server \n");
			this.bWriter.flush();  //这个是重中之重啊!!!!
			this.bWriter.write(Var.command + "\n");
			this.bWriter.flush();  //这个是重中之重啊!!!!
			
			Var.loggerModel.info("Starting the server container successful!",Var.INFO);
		} catch (IOException e) {Var.loggerModel.info(e.getMessage() + "容器 I/O异常", Var.ERROR);e.printStackTrace();System.exit(-3);}
	}
	
	
	
	/**
	 * Getter and Setter
	 * @return
	 */
	
	public Process getProcess() {
		return this.process;
	}
	
	public BufferedReader getBR() {
		return this.bReader;
	}
	
	public void send(String cmd) throws IOException {
		this.bWriter.write(cmd+"\n");
		this.bWriter.flush();  //这个是重中之重啊!!!!
	}
	
	
}


