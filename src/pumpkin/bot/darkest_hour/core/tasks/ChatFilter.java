package pumpkin.bot.darkest_hour.core.tasks;

import pumpkin.bot.darkest_hour.core.Listener;

/* Filters out and deletes specific messages sent to a Guild.
NOTE: filter doesn't run if the channel is marked with the • symbol. */
public class ChatFilter {
	
	private String[] phrases = {// Censored for your health};
	private String message; // Message from the user
	private String channel;
	
	public ChatFilter(String channel, String message) {

		this.channel = channel;
		this.message = message.trim().replaceAll(" +", " ").replaceAll("[^A-z +]", "").toLowerCase();
		
	}
	
	public boolean check() {
		
		for (String index : phrases) 
			if (!channel.startsWith("•") && (message.startsWith(index) || message.endsWith(" " + index) || message.contains(" " + index + " "))) return true;
		return false;
		
	}

}
