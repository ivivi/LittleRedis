package ivivi.redis.core.server;

import java.nio.charset.Charset;

import ivivi.redis.core.command.impl.CommandType;
import ivivi.redis.core.util.SeparatorUtil;

public class ProcessCommand {
	
	private static final byte ensure = (byte)0x80;
	
	public static final void initProcessCommand(final byte type,final String command) {
		
		if(isBaseCommand(type)) 
			processBaseCommand(type, command);
		if(isExtCommand(type)) 
			processExtCommand(type, command);
	}
	
	public static final void processBaseCommand(final byte type,final String command) {
		//String[] cka = command.split(Character.toString(SeparatorUtil.SEPARATOR_MAIN));
		
		
	}
	
	public static final void processExtCommand(final byte type,final String command) {
		
	}
	
	private static final boolean isBaseCommand(final byte type) {
		return (type & ensure) == 0;
	}
	
	private static final boolean isExtCommand(final byte type) {
		return (type & ensure) != 0;
	}
	
	public static void main(String[] args) {
		System.out.println(isBaseCommand(CommandType.BASE_Hashes.toByte()));
		System.out.println(isBaseCommand(CommandType.BASE_Keys.toByte()));
		System.out.println(isBaseCommand(CommandType.BASE_SortedSets.toByte()));
		System.out.println(isBaseCommand(CommandType.EXT_Connection.toByte()));
		System.out.println(isBaseCommand(CommandType.EXT_Server.toByte()));
		System.out.println(isExtCommand(CommandType.BASE_Hashes.toByte()));
		System.out.println(isExtCommand(CommandType.EXT_Transcations.toByte()));
		
		System.out.println(CommandType.BASE_Keys.toByte() & 0xff);
	}
}
