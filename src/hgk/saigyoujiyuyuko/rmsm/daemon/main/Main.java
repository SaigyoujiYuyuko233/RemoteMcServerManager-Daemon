package hgk.saigyoujiyuyuko.rmsm.daemon.main;

import java.io.File;
import java.io.IOException;

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
		System.out.println();
		
		
		/**
		 * 读取配置
		 */
		
		try {
			
			//配置文件是否存在
			File conf = new File(Var.CONF_FILE);
			
			if (conf.exists() == false) {
				conf.createNewFile();
				
				Var.fileModel.WriteFile(conf, "[Server]");
				Var.fileModel.WriteFile(conf, "# 绑定IP 默认: 0.0.0.0");
				Var.fileModel.WriteFile(conf, "ip=0.0.0.0");
				Var.fileModel.WriteFile(conf, "# 监听端口 默认: 25560");
				Var.fileModel.WriteFile(conf, "port=25560");
				Var.fileModel.WriteFile(conf, "# 连接口令 默认: Rmsmanager233");
				Var.fileModel.WriteFile(conf, "password=Rmsmanager233");
				
				Var.loggerModel.info("未检测到配置文件! 系统已自动创建配置文件!", Var.WARN);
			}
			
			Var.iniEditor.load(new File(Var.CONF_FILE));
			
		} catch (IOException e) {
			Var.loggerModel.info(e.getMessage() + " 系统将全部使用默认配置!", Var.ERROR);
		}
		
	}

}
