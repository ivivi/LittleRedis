package ivivi.redis.core.test;

public class Steper {
	
	private static int i = 0;
	
	public synchronized static long getStep() {
		if(i>100) System.exit(1);
		i++;
		return System.currentTimeMillis();
	}
}
