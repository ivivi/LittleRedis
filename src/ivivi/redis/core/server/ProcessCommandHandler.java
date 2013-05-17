package ivivi.redis.core.server;

public interface ProcessCommandHandler {

	/*
	 * Base
	 */
	public void isBaseKeysCommand(final byte type, final String command);
	public void isBaseStringsCommand(final byte type, final String command);
	public void isBaseHashesCommand(final byte type, final String command);
	public void isBaseListsCommand(final byte type, final String command);
	public void isBaseSetsCommand(final byte type, final String command);
	public void isBaseSortedSetsCommand(final byte type, final String command);
	
	/*
	 * Ext
	 */
	public void isExtPubAndSubCommand(final byte type, final String command);
	public void isExtTranscationsCommand(final byte type, final String command);
	public void isExtScriptingCommand(final byte type, final String command);
	public void isExtConnectionCommand(final byte type, final String command);
	public void isExtServerCommand(final byte type, final String command);
}
