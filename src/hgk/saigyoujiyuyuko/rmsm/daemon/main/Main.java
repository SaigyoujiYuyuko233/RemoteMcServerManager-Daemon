package hgk.saigyoujiyuyuko.rmsm.daemon.main;

import java.io.File;
import java.io.IOException;

import hgk.saigyoujiyuyuko.rmsm.daemon.core.container.Container;
import hgk.saigyoujiyuyuko.rmsm.daemon.core.socket.ServerSocketEntrypoint;
import hgk.saigyoujiyuyuko.rmsm.daemon.var.Var;

/**
 * Endpoint of all the program
 * 
 * @author SaigyoujiYuyuko
 * @time 2019/1/1 22:26:19
 */

public class Main {

	public static void main(String[] args) {
		// TODO 主入口
		
		//打印Logo
		System.out.println("------------------------------------------------------------------------");
		
		System.out.println(" ____  __  __ ____  __  __                                   \r\n" + 
				"|  _ \\|  \\/  / ___||  \\/  | __ _ _ __   __ _  __ _  ___ _ __ \r\n" + 
				"| |_) | |\\/| \\___ \\| |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|\r\n" + 
				"|  _ <| |  | |___) | |  | | (_| | | | | (_| | (_| |  __/ |   \r\n" + 
				"|_| \\_\\_|  |_|____/|_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_|   \r\n" + 
				"                                             |___/          \n ");
		
		System.out.println("+ Version " + Var.version);
		System.out.println("+ Copyright 2019 SaigyoujiYuyuko(3558168775) All rights reserved.");
		System.out.println("------------------------------------------------------------------------\n");
		
		/**
		 * 读取配置
		 */
		
		try {
			
			//配置文件是否存在
			File conf = new File(Var.CONF_FILE);
			File serverDir = new File("Server");
			
			if (serverDir.exists() == false) {
				serverDir.mkdirs();
			}
			
			if (conf.exists() == false) {
				
				conf.createNewFile();
				
				Var.fileModel.WriteFile(conf, "[Server]");
				Var.fileModel.WriteFile(conf, "# 绑定IP 默认: 0.0.0.0");
				Var.fileModel.WriteFile(conf, "ip=0.0.0.0");
				Var.fileModel.WriteFile(conf, "# 监听端口 默认: 25560");
				Var.fileModel.WriteFile(conf, "port=25560");
				Var.fileModel.WriteFile(conf, "# 连接口令 默认: Rmsmanager233");
				Var.fileModel.WriteFile(conf, "password=Rmsmanager233");
				Var.fileModel.WriteFile(conf, "# 服务器启动命令 默认: java -jar server.jar");
				Var.fileModel.WriteFile(conf, "command= java -jar server.jar");
				
				Var.loggerModel.info("未检测到配置文件! 系统已自动创建配置文件!", Var.WARN);
				
			}
			
			//存在则读取配置文件
			if (conf.exists() == true) {
				
				Var.iniEditor.load(new File(Var.CONF_FILE));
				
				Var.ip = Var.iniEditor.get("Server", "ip");
				Var.port = Integer.valueOf(Var.iniEditor.get("Server", "port"));
				Var.password = Var.iniEditor.get("Server", "password");	
				Var.command = Var.iniEditor.get("Server", "command");	
				
				Var.loggerModel.info("Finish to read the config! Loaded in: " + (System.currentTimeMillis() - Var.programStart) + "ms", Var.INFO);
			}
			
		} catch (IOException e) {Var.loggerModel.info(e.getMessage() + " 系统将全部使用默认配置!", Var.ERROR); e.printStackTrace();}
		
		
		/**
		 * 启动系统主要服务
		 */
		
		Var.loggerModel.info("Starting the server container...",Var.INFO);
		
		//启动容器
		Var.container = new Container();
		new Thread(Var.container,"Container").start();
		
		//启动socket 
		Var.loggerModel.info("Starting the socket...",Var.INFO);
		new Thread(new ServerSocketEntrypoint(Var.ip, Var.port),"SocketThread").start();
		
		//启动完成!
		Var.loggerModel.info("Starting the program successful! Loaded in: " + (System.currentTimeMillis() - Var.programStart) + "ms",Var.INFO);
	}

}
