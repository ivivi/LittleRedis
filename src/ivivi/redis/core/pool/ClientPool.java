package ivivi.redis.core.pool;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.util.ConfigUtil;

import java.io.IOException;
import java.util.LinkedList;

public class ClientPool {
	
	private static LinkedList<NIOClient> linkedList;
	
	public static void initialPool(int poolSize) {
		linkedList = new LinkedList<NIOClient>();
		for(int i=0;i<poolSize;i++) {
			linkedList.push(initialClient());
		}
	}
	
	public static NIOClient initialClient() {
		try {
			NIOClient client = new NIOClient();
			client.initClient();
			client.start(ConfigUtil.getConfig("hostname"), ConfigUtil.getIntegerConfig("port"));
			
			return client;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return null;
	}
}
