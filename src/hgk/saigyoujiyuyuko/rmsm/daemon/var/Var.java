package hgk.saigyoujiyuyuko.rmsm.daemon.var;

import ch.ubique.inieditor.IniEditor;
import hgk.saigyoujiyuyuko.rmsm.daemon.models.io.ArrayToStringModel;
import hgk.saigyoujiyuyuko.rmsm.daemon.models.io.FileModel;
import hgk.saigyoujiyuyuko.rmsm.daemon.models.io.LoggerModel;

public class Var {
	
	//Static Var
	public static int INFO = 0;
	public static int WARN = 1;
	public static int ERROR = 2;
	
	public static String CONF_FILE = "Config.ini";
	
	//Class
	public static FileModel fileModel = new FileModel();
	public static LoggerModel loggerModel = new LoggerModel();
	public static ArrayToStringModel arrayToString = new ArrayToStringModel();
	
	//Jar class
	public static IniEditor iniEditor =new IniEditor();
	
	//system var
	public static String ip = "0.0.0.0";
	public static int port = 25560;
	public static String password = "Rmsmanager233";
}
