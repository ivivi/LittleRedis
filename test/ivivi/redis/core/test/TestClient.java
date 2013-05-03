package ivivi.redis.core.test;

import java.io.IOException;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.pool.ClientPool;

public class TestClient {

	public static void main(String[] args) throws InterruptedException {
			
		ClientPool.initialPool();
		
		for(int i=0;;i++) {
			NIOClient client = ClientPool.getClient();
			client.getHandler().exists("redis" + i);
			System.out.println(i);
			
			ClientPool.closeClient(client);
			
			Thread.sleep(2000);
		}
		
		
	}

}
