package ivivi.redis.core.test;

import java.io.IOException;

import ivivi.redis.core.client.NIOClient;

public class TestClient {

	public static void main(String[] args) {
		try {
			NIOClient client = new NIOClient();
			client.initClient();
			client.start("localhost", 10001);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
