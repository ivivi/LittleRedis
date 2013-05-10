package ivivi.redis.core.command;

/*
 * it is a mapping for all commands which are covered by "http://redis.io/commands#generic"
 */
public interface KeysCommandsSet {
	
	public void del(final String... keys);
	
	public void dump(final String key);
	
	public void exists(final String key);
	
	public void expire(final String key, final int seconds);
	
	public void expireAt(final String key, final long unixTimestamp);
}
