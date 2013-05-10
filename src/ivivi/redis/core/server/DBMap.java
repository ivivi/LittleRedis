package ivivi.redis.core.server;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBMap {
	
	private static final int capacity = 16;
	private static final List<HashMap<String, Object>> dbs = new ArrayList<HashMap<String, Object>>(capacity);
		
	private static final Map<SocketChannel,Integer> socket2db = new HashMap<SocketChannel, Integer>(); 
	
	public static final void registerSocket2db(SocketChannel sc,int index4db) {
		socket2db.put(sc, index4db);
	}
	
	public static final HashMap<String, Object> getDefaultDB() {
		return dbs.get(0);
	}
	
}
