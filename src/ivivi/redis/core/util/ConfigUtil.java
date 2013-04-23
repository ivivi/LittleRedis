package ivivi.redis.core.util;

import java.util.HashMap;

public class ConfigUtil {
	
	private static final HashMap<String,String> configs = new HashMap<String,String>();
	private static boolean parsed = false;
	
	static {
		parseConfigFile();
	}
	
	public static int getIntegerConfig(String key) {
		
		return 0;
	}
	
	public static String getConfig(String key) {
		
		return null;
	}
	
	private static void parseConfigFile() {
		
	}
}
