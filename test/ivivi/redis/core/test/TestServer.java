package ivivi.redis.core.test;

import ivivi.redis.core.server.NIOServer;

public class TestServer {

	public static void main(String[] args) {
		try {
			NIOServer server = new NIOServer();
			server.initServer(10001);
			server.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
