package hgk.saigyoujiyuyuko.rmsm.daemon.var;

import hgk.saigyoujiyuyuko.rmsm.daemon.models.io.FileModel;
import hgk.saigyoujiyuyuko.rmsm.daemon.models.io.LoggerModel;

public class Var {
	
	//Static Var
	public static int INFO = 0;
	public static int WARN = 1;
	public static int ERROR = 2;
	
	//Class
	public static FileModel fileModel = new FileModel();
	public static LoggerModel loggerModel = new LoggerModel();
	
}
