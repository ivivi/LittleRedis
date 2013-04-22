package ivivi.redis.core.pool;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.util.ConfigUtil;

import java.io.IOException;
import java.util.LinkedList;

public class ClientPool {
	
	private static LinkedList<NIOClient> linkedList = new LinkedList<NIOClient>();
	private static final byte[] lock = new byte[0];
	
	public static void initialPool(int poolSize) {
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
	
	public static NIOClient getClient() {
		synchronized (lock) {
			for(;;) {
				if(linkedList.size() > 0) {
					return linkedList.pop();
				} else {
					try {
						lock.wait(1000);
					} catch (InterruptedException e) {//be interrupted by calling this thread.interrupt()
						continue;
					}
					return initialClient();
				}
			}
		}
	}
}
