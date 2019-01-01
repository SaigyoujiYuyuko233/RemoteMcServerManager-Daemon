package hgk.saigyoujiyuyuko.rmsm.daemon.models.io;

public class ArrayToStringModel {
	
	/**
	 * @author SaigyoujiYuyuko
	 * 
	 * @param Array array 要转换的数组
	 * @param String fillers 每个元素之间填充的字符串
	 * 
	 * @usage 数组 => 字符串
	 * 
	 * @return String 转换后
	 */
	
	public String parseArray(String[] array,String fillers) {
		String string = "";
		
		for (int i = 0; i < array.length; i++) {
			string = string + array[i];
		}
		
		return string;
	}
	
	
	//多态...
	public String parseArray(int[] array,String fillers) {
		String string = "";
		
		for (int i = 0; i < array.length; i++) {
			string = string + String.valueOf(array[i]);
		}
		
		return string;
	}
	
}
