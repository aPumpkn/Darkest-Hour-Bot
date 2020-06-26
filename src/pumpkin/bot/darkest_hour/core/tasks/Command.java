package pumpkin.darkest_dawn.core.tasks;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import pumpkin.darkest_dawn.core.Listener;
import pumpkin.darkest_dawn.core.utils.BotFile;
import pumpkin.darkest_dawn.core.utils.Response;
import pumpkin.darkest_dawn.core.utils.Tool;

/* First, make resolve() return a String instead of a boolean.
 * Next, complete all commands.
 * 
 * 1) $char
 * 2) $wallet
 * 3) $dmp
 * 4) $shop
 * 5) $train
 * 6) $stats
 * 7) $prefs
 * 8) $help
 */

public class Command extends Response {
	
	private Listener listener = new Listener();
	private TextChannel channel = listener.channel;
	private NumberFormat number = NumberFormat.getInstance(Locale.US);
	private String[] command;
	private String message; // The actual Message object passed to the constructor
	private int args; // Amount of arguments in the command
	private int value; // The numeric argument in the command
	private String mention; // Mentioned user in the command
	private String user; // User who typed the command
	private String character;
	private String response;
	private boolean privateMsg;
	private boolean staff;
	private Tool tool = new Tool();
	private Guild guild;
	private BotFile botFile; // The default file to be edited.
	
	public Command(User user, Message message) {

		if (guild == null) guild = message.getGuild();
		
		this.user = user.getId();
		List<User> mentions = message.getMentionedUsers();

		if (mentions.isEmpty()) mention = "";
		else mention = mentions.get(0).getId();
		
		this.message = message.getContentRaw().trim().replaceAll(" +", " ");
		command = this.message.toLowerCase().split(" ");
		staff = String.valueOf(message.getMember().getRoles()).contains(":Staff(");
		args = command.length;
		character = "";
		privateMsg = false;
		
		response = getInternalErr();
		
		switch (command[0]) {
		
			/* 
			 * $char create <name> <@user> <type>
			 * $char create <name> <@user>
			 * $char delete <name>
			 * $char recycle <name>
			 * $char backup <name>
			 * $char backup *
			 * $char restore <name>
			 * $char list
			 */
			case "$char":
				switch (args) {
				
					case 5:
						character = "profiles/character/" + tool.capitalize(command[2]) + "/";
						botFile = new BotFile(character);
						if (command[1].equals("create")) {
							if (!botFile.exists()) {
								if (!(command[2].startsWith("<@!") || mention.isEmpty())) {
									if (staff) character(0);
									else response = "Error: You do not have permission to perform this action.";
								} else response = "Error: Expected an attached user mention to the command line on the fourth value.";
							} else response = "Error: Character \"" + tool.capitalize(command[3]) + "\" already exists.";
						} else response = "Error: The name of the command was recognized, but the syntax is incorrect.";
						break;
						
						
					case 4: 
						character = "profiles/character/" + tool.capitalize(command[2]) + "/";
						botFile = new BotFile(character);
						if (command[1].equals("create")) {
							if (!botFile.exists()) {
								if (!(command[2].startsWith("<@!") || mention.isEmpty())) { 
									if (staff) character(0);
									else response = "Error: You do not have permission to perform this action.";
								} else response = "Error: Expected an attached user mention to the command line on the fourth value.";
							} else response = "Error: Character \"" + tool.capitalize(command[2]) + "\" already exists.";
						} else response = "Error: The name of the command was recognized, but the syntax is incorrect.";
						break;
						
						
					case 3: 
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/");
						switch (command[1]) {
						
							case "delete":
								if (command[2].equals("*")) response = "Error: Using this sort of action with the universal identifier is prohibited.";
								else if (staff) {
									if (botFile.exists()) character(1);
									else response = "Error: Character \"" + character + "\" doesn't exist.";
								} else response = "Error: You do not have permission to perform this action.";
								break;
								
							case "restore":
								if (command[2].equals("*")) response = "Error: Using this sort of action with the universal identifier is prohibited.";
								else if (staff) {
									if (botFile.exists()) character(4);
									else response = "Error: Character \"" + character + "\" doesn't exist.";
								} else response = "Error: You do not have permission to perform this action.";
								break;
								
							case "recycle":
								if (command[2].equals("*")) response = "Error: Using this sort of action with the universal identifier is prohibited.";
								else if (staff) {
									if (botFile.exists()) character(6);
									else response = "Error: Character \"" + character + "\" doesn't exist.";
								} else response = "Error: You do not have permission to perform this action.";
								break;
								
							case "backup":
								if (command[2].equals("*")) character(3);
								else if (staff) {
									if (botFile.exists()) character(6);
									else response = "Error: Character \"" + character + "\" doesn't exist.";
								} else response = "Error: You do not have permission to perform this action.";
								break;
								
							case "info":
								if (botFile.exists()) character(7);
								else response = "Error: Character \"" + character + "\" doesn't exist.";
								break;
							
							default:
								response = "Error: The name of the command was recognized, but the syntax is incorrect.";
						
						} break;
						
						
						
					case 2: 
						switch (command[1]) {
						
							case "list":
								character(2);
								break;
								
							default:
								response = "Error: The name of the command was recognized, but the syntax is incorrect.";
							
						} break;
						
					default:
						response = "Error: The name of the command was recognized, but the syntax is incorrect.";
						
					
				} break;
				
				
				
				
			/* $wallet
			 * $wallet <char>
			 * $wallet add <char> <amount>
			 * $wallet remove <char> <amount>
			 * $wallet set <char> <amount>
			 */
			case "$wallet":
				switch (args) {
				
					case 1:
						character = new BotFile("profiles/player/registered/" + this.user + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/inventory.info");
						if (character.equals("None")) response = "Error: You haven't set your default character. Type `$prefs default <char>` to utilize this command.";
						else balance("wallet", 0);
						break;
						
					case 2:
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + tool.capitalize(command[1]) + "/inventory.info");
						if (botFile.exists()) balance("wallet", 0);
						else response = "Error: This character does not exist.";
						break;
						
					case 4:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/inventory.info");
						if (botFile.exists()) 
							if (tool.isInteger(command[3]))
								switch (command[1]) {
						
									case "set":
										balance("wallet", 1);
										break;
								
									case "add":
										balance("wallet", 2);
										break;
								
									case "remove":
										balance("wallet", 3);
										break;
										
									default:
										response = "Error: The name of the command was recognized, but the syntax is incorrect.";
										
								}
						
							else response = "Error: Last argument needs to be a numeric value.";
						else response = "Error: This character does not exist."; 
						break;
						
					default:
						response = "Error: The name of the command was recognized, but the syntax is incorrect.";
				
				} break;
				
				
				
				
				
			/* $dmp
			 * $dmp <char>
			 * $dmp add <char> <amount>
			 * $dmp remove <char> <amount>
			 * $dmp set <char> <amount>
			 */
			case "$dmp":
				switch (args) {
				
					case 1:
						botFile = new BotFile("profiles/player/registered/" + this.user + ".profile");
						balance("dmp", 0);
						break;
						
					case 2:
						botFile = new BotFile("profiles/player/registered/" + mention + ".profile");
						if (mention.isEmpty()) response = "Error: Expected an attached user mention on the second argument.";
						else balance("dmp", 0);
						break;
						
					case 4:
						botFile = new BotFile("profiles/player/registered/" + mention + ".profile");
						if (mention.isEmpty()) response = "Error: Expected an attached user mention on the second argument.";
						else if (!botFile.exists()) response = "Error: Character does not exist."; 
						else if (!tool.isInteger(command[3])) response = "Error: Last argument needs to be a numeric value.";
						else switch (command[1]) {
							
								case "set":
									balance("dmp", 1);
									break;
							
								case "add":
									balance("dmp", 2);
									break;
									
								case "remove":
									balance("dmp", 3);
									break;
									
								default:
									response = "Error: The name of the command was recognized, but the syntax is incorrect.";
							
						} break;
						
					default:
						response = "Error: The name of the command was recognized, but the syntax is incorrect.";
				
				} break;
				
				
				
				
			/* $shop list
			 * $shop buy <item>
			 * $shop buy <item> <char>
			 * $shop buy <item> <amount>
			 * $shop buy <item> <char> <amount>
			 * $shop sell <item>
			 * $shop sell <item> <char>
			 * $shop sell <item> <amount>
			 * $shop sell <item> <char> <amount>
			 */
			case "$shop":
				switch (args) {
				
					case 2:
						if (command[1].equals("list")) shop(0);
						else response = "Error: The name of the command was recognized, but the syntax is incorrect.";
						break;
						
					case 3:
						character = new BotFile("profiles/player/registered/" + this.user + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/inventory.info");
						if (botFile.exists()) switch (command[1]) {
							
							case "buy":
								tool.integer = -1;
								shop(1);
								break;
								
							case "sell":
								tool.integer = -1;
								shop(2);
								break;
								
							default:
								response = "Error: The name of the command was recognized, but the syntax is incorrect.";
						
						} else response = "Error: You haven't set your default character."; 
						break;
						
					case 4:
						boolean isInteger = tool.isInteger(command[3]);
						if (isInteger) character = new BotFile("profiles/player/registered/" + this.user + ".profile").find("default character");
						else character = tool.capitalize(command[3]);
						botFile = new BotFile("profiles/character/" + character + "/inventory.info");
						if (!botFile.exists()) response = "Error: Character does not exist.";
						else switch (command[1]) {
						
							case "buy":
								if (!isInteger) tool.integer = -1;
								shop(1);
								break;
								
							case "sell":
								if (!isInteger) tool.integer = -1;
								shop(2);
								break;
								
							default:
								response = "Error: Incorrect Syntax.";
					
						} break;
						
						
						
					case 5:
						isInteger = tool.isInteger(command[4]);
						character = tool.capitalize(command[3]);
						botFile = new BotFile("profiles/character/" + character + "/inventory.info");
						if (!botFile.exists()) response = "Error: Character does not exist.";
						else if (!isInteger) response = "Error: Expected a numeric value on the last argument.";
						else switch (command[1]) {
						
							case "buy":
								shop(1);
								break;
								
							case "sell":
								shop(2);
								break;
								
							default:
								response = "Error: Incorrect Syntax.";
					
						} break;
						
						
						
					default:
						response = "Error: Incorrect Syntax.";
						
				
				} break;
				
				
				
			case "$prefs":
				switch (args) {
				
					case 2:
						botFile = new BotFile("profiles/player/registered/" + user.getId() + ".profile");
						if (command[1].equals("list")) prefs(0);
						else;
						break;
						
					case 3:
						botFile = new BotFile("profiles/player/registered/" + user.getId() + ".profile");
						character = tool.capitalize(command[2]);
						if (command[1].equals("default")) prefs(1);
						else;
						break;
				
				} break;
				
				
				
			/* $stats <type>
			 * $stats <type> <char>
			 * $stats add <char> <stat>
			 * $stats add <char> <stat> <amount>
			 * $stats remove <char> <stat>
			 * $stats remove <char> <stat> <amount>
			 * $stats set <char> <stat> <amount>
			 * $stats create <char> <type> <name>
			 * $stats delete <char> <stat>
			 */
			case "$stats":
				switch (args) {
				
					case 2:
						character = new BotFile("profiles/player/registered/" + this.user + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						if (botFile.exists()) stats(tool.capitalize(command[1]), 0);
						else response = "You haven't set your default character yet!";
						break;
				
					case 3:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						if (botFile.exists()) stats(tool.capitalize(command[1]), 0);
						else response = "This character doesn't exist!";
						break;
						
					case 4:
						switch (command[1]) {
						
							case "up":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 3);
								else response = "This character doesn't exist!";
								break;
								
							case "down":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 3);
								else response = "This character doesn't exist!";
								break;
							
							case "delete":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 2);
								else response = "This character doesn't exist!";
								break;
								
							default:
								response = "Error: Incorrect Syntax.";
							
						} break;
						
					case 5:
						switch (command[1]) {

							case "up":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 3);
								else response = "This character doesn't exist!";
								break;
								
							case "down":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 3);
								else response = "This character doesn't exist!";
								break;
							
							case "add":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 5);
								else response = "This character doesn't exist!";
								break;
								
							case "remove":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 5);
								else response = "This character doesn't exist!";
								break;
								
							case "set":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 6);
								else response = "This character doesn't exist!";
								break;
								
							case "create":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								if (botFile.exists()) stats(tool.capitalize(command[3]), 1);
								else response = "This character doesn't exist!";
								break;
								
							default:
								response = "Error: Incorrect Syntax.";
						
						} break;
						
					default:
						response = "Error: Incorrect Syntax.";
						
				
				} break;
				
				
				
				
			/* $train
			 * $train <char>
			 * $train add <char> <stat> <amount>
			 * $train remove <char> <stat> <amount>
			 * $train set <char> <stat> <amount>
			 * $train reset <char>
			 * $train reset *
			 */
			case "$train":
				switch (args) {
				
					case 1:
						character = new BotFile("profiles/player/registered/" + user.getId() + ".profile").find("Default Character");
						botFile = new BotFile("profiles/character/" + character + "/training.info");
						if (botFile.exists()) train("", 0, 0);
						else response = "You haven't set your default character yet!";
						break;
						
					case 2:
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + character + "/training.info");
						if (botFile.exists()) train("", 0, 0);
						else response = "This character doesn't exist!";
						break;
				
					case 3:
						if (command[1].equals("reset")) {
							
							if (command[2].equals("*")) train("", 1, 0);
							else {
								
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/training.info");
								if (botFile.exists()) train("", 2, 0);
								else response = "This character does not exist!";
								
							}
						
						} break;
						
					case 5:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/training.info");
						if (botFile.exists()) switch (command[1]) {
						
							case "add":
								train(tool.capitalize(command[3]), 3, Integer.parseInt(command[4]));
								break;
								
							case "remove":
								train(tool.capitalize(command[3]), 3, Integer.parseInt(command[4]));
								break;
								
							case "set":
								train(tool.capitalize(command[3]), 4, Integer.parseInt(command[4]));
								break;
					
						} else response = "This character doesn't exist!"; 
						break;
						
					default:
						response = "Error: Incorrect Syntax.";
						
				
				} break;
				
			case "$rank":
				switch (args) {
				
				case 1:
					character = new BotFile("profiles/player/registered/" + user.getId() + ".profile").find("default character");
					botFile = new BotFile("profiles/character/" + character + "/general.info");
					rank(0);
					break;
					
				case 2:
					character = tool.capitalize(command[1]);
					botFile = new BotFile("profiles/character/" + character + "/general.info");
					rank(0);
					break;
					
				case 4:
					character = tool.capitalize(command[2]);
					botFile = new BotFile("profiles/character/" + character + "/general.info");
					if (command[1].equals("set")) rank(1);
					break;
				
				} break;
				
				
			/* $help
			 * $help <page>
			 */
			case "$help":
				switch (args) {
				
					case 1:
						listener.sendPrivateMessage(user, command1);
						listener.sendPrivateMessage(user, command2);
						listener.sendPrivateMessage(user, command3);
						listener.sendPrivateMessage(user, command4);
						response = "sent you a pm lol";
						break;
						
					default:
						
				
				} break;
				
			
				
				
			/* $inv
			 * $inv <char>
			 */
			case "$inv":
				switch (args) {
				
					case 1:
						response = "";
						character = new BotFile("profiles/player/registered/" + user.getId() + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/" + "inventory.info");
						if (botFile.exists()) {
							for (String index : botFile.getAll().split("\r\n")) {
								
								String[] split = index.split(": ");
								response += split[0] + ": **" + split[1] + "**\r\n";
								
							}
						} else {
							response = "You haven't set your default character!";
						} break;
						
					case 2:
						response = "";
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + character + "/" + "inventory.info");
						if (botFile.exists()) {
							for (String index : botFile.getAll().split("\r\n")) {
								
								String[] split = index.split(": ");
								response += split[0] + ": **" + split[1] + "**\r\n";
								
							}
						} else {
							response = "This character doesn't exist!";
						} break;
						
					default:
						response = "Error: Incorrect Syntax.";
				
				} break;
				
				
				
				
			/*$charges
			 * $charges <char>
			*/
			case "$charges":
				switch (args) {
				
					case 1:
						character = new BotFile("profiles/player/registered/" + user.getId() + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						if (botFile.exists()) {
							String[] split = botFile.find("charges").split("-");
							response = "Charges: **" + split[0] + "/" + split[2] + "**";
						} else {
							response = "You haven't set your default character!";
						} break;
						
					case 2:
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						if (botFile.exists()) {
							String[] split = botFile.find("charges").split("-");
							response = "Charges: **" + split[0] + "/" + split[2] + "**";
						} else {
							response = "Character doesn't exist!";
						} break;
						
					default:
						
				
				} break;
				
				
				
				
			/* $tech list
			 * $tech list <char>
			 * $tech <name>
			 * $tech <name> <char> 
			 * $tech create <char> <name>
			 * $tech delete <char> <name>
			 */
			case "$tech":
				switch (args) {
						
					case 2:
						character = new BotFile("profiles/player/registered/" + user.getId() + ".profile").find("Default Character");
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (command[1].equals("list")) tech("", 0);
						else if (botFile.exists()) tech(tool.capitalize(command[1]), 1);
						else response = "You haven't set up your default character!";
						break;
				
					case 3:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (!botFile.exists()) response = "This character doesn't exist!";
						else if (command[1].equals("list")) tech("", 0);
						else tech(tool.capitalize(command[1]), 1);
						break;
						
					case 4:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (!botFile.exists()) response = "This character doesn't exist!";
						else if (command[1].equals("delete")) tech(tool.capitalize(command[3]), 4);
						break;
						
					default:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (command[1].equals("create")) tech(tool.capitalize(command[3]), 3);
						break;
				
				} break;
				
				
				
				
				
			default: 
				response = "Command not recognized.";
			
		}
		
	}
	
	/* $char create <name> <@user> <type>
	 * $char create <name> <@user>
	 * $char delete <name>
	 * $char recycle <name>
	 * $char backup <name>
	 * $char backup *
	 * $char restore <name>
	 * $char list
	 */
	private void character(int operation) {
		
		switch (operation) {
		
			case 0: // $char create
				String typeStats = "";
				String powerType = "Custom";
				boolean hasType = args == 5;
				
				if (hasType) {
					
					powerType = tool.capitalize(command[4]);
					
					switch (powerType) {
					
						case "Shinobi":
							typeStats = getShinobiStats();
							break;
							
						case "Watch User":
							typeStats = getWatchUserStats();
							break;
							
						case "Osmosian":
							typeStats = getOsmosianStats();
							break;
							
						case "Magician":
							typeStats = getMagicianStats(); 
							break;
							
						case "Anodite":
							typeStats = getAnoditeStats();
							break;
							
						case "Pure Human":
							typeStats = getPureHumanStats();
							break;
							
						case "Pure Faunus":
							typeStats = getPureFaunusStats();
							break;
							
						case "Mixed Heritage":
							typeStats = getMixedHeritageStats();
							break;
							
						case "Halfa":
							typeStats = getHalfaStats();
							break;
							
						case "Ghost":
							typeStats = getGhostStats();
							break;
							
						case "Hunter":
							typeStats = getHunterStats();
							break;
							
						default: // Incorrect Type
							response = "Error: Character type \"" + command[4] + "\" is not recognized.";
							typeStats = "Error";
							
					
					}
					
				} if (!typeStats.equals("Error")) {
				
					botFile.createFolder();
					new BotFile(character + "backup/").createFolder();
					new BotFile(character + "general.info").create("Player: " + mention + "\r\n" + getGeneral() + "\r\nPower Type: " + powerType);
					new BotFile(character + "stats.info").create(getStats() + typeStats);
					new BotFile(character + "techniques.info").create();
					new BotFile(character + "training.info").create(getTraining());
					new BotFile(character + "inventory.info").create(getInventory());
					new BotFile(character + "general.info").copy(character + "backup/general.info");
					new BotFile(character + "stats.info").copy(character + "backup/stats.info");
					new BotFile(character + "techniques.info").copy(character + "backup/techniques.info");
					new BotFile(character + "training.info").copy(character + "backup/training.info");
					new BotFile(character + "inventory.info").copy(character + "backup/inventory.info");
					new BotFile(character + "backup/info.bak").create(Calendar.getInstance(Locale.US).getTime().toString());
					response = "Character \"" + tool.capitalize(command[2]) + "\" created.";
				
				} break;
				
			case 1: // $char delete <name>
				character = "profiles/character/" + tool.capitalize(command[2]) + "/";
				new BotFile(character + "general.info").delete();
				new BotFile(character + "stats.info").delete();
				new BotFile(character + "techniques.info").delete();
				new BotFile(character + "training.info").delete();
				new BotFile(character + "inventory.info").delete();
				new BotFile(character + "backup/general.info").delete();
				new BotFile(character + "backup/stats.info").delete();
				new BotFile(character + "backup/techniques.info").delete();
				new BotFile(character + "backup/training.info").delete();
				new BotFile(character + "backup/inventory.info").delete();
				new BotFile(character + "backup/info.bak").delete();
				new BotFile(character + "backup").delete();
				new BotFile(character).delete();
				response = "Deleted the character `" + tool.capitalize(command[2]) + "` from the bot.";
				break;
				
			case 2: // $char list
				response = new BotFile("profiles/character/").list();
				privateMsg = true;
				break;
				
			case 3: // $char backup *
				character = "profiles/character/";
				for (String index : new BotFile(character).list().split("\r\n")) {
					
					new BotFile(character + index + "/general.info").copy(character + index + "/backup/general.info");
					new BotFile(character + index + "/stats.info").copy(character + index + "/backup/stats.info");
					new BotFile(character + index + "/techniques.info").copy(character + index + "/backup/techniques.info");
					new BotFile(character + index + "/training.info").copy(character + index + "/backup/training.info");
					new BotFile(character + index + "/inventory.info").copy(character + index + "/backup/inventory.info");
					
				} response = "Created a backup for all characters.";
				break;
				
			case 4: // $char restore <name>
				character = "profiles/character/" + character + "/";
				new BotFile(character + "backup/general.info").copy(character + "general.info");
				new BotFile(character + "backup/stats.info").copy(character + "stats.info");
				new BotFile(character + "backup/techniques.info").copy(character + "techniques.info");
				new BotFile(character + "backup/training.info").copy(character + "training.info");
				new BotFile(character + "backup/inventory.info").copy(character + "inventory.info");
				response = "Restored this character to its backup.";
				break;
				
			case 5: // $char backup <name>
				character = "profiles/character/" + character + "/";
				new BotFile(character + "general.info").copy(character + "backup/general.info");
				new BotFile(character + "stats.info").copy(character + "backup/stats.info");
				new BotFile(character + "techniques.info").copy(character + "backup/techniques.info");
				new BotFile(character + "training.info").copy(character + "backup/training.info");
				new BotFile(character + "inventory.info").copy(character + "backup/inventory.info");
				response = "Created a new backup for this character.";
				break;
				
			case 6:
				character = "profiles/character/" + character + "/";
				BotFile general = new BotFile(character + "general.info");
				BotFile stats = new BotFile(character + "stats.info");
				BotFile tech = new BotFile(character + "techniques.info");
				BotFile train = new BotFile(character + "training.info");
				BotFile inv = new BotFile(character + "inventory.info");
				general.copy(character + "backup/general.info");
				stats.copy(character + "backup/stats.info");
				tech.copy(character + "backup/techniques.info");
				train.copy(character + "backup/training.info");
				inv.copy(character + "backup/inventory.info");
				new BotFile(character + "backup/info.bak").create(Calendar.getInstance(Locale.US).getTime().toString());
				general.delete();
				stats.delete();
				tech.delete();
				train.delete();
				inv.delete();
				response = "Saved current instance to backup, then deleted it. To restore this profile, type $char restore <char>.";
				break;
				
			case 7:
				List<String> info = new BotFile("profiles/character/" + character + "/general.info").find("player", "power type");
				String backup = new BotFile("profiles/character/" + character + "/backup/info.bak").getAll().trim();
				response = "Creator: **" + guild.getMemberById(info.get(0)).getEffectiveName() + "**\r\nPower Type: **" + info.get(1) + "**\r\nLast Backup: **" + backup + "**";
				
		}
		
	}
	
	private void balance(String type, int operation) {
		
		switch (operation) {
		
			case 0: // $wallet/dmp check
				response = number.format(Integer.parseInt(botFile.find(type)));
				break;
				
				
					  
			case 1: // $wallet/dmp set
				value = tool.integer;
				if (value > -1) response = number.format(botFile.edit(type, value));
				else response = "Error: Given value cannot be negative.";
				break;
				
				
				
			case 2: // $wallet/dmp add
				value = tool.integer;
				if (value > 0) {
					int original = Integer.parseInt(botFile.find(type));
					int result = value + original;
					response = original + " + " + value + " = " + number.format(botFile.edit(type, result));
				} else response = "Error: Given value must be greater than 0.";
				break;
				
				
				
			case 3: // $wallet/dmp remove
				value = tool.integer;
				if (value > 0) {		
					int original = Integer.parseInt(botFile.find(type));
					if (original > 0) {
						int result = original - value;
						if (result > -1) response = original + " - " + value + " = " + number.format(botFile.edit(type, result));
						else response = "(Result fell below the minimum value. It has been set to 0.) " + original + " - " + value + " = " + number.format(botFile.edit(type, 0));
					} else response = "Error: Original value is already at the minimum amount.";
				} else response = "Error: Given value must be greater than 0.";
				
		
		}
		
	}
	
	/* $shop list
	 * $shop buy <item>
	 * $shop buy <item> <char>
	 * $shop buy <item> <amount>
	 * $shop buy <item> <char> <amount>
	 * $shop sell <item>
	 * $shop sell <item> <char>
	 * $shop sell <item> <amount>
	 * $shop sell <item> <char> <amount>
	 */
	private void shop(int operation) {

		BotFile shop = new BotFile("resources/shop.list");
		int cost = 0;
		int quantity = 0;
		int wallet = -1;
		String stock = "";
		String item = "";
		
		if (args > 2) {
			
			item = tool.capitalize(command[2]);
			String line = shop.getLine(item);
		
			if (line.isEmpty()) {
				response = "Error: The item \"" + item + "\" does not exist.";
				return;
			}
				
			if (item.endsWith("s")) item = item.substring(0, item.length() - 1);
			
			int amount = -1;
			
			String[] keyval = line.split(": ");
			String[] splitdash = keyval[1].split("-");
			if (tool.integer != -1) amount = tool.integer;
			else amount = 1;
			cost = Integer.parseInt(splitdash[1]) * amount;
			quantity = Integer.parseInt(splitdash[0]) * amount;
			value = -1;
			wallet = -1;
			stock = "";
		
		}
		
		switch (operation) {
		
			case 0: // list
				response = "▬▬▬▬▬▬▬▬ **Shop** ▬▬▬▬▬▬▬▬";
				for (String index : shop.getAll().replaceAll("[\r\n]+", "\r\n").split("\r\n")) {
					String[] keyval = index.split(": ");
					String key = keyval[0].split("[|]")[1];
					String[] value = keyval[1].split("-");
					response += key + ": **$" + number.format(Integer.parseInt(value[1])) + "** per **" + value[0] + "**\r\n";
				}
				response = response.trim();
				privateMsg = true;
				break;
				
				
		
			case 1: // buy
				if (!new BotFile("profiles/character/" + character + "/general.info").find("player").equals(user)) {
					response = "You don't own this player!";
					return;
				}
				wallet = Integer.parseInt(botFile.find("wallet"));
				if (wallet - cost < 0) response = "Error: Insufficent funds.";
				else {
					wallet -= cost;
					botFile.edit("wallet", wallet);
					stock = botFile.find(item);
					if (stock.isEmpty()) {
						value = quantity;
						botFile.add(item + ": " + quantity);
					} else {
						value = Integer.parseInt(stock) + quantity;
						botFile.edit(item, value);
					}
					response = "Obtained **+" + quantity + "** " + item + "!\r\n\r\n" + item + ": **" + value + "**\r\nWallet: **$" + number.format(wallet) + "**";
				} break;
				
				
		
			case 2: // sell
				if (!new BotFile("profiles/character/" + character + "/general.info").find("player").equals(user)) {
					response = "You don't own this player!";
					return;
				}
				stock = botFile.find(item);
				if (stock.isEmpty()) response = "Error: You do not possess this item.";
				else {
					value = Integer.parseInt(stock) - quantity;
					if (value < 0) response = "Error: You do not have enough of this to sell.";
					else {
						wallet = Integer.parseInt(botFile.find("wallet")) + cost;
						if (value == 0) botFile.remove(item);
						else botFile.edit(item, value);
						botFile.edit("wallet", wallet);
						response = "Sold **-" + quantity + " " + item + "**.\r\n\r\n" + item + ": **" + value + "**\r\nWallet: **$" + number.format(wallet) + "**";
					}
				}
				
		}
		
	}
	
	/* $stats <type>
	 * $stats <type> <char>
	 * $stats add <char> <stat>
	 * $stats add <char> <stat> <amount>
	 * $stats remove <char> <stat>
	 * $stats remove <char> <stat> <amount>
	 * $stats set <char> <stat> <amount>
	 * $stats create <char> <type> <name>
	 * $stats delete <char> <stat>
	 */
	private void stats(String name, int operation) {
				
		switch (operation) {
		
			case 0: // $stats <type> OR $stats <type> <char>
				response = "";
				int i = 0;
				switch (name) {
				
					case "Base":
						for (String index : botFile.getList("b")) {
							
							if (i == 0) {
								
								i++;
								response += index + ": ";
							
							} else {
								
								i = 0;
								String[] statSplit = index.split("-");
								response += "**" + statSplit[0] + "**\r\n";
								
							}
						
						} break;

					case "Primary":
						for (String index : botFile.getList("p")) {
							
							if (i == 0) {
								
								i++;
								response += index + ": ";
							
							} else {
								
								i = 0;
								String[] statSplit = index.split("-");
								response += "**" + statSplit[0] + "/" + statSplit[2] + "**\r\n";
								
							}
						
						} break;

					case "Secondary":
						for (String index : botFile.getList("s")) {
							
							if (i == 0) {
								
								i++;
								response += index + ": ";
							
							} else {
								
								i = 0;
								String[] statSplit = index.split("-");
								response += "**" + statSplit[0] + "/" + statSplit[2] + "**\r\n";
								
							}
						
						} break;
						
					default:
						
						
				} break;
		
			case 1: // $stats create <char> <name> <type>
				// TODO: Make sure the stat does not exist.
				if (!botFile.find(name).isEmpty()) {
					response = "Error: A stat with this name already exists.";
					return;
				}
				
				String cat = tool.capitalize(command[4]);
				int hardCap = 0;
				int softCap = 0;
				int inc = 0;
				
				if (cat.equals("P") || cat.equals("Primary")) {
					
					hardCap = 100;
					softCap = 75;
					inc = 4;
					
				} else if (cat.equals("S") || cat.equals("Secondary")) {
					
					hardCap = 50;
					softCap = 40;
					inc = 3;
					
				} else {
					response = "Error: The category \"" + "\" is not valid. Please choose either `primary` or `secondary`.";
					return;
					
				}
				
				botFile.add("\r\n" + cat.substring(0, 1) + "|" + name + ": 0-" + inc + "-" + hardCap + "-" + softCap);
				response = "Stat added.";
				break;
				
			case 2: // delete
				// TODO: Make sure default stats cannot be deleted.
				if (botFile.find(name).isEmpty()) {
					response = "Error: This stat does not exist.";
					return;
				}
				botFile.remove(name);
				response = "Stat `" + name + "` deleted.";
				break;
				
			case 3: // up & down
				String statLine = botFile.getLine(name);
				
				if (statLine.isEmpty()) {
					response = "This stat does not exist!";
					return;
				}
				
				BotFile train = new BotFile("profiles/character/" + character + "/training.info");
				String rank = new BotFile("profiles/character/" + character + "/general.info").find("rank");
				String result = "";
				String[] line = statLine.split(": ");
				String[] stat = line[1].split("-");
				String category = line[0].split("[|]")[0];
				int original = Integer.parseInt(stat[0]);
				int reward = Integer.parseInt(stat[1]);
				int max = Integer.parseInt(stat[2]);
				int soft = Integer.parseInt(stat[3]);
				boolean other = false;
				boolean halved = false;
				int repeat = 1;
				
				if (args == 5) repeat = Integer.parseInt(command[4]);
				
				int multiplier = repeat;
				
				while (repeat != 0) {
					
					if (command[1].equals("down")) {
						
						reward -= reward * 2;
						String key = "";
						String[] statSplit = {};
						
						if (category.equals("B")) {

							List<String> types = train.find(name, "Base");
							String[] baseSplit = types.get(0).split("/");
							statSplit = types.get(1).split("/");
							
							if (Integer.parseInt(statSplit[0]) + multiplier > 2) {
								response = "You cannot remove too many stats, else the training cap will be exceeded.";
								return;
							} else if (Integer.parseInt(baseSplit[0]) + multiplier > 4) {
								response = "You cannot remove too many stats, else the training cap will be exceeded!";
								return;
							}
							
							train.edit(name, String.valueOf(Integer.parseInt(statSplit[0]) + 1) + "/" + statSplit[1],
									  "Base", String.valueOf(Integer.parseInt(baseSplit[0]) + 1) + "/" + baseSplit[1]);
							
						} else { 
							
							if (category.equals("P")) key = "Primary";
							else if (category.equals("S")) key = "Secondary";
							else {
								other = true;
								key = name;
							}
							
							statSplit = train.find(key).split("/");
							
							if (Integer.parseInt(statSplit[0]) + multiplier > Integer.parseInt(statSplit[1])) {
								response = "You don't have enough trainings of this stat for this request.";
								return;
							}
							
							train.edit(key, String.valueOf(Integer.parseInt(statSplit[0]) + 1) + "/" + statSplit[1]);
							
						}
						
					} else {
						
						String key = "";
						String[] statSplit = {};
						
						if (category.equals("B")) {

							List<String> types = train.find(name, "Base");
							String[] baseSplit = types.get(0).split("/");
							statSplit = types.get(1).split("/");
							
							if (Integer.parseInt(statSplit[0]) - multiplier < 0) {
								response = "You don't have enough trainings of this stat for this request.";
								return;
							} else if (Integer.parseInt(baseSplit[0]) - multiplier < 0) {
								response = "You don't have enough trainings of Base stats for this request.";
								return;
							}
							
							train.edit(name, String.valueOf(Integer.parseInt(statSplit[0]) - 1) + "/" + statSplit[1],
									  "Base", String.valueOf(Integer.parseInt(baseSplit[0]) - 1) + "/" + baseSplit[1]);
							
						} else { 
							
							if (category.equals("P")) key = "Primary";
							else if (category.equals("S")) key = "Secondary";
							else {
								other = true;
								key = name;
							}
							
							statSplit = train.find(key).split("/");
							
							if (Integer.parseInt(statSplit[0]) < multiplier) {
								response = "You don't have enough trainings of this stat for this request!";
								return;
							}
							
							train.edit(key, String.valueOf(Integer.parseInt(statSplit[0]) - 1) + "/" + statSplit[1]);
							
						}
						
					} if (!other) {
						
						if (rank.equals("Academy")) reward = 2;
						else if (max == 640) {
							if ((rank.equals("Initiate") && original > 79) ||
								(rank.equals("Captain") && original > 159) ||
								(rank.equals("General") && original > 319)) reward = 5;
							
						} else if (original >= soft) reward = 2;
					
					}
					
					result = String.valueOf(original + reward);
					botFile.edit(name, result + "-" + stat[1] + "-" + max + "-" + stat[3]);
					repeat--;
				}
				reward *= multiplier;
				response = name + ": " + original + " + " + reward + " = " + result;
				break;
				
			case 5: // add & remove
				line = botFile.getLine(name).split(": ");
				stat = line[1].split("-");
				category = line[0].split("[|]")[0];
				original = Integer.parseInt(stat[0]);
				max = Integer.parseInt(stat[2]);
				reward = Integer.parseInt(command[4]);

				if (command[1].equals("remove")) reward -= reward * 2;
				
				result = String.valueOf(original + reward);
				botFile.edit(name, result + "-" + stat[1] + "-" + max + "-" + stat[3]);
				response = name + ": " + original + " + " + reward + " = " + result;
				break;
				
			case 6: // set
				line = botFile.getLine(name).split(": ");
				stat = line[1].split("-");
				category = line[0].split("[|]")[0];
				original = Integer.parseInt(stat[0]);
				max = Integer.parseInt(stat[2]);
				reward = Integer.parseInt(command[4]);
				botFile.edit(name, reward + "-" + stat[1] + "-" + max + "-" + stat[3]);
				response = name + ": " + original + " = " + reward;
				break;
		
		}
		
	}
	
	private void tech(String name, int operation) {
		
		switch (operation) {
		
			case 0:
				String all = botFile.findAllKeys();
				if (all.isEmpty()) response = "This character does not have any techniques.";
				else response = all;
				break;
				
			case 1:
				String technique = botFile.findLines(name).replaceAll("%", ":");
				if (technique.isEmpty()) response = "This technique does not exist.";
				else response = technique;
				break;
		
			case 3: // create
				if (!botFile.find(name).isEmpty()) {
					response = "A technique with this name already exists.";
					return;
				}
				botFile.add(name + ": " + message.split("\'")[1].replaceAll(":", "%") + "\r\n\r\n");
				response = "Technique `" + name + "` added.";
				break;
				
			case 4:
				if (botFile.find(name).isEmpty()) {
					response = "This technique does not exist.";
					return;
				}
				botFile.removeLines(name);
				response = "Deleted the technique `" + name + "`.";
				break;
				
			default:
				
		}
		
	}
	
	private void train(String name, int operation, int amount) {
		
		switch (operation) {
		
			case 0:
				response = "";
				int i = 0;
				boolean newline = false;
				for (String index : botFile.getList("base", "perception", "control", "quickness", "brawn", "durability", "primary", "secondary", "charges")) {
					
					if (i == 0) {

						if (index.equalsIgnoreCase("durability")) newline = true;
						if (index.equalsIgnoreCase("base") || index.equalsIgnoreCase("primary") || index.equalsIgnoreCase("secondary") || index.equalsIgnoreCase("charges")) response += index + ": **";
						else response += "     • " + index + ": **";
						i++;
					
					} else {

						i = 0;
						response += index + "**\r\n";
						if (newline) {
							
							response += "\r\n";
							newline = false;
							
						}
						
					}
					
				} break;
				
			case 1:
				character = "profiles/character/";
				for (String index : new BotFile(character).list().split("\r\n")) new BotFile(character + index + "/training.info").create(getTraining());
				response = "Reset all trainings.";
				break;
			
			case 2:
				botFile.create(getTraining());
				response = "Reset trainings for `" + character + "`.";
				break;
				
			case 3: // add/remove
				response = "";
				String value = botFile.find(name);
				if (value.isEmpty()) {
					response = "Training does not exist.";
					return;
				}
				String[] valueSplit = value.split("/");
				int original = Integer.parseInt(valueSplit[0]);
				int cap = Integer.parseInt(valueSplit[1]);
				int reward = 0;
				if (command[1].equalsIgnoreCase("remove")) {
					if (original == 0) {
						response = "Trainings for this category have already reached the minimum value.";
						return;
					} 
					amount -= amount * 2;
					if (original + amount < 0) {
						response = "(Note: Result fell below the minimum value. It has been set to 0.)\r\n";
					} else reward = original + amount;
					response += "Removed `" + amount + "` points from this training category.";
				} else {
					if (cap == original) {
						response = "Trainings for this category have already reached the maximum value.";
						return;
					}
					if (amount + original > cap) {
						reward = cap;
						response = "(Note: Result exceeded the maximum value. It has been set to the cap.)\r\n";
					}
					response += "Trainings for this category have increased to " + reward + ".";
				}
				botFile.edit(name, reward + "/" + valueSplit[1]);
				break;
				
			case 4: // set
				String training = botFile.find(name);
				String max = training.split("/")[1];
				if (training.isEmpty()) {
					response = "Training does not exist.";
					return;
				}
				if (amount < 0) {
					response = "Cannot enter a negative value.";
					return;
				} else if (amount > Integer.parseInt(max)) {
					response = "Cannot exceed the maximum value.";
					return;
				}
				botFile.edit(name, amount + "/" + max);
				response = name + " = " + amount;
				break;
		
		}
		
	}
	
	private void rank(int operation) {
		
		switch (operation) {
		
			case 0:
				response = botFile.find("rank");
				break;
				
			case 1:
				String newRank = tool.capitalize(command[3]);
				BotFile stats = new BotFile("profiles/character/" + character + "/stats.info");
				String[] charges = stats.find("charges").split("-");
				if (newRank.equalsIgnoreCase("Academy")) charges[2] = "16";
				else if (newRank.equalsIgnoreCase("Initiate")) charges[2] = "30";
				else if (newRank.equalsIgnoreCase("Captain")) charges[2] = "50";
				else if (newRank.equalsIgnoreCase("General")) charges[2] = "70";
				else if (newRank.equalsIgnoreCase("Leader")) charges[2] = "70";
				else {
					response = "This rank does not exist.";
					return;
				}
				botFile.edit("rank", newRank);
				stats.edit("charges", charges[0] + "-" + charges[1] + "-" + charges[2] + "-" + charges[3]);
				response = "Changed rank to `" + newRank + "`.";
				break;
		
		}
		
	}
	
	private void prefs(int operation) {
		
		switch (operation) {
		
			case 0: // $prefs list
				response = "Yeah... Short list (for now).\r\n" + botFile.getLine("default character");
				break;
				
			case 1: // $prefs default <char>
				if (new BotFile("profiles/character/" + character + "/general.info").find("player").equals(user)) response = botFile.edit("default character", character);
				else response = "You can't use someone else's character as your default.";
				break;
		
		}
		
	}
	
	public boolean isPrivateMsg() {
		
		return privateMsg;
		
	}

	public List<String> getLongResponse() {
		
		return tool.sect(response);
		
	}
	
	public String getResponse() {
		
		return response;
		
	}

}
