Completed the Main class.

The place where the JDA is initialized. Also includes terminal interaction for the user. This is used to safely log out and close the program.

package pumpkin.bot.darkest_hour.core;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import pumpkin.bot.darkest_hour.core.utils.BotFile;

// Core of the bot. Establishes a connection between discord and the bot.
public class Main {
	
	public static void main(String[] args) {
		
		Timer timer = new Timer("Main");
		JDA api = null;
		boolean quit = false;
		
		try {

			System.out.println("\r\n____________________________________________________\r\n\r\n[Darkest Hour] API Initializing...\r\n");
			api = new JDABuilder(AccountType.BOT).setToken("lol no").build();
			api.awaitReady();
			api.addEventListener(new Listener());
			
		} catch (LoginException | InterruptedException e) {
			
			String error = String.valueOf(e);
			new BotFile("logs/error/" + Calendar.getInstance(Locale.US).getTime().toString().replace(":", "-") + ".err").create(error);
			System.out.println("[Darkest Hour] Error encountered.\r\n\r\n");
			System.err.println(error);
			System.out.println("\r\n[Darkest Hour] Creating log file...\r\n\r\n");
			quit = true;
			
		}
		
		if (!quit) System.out.println("____________________________________________________\r\n\r\n\r\n[Darkest Hour] Bot is online.");
		else;

		Scanner scanner = new Scanner(System.in);
		
		while (!quit) {

			System.out.print("> ");
			
			switch (scanner.nextLine().trim().toLowerCase()) {
				
				case "quit":
					System.out.println("\r\n[Darkest Hour] Closing current connection...");
					api.shutdown();
					quit = true;
					break;
				
				default:
					System.out.println("\r\nTo safely close the program, type 'quit'.\r\n");
				
			}
			
		}

		scanner.close();
		System.out.println("[Darkest Hour] Shutting down in five seconds...");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				System.exit(-1);
				
			}
			
		}, 5000);

	}

}
