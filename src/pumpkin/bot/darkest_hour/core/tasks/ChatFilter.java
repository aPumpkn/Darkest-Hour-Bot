package pumpkin.darkest_dawn.core.tasks;

/* Filters out and deletes specific messages sent to a Guild.
NOTE: filter doesn't run if the channel is marked with the â€¢ symbol. */
public class ChatFilter {
	
	private String[] phrases = {/* Censored for your health lol */};
	private String message; // Message from the user
	
	public ChatFilter(String message) {

		this.message = message.trim().replaceAll(" +", " ").replaceAll("[^A-z +]", "").toLowerCase();
		
	}
	
	public boolean check() {
		
		for (String index : phrases) 
			if (message.equals(index) || 
				message.startsWith(index + " ") || 
				message.endsWith(" " + index) || 
				message.contains(" " + index + " "))
			return true;
		return false;
		
	}

}
