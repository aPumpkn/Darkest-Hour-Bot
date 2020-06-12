package pumpkin.darkest_dawn.core;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import pumpkin.darkest_dawn.core.utils.BotFile;

// Core of the bot. Establishes a connection between discord and the bot.
public class Main {
	
	public static void main(String[] args) {
		
		JDA api = null;
		boolean quit = false;
		
		try {
			
			System.out.println("\r\n____________________________________________________\r\n\r\n[Darkest Dawn] API Initializing...\r\n");
			api = new JDABuilder(AccountType.BOT).setToken("lol no").build();
			api.addEventListener(new Listener());
			api.awaitReady();
			
		} catch (LoginException | InterruptedException e) {
			
			String error = e.toString();
			System.out.println("[Darkest Dawn] Error encountered.\r\n\r\n");
			System.err.println(error);
			System.out.println("\r\n[Darkest Dawn] Creating log file...\r\n\r\n");
			new BotFile("logs/error/" + Calendar.getInstance(Locale.US).getTime().toString().replace(":", "-") + ".err").create(error);
			quit = true;
			
		}
		
		if (!quit) System.out.println("____________________________________________________\r\n\r\n\r\n[Darkest Dawn] Bot is online.");

		Scanner scanner = new Scanner(System.in);
		
		while (!quit) {
			
			System.out.print("> ");
			
			switch (scanner.nextLine().trim().toLowerCase()) {
				
				case "quit":
					System.out.println("\r\n[Darkest Dawn] Closing current connection...");
					api.shutdown();
					quit = true;
					break;
				
				default:
					System.out.println("\r\nTo safely close the program, type 'quit'.\r\n");
				
			}
			
		}

		scanner.close();
		System.exit(-1);

	}

}
