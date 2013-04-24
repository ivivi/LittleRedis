package ivivi.redis.core.util;

import java.util.HashMap;

public class ConfigUtil {
	
	private static final HashMap<String,String> configs = new HashMap<String,String>();
	private static boolean parsed = false;
	
	static {
		//TODO parse config file
		parseConfigFile();
		configs.put("hostname", "localhost");
		configs.put("port", "10001");
		configs.put("client.pool.size", "3");
		configs.put("timeout", "1000");
	}
	
	public static int getIntegerConfig(String key) {
		return Integer.parseInt(configs.get(key));
	}
	
	public static String getConfig(String key) {
		return configs.get(key);
	}
	
	private static void parseConfigFile() {
		
	}
}
