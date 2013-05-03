package ivivi.redis.core.command;

public enum Command {
	EXISTS("exists");
	
	private String commandName;
	
	private Command(String commandName) {
		this.commandName = commandName;
	}
	
}
