package ivivi.redis.core.pool;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.util.ConfigUtil;

import java.io.IOException;
import java.util.LinkedList;

public class ClientPool {
	
	private static final LinkedList<NIOClient> linkedList = new LinkedList<NIOClient>();
	private static final byte[] lock = new byte[0];
	private static final int timeout = ConfigUtil.getIntegerConfig("client.timeout");
	private static final int poolSize = ConfigUtil.getIntegerConfig("client.pool.size");
	
	public static void initialPool() {
		for(int i=0;i<poolSize;i++) {
			linkedList.push(initialClient());
		}
	}
	
	private static NIOClient initialClient() {
		try {
			NIOClient client = new NIOClient(ConfigUtil.getConfig("hostname"), 
					 						 ConfigUtil.getIntegerConfig("port"),
					 						 ConfigUtil.getIntegerConfig("socket.timeout"));
			client.initClient();
			
			return client;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return null;
	}
	
	/*
	 * Guarded timed pattern
	 */
	public static NIOClient getClient() {
		synchronized (lock) {
			long start = System.currentTimeMillis();
			for(;linkedList.size() <= 0;) {
				long now = System.currentTimeMillis();
				long rest = timeout - (now - start);
				if(rest <= 0) {//timed out
					return initialClient();
				}
				try {
					lock.wait(rest);
				} catch (InterruptedException e) {
					continue;
				}
			}
			
			return linkedList.pop();
		}
	}
	
	public static void closeClient(NIOClient client) {
		synchronized (lock) {
			if(linkedList.size() < poolSize) {
				linkedList.addLast(client);
			} else {
				client.closeClient();
				client = null;
			}
			
			lock.notifyAll();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		
		for(NIOClient client : linkedList) {
			client.closeClient();
		}
		
		super.finalize();
	}
}
