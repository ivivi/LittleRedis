package ivivi.redis.core.command;

/*
 * it is a mapping for all commands which are covered by "http://redis.io/commands#generic"
 */
public interface KeysInter {
	
	public void exists(final String key);
}
