package ivivi.redis.core.command;

import java.io.InputStream;
import java.io.OutputStream;

public interface AggregatedCommand extends KeysCommandsSet, StringsCommandsSet,
										 HashesCommandsSet, SetsCommandsSet {
	
	public abstract OutputStream getOS();
	public abstract InputStream getIS();
	
}
