package ivivi.redis.core.command;

public interface StringsInter {
	
	public void get(final String key);
	public void set(final String key, final String value);
}
