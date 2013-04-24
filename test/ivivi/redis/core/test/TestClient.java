package ivivi.redis.core.test;

import java.io.IOException;

import ivivi.redis.core.client.NIOClient;
import ivivi.redis.core.pool.ClientPool;

public class TestClient {

	public static void main(String[] args) {
			
		ClientPool.initialPool();
		
		NIOClient client = ClientPool.getClient();
		
		client.getHandler().exists("redis");
	}

}
