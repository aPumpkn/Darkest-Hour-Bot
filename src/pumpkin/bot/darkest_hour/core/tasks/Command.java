package pumpkin.darkest_dawn.core.tasks;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import net.dv8tion.jda.api.entities.Message;
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
	
	Listener listener = new Listener();
	private String[] command;
	private String message; // The actual Message object passed to the constructor
	private int args; // Amount of arguments in the command
	private int value; // The numeric argument in the command
	private String mention; // Mentioned user in the command
	private String user; // User who typed the command
	private String character;
	private String response;
	private boolean privateMsg;
	private Tool tool = new Tool();
	private BotFile botFile; // The default file to be edited.
	
	public Command(User user, Message message) {

		this.user = user.getId();
		List<User> mentions = message.getMentionedUsers();

		if (mentions.isEmpty()) mention = "";
		else mention = mentions.get(0).getId();
		
		this.message = message.getContentRaw().trim().replaceAll(" +", " ");
		command = this.message.toLowerCase().split(" ");
		character = "";
		privateMsg = false;
		args = command.length;
		
		response = "Lorem ipsum dolor sit amet...";
		
		switch (command[0]) {
		
			case "$test":
				break;
		
			/* 
			 * $char create <@user> <name> <type>
			 * $char delete <name>
			 * $char backup <name>
			 * $char backup *
			 * $char restore <name>
			 * $char list
			 */
			case "$char":
				switch (args) {
				
					case 5:
						character = "profiles/character/" + tool.capitalize(command[3]) + "/";
						if (command[1].equals("create")) character(0);
						else; // Error Handling: (Syntax Error)
						break;
						
						
					case 3: 
						character = "profiles/character/" + tool.capitalize(command[2]) + "/";
						switch (command[1]) {
						
							case "delete":
								character(1);
								break;
								
							case "restore":
								character(4);
								break;
								
							case "backup":
								if (command[2].equals("*")) character(3);
								else character(5);
								break;
							
							default:
								// Error Handling: (Syntax Error)
						
						} break;
						
						
						
					case 2: 
						switch (command[1]) {
						
							case "list":
								character(2);
								break;
							
							case "info":
								character(6);
								break;
								
							default:
								// Error Handling: (Syntax Error)
							
						}
						
						
					default:
						
						
					
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
						botFile = new BotFile("profiles/character/" + new BotFile("profiles/player/registered/" + this.user + ".profile").find("default character") + "/inventory.info");
						balance("wallet", 0);
						break;
						
					case 2:
						botFile = new BotFile("profiles/character/" + tool.capitalize(command[1]) + "/inventory.info");
						balance("wallet", 0);
						break;
						
					case 4:
						botFile = new BotFile("profiles/character/" + tool.capitalize(command[2]) + "/inventory.info");
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
								// Error Handling: (Syntax Error)
						
						} break;
						
					default:
						
				
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
						balance("dmp", 0);
						break;
						
					case 4:
						botFile = new BotFile("profiles/player/registered/" + mention + ".profile");
						switch (command[1]) {
						
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
								// Error Handling: (Syntax Error)
						
						} break;
						
					default:
						
				
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
						else; // Error Handling: (Syntax Error)
						break;
						
					case 3:
						botFile = new BotFile("profiles/character/" + new BotFile("profiles/player/registered/" + this.user + ".profile").find("default character") + "/inventory.info");
						switch (command[1]) {
							
							case "buy":
								shop(1);
								break;
								
							case "sell":
								shop(2);
								break;
								
							default:
								// Error Handling: (Syntax Error)
						
						} break;
						
					case 4:
						botFile = new BotFile("profiles/character/" + command[3] + "/inventory.info");
						switch (command[1]) {
						
							case "buy":
								shop(1);
								break;
								
							case "sell":
								shop(2);
								break;
								
							default:
								// Error Handling: (Syntax Error)
					
						} break;
						
						
						
					case 5:
						botFile = new BotFile("profiles/character/" + command[3] + "/inventory.info");
						switch (command[1]) {
						
							case "buy":
								shop(1);
								break;
								
							case "sell":
								shop(2);
								break;
								
							default:
								// Error Handling: (Syntax Error)
					
						} break;
						
						
						
					default:
						
				
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
						stats(tool.capitalize(command[1]), 0);
						break;
				
					case 3:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						stats(tool.capitalize(command[1]), 0);
						break;
						
					case 4:
						switch (command[1]) {
						
							case "up":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 3);
								break;
								
							case "down":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 3);
								break;
							
							case "delete":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 2);
								break;
								
							default:
								
							
						} break;
						
					case 5:
						switch (command[1]) {

							case "up":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 3);
								break;
								
							case "down":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 3);
								break;
							
							case "add":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 5);
								break;
								
							case "remove":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 5);
								break;
								
							case "set":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 6);
								break;
								
							case "create":
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/stats.info");
								stats(tool.capitalize(command[3]), 1);
								break;
								
							default:
								
						
						} break;
						
					default:
						
				
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
						train("", 0, 0);
						break;
						
					case 2:
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + character + "/training.info");
						train("", 0, 0);
						break;
				
					case 3:
						if (command[1].equals("reset")) {
							
							if (command[2].equals("*")) train("", 1, 0);
							else {
								
								character = tool.capitalize(command[2]);
								botFile = new BotFile("profiles/character/" + character + "/training.info");
								train("", 2, 0);
								
							}
						
						} break;
						
					case 5:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/training.info");
						switch (command[1]) {
						
						case "add":
							train(tool.capitalize(command[3]), 3, Integer.parseInt(command[4]));
							break;
							
						case "remove":
							train(tool.capitalize(command[3]), 3, Integer.parseInt(command[4]));
							break;
							
						case "set":
							train(tool.capitalize(command[3]), 4, Integer.parseInt(command[4]));
							break;
					
						} break;
						
					default:
						
				
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
			case "$help": // TODO:
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
						
						for (String index : botFile.getAll().split("\r\n")) {
							
							String[] split = index.split(": ");
							response += split[0] + ": **" + split[1] + "**\r\n";
							
						} break;
						
					case 2:
						response = "";
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + character + "/" + "inventory.info");
						
						for (String index : botFile.getAll().split("\r\n")) {
							
							String[] split = index.split(": ");
							response += split[0] + ": **" + split[1] + "**\r\n";
							
						} break;
						
					default:
						
				
				} break;
				
				
				
				
			/*$charges
			 * $charges <char>
			*/
			case "$charges":
				switch (args) {
				
					case 1:
						character = new BotFile("profiles/player/registered/" + user.getId() + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						String[] split = botFile.find("charges").split("-");
						response = "Charges: **" + split[0] + "/" + split[2] + "**";
						break;
						
					case 2:
						character = tool.capitalize(command[1]);
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						split = botFile.find("charges").split("-");
						response = "Charges: **" + split[0] + "/" + split[2] + "**";
						break;
						
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
						else;
						break;
				
					case 3:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (command[1].equals("list")) tech("", 0);
						else if (botFile.exists()) tech(tool.capitalize(command[1]), 1);
						else;
						break;
						
					case 4:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (command[1].equals("delete")) tech(tool.capitalize(command[3]), 4);
						break;
						
					default:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/techniques.info");
						if (command[1].equals("create")) tech(tool.capitalize(command[3]), 3);
						break;
				
				} break;
				
				
				
				
				
			default: 
			
		}
		
	}
	
	private void character(int operation) {
		
		switch (operation) {
		
			case 0: // $char create <@user> <name> <type>
				String typeStats = "";
				switch (tool.capitalize(command[4])) {
				
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
				
				}
				
				new BotFile(character).createFolder();
				new BotFile(character + "backup/").createFolder();
				new BotFile(character + "general.info").create("Player: " + mention + "\r\n" + getGeneral());
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
				break;
				
			case 1: // $char delete <name>
				new BotFile(character + "general.info").delete();
				new BotFile(character + "stats.info").delete();
				new BotFile(character + "techniques.info").delete();
				new BotFile(character + "training.info").delete();
				new BotFile(character + "inventory.info").delete();
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
					
				} break;
				
			case 4: // $char restore <name>
				new BotFile(character + "backup/general.info").copy(character + "general.info");
				new BotFile(character + "backup/stats.info").copy(character + "stats.info");
				new BotFile(character + "backup/techniques.info").copy(character + "techniques.info");
				new BotFile(character + "backup/training.info").copy(character + "training.info");
				new BotFile(character + "backup/inventory.info").copy(character + "inventory.info");
				break;
				
			case 5: // $char backup <name>
				new BotFile(character + "general.info").copy(character + "backup/general.info");
				new BotFile(character + "stats.info").copy(character + "backup/stats.info");
				new BotFile(character + "techniques.info").copy(character + "backup/techniques.info");
				new BotFile(character + "training.info").copy(character + "backup/training.info");
				new BotFile(character + "inventory.info").copy(character + "backup/inventory.info");
				break;
				
		}
		
	}
	
	private void balance(String type, int operation) {
		
		switch (operation) {
		
			case 0: // $wallet/dmp check
				if (botFile.exists()) response = botFile.find(type);
				else; // Error Handling (File Error: Player doesn't exist.) OR (Preference Error: Player hasn't set their default character.)
				break;
					  
				
				
				
			case 1: // $wallet/dmp set
				if (tool.isInteger(command[3])) {
					
					value = tool.getInteger();
					
					if (value > -1) {
							
						if (botFile.exists()) response = "" + botFile.edit(type, value);
						else; // Error Handling (File Error: Player doesn't exist.) OR
						// Error Handling (File Error: Character Doesn't Exist.)
						
					} else; // Error Handling (Calculation Error: Value cannot be negative.)
					
				} else; // Error Handling (Calculation Error: Fourth parameter could not be indentified as an integer. You've either excluded a number from this argument, or the number was too large.)
				break;
				
				
				
			case 2: // $wallet/dmp add
				if (tool.isInteger(command[3])) {

					value = tool.getInteger();
					
					if (value > 0) {

						if (botFile.exists()) {

							int original = Integer.parseInt(botFile.find(type));
							int result = value + original;
							response = original + " + " + value + " = " + botFile.edit(type, result);
							
						} else; // Error Handling (File Error: Character Doesn't Exists)
				
					} else; // Error Handling (Calculation Error: Value must be greater than 0.)
					
				} else; // Error Handling (Calculation Error: Fourth parameter could not be indentified as an integer. You've either excluded a number from this argument, or the number was too large.)
				break;
				
				
				
			case 3: // $wallet/dmp remove
				if (tool.isInteger(command[3])) {
					
					value = tool.getInteger();
					
					if (value > 0) {

						if (botFile.exists()) {
															
							int original = Integer.parseInt(botFile.find(type));
							
							if (original > 0) {
							
								int result = original - value;
								
								if (result > -1) response = original + " - " + value + " = " + botFile.edit(type, result);
								else response = "(Result fell below the minimum value. It has been set to 0.) " + original + " - " + value + " = " + botFile.edit(type, result);
							
							} else; // Error Handling (Calculation Error: Result cannot be negative.)
							
						} // Error Handling (File Error: Character Doesn't Exists)
							
					} else; // Error Handling (Calculation Error: Fourth parameter could not be indentified as an integer. You've either excluded a number from this argument, or the number was too large.)
					
				} else; // Error Handling (Calculation Error: Value cannot be negative.)
		
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
		
		String item = tool.capitalize(command[2]);
		
		if (item.endsWith("s")) item = item.substring(0, item.length() - 1);

		BotFile shop = new BotFile("resources/shop.list");
		String line = shop.getLine(item);
		String[] keyval = line.split(": ");
		String[] splitdash = keyval[1].split("-");
		int amount = Integer.parseInt(command[4]);
		int cost = Integer.parseInt(splitdash[1]) * amount;
		int quantity = Integer.parseInt(splitdash[0]) * amount;
		int value = -1;
		
		switch (operation) {
		
			case 0: // list
				response = shop.findAllKeys();
				break;
		
			case 1: // buy
				botFile.edit("wallet", Integer.parseInt(botFile.find("wallet")) - cost);
				tool.isInteger(botFile.find(item));
				value = tool.integer;
				if (botFile.find(item).isEmpty()) botFile.add(item + ": " + quantity);
				else botFile.edit(item, value + quantity);
				break;
		
			case 2: // sell
				tool.isInteger(botFile.find(item));
				value = tool.integer;
				if (botFile.find(item).isEmpty()); // can't sell what you don't have
				else if (value - amount < 1); // don't have enough of the item
				else botFile.edit(item, value - amount);
				botFile.edit("wallet", Integer.parseInt(botFile.find("wallet")) + cost);
				
		
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
				String cat = command[4].substring(0, 1).toUpperCase();
				int hard = 0;
				int soft = 0;
				int inc = 0;
				
				if (cat.equals("P")) {
					
					hard = 100;
					soft = 75;
					inc = 4;
					
				} else if (cat.equals("S")) {
					
					hard = 50;
					soft = 40;
					inc = 3;
					
				} else; // ERROR
				botFile.add("\r\n" + cat + "|" + name + ": 0-" + inc + "-" + hard + "-" + soft);
				break;
				
			case 2: // delete
				botFile.remove(name);
				break;
				
			case 3: // up & down
				BotFile train = new BotFile("profiles/character/" + character + "/training.info");
				String rank = new BotFile("profiles/character/" + character + "/general.info").find("rank");
				String result = "";
				String[] line = botFile.getLine(name).split(": ");
				String[] stat = line[1].split("-");
				String category = line[0].split("[|]")[0];
				int original = Integer.parseInt(stat[0]);
				int reward = Integer.parseInt(stat[1]);
				int max = Integer.parseInt(stat[2]);
				boolean other = false;
				int repeat = 1;
				
				if (args == 5) repeat = Integer.parseInt(command[4]);
				
				int multiplier = repeat;
				
				while (repeat != 0) {
					
					if (command[1].equals("down")) {
						
						reward -= reward * 2;
						String key = "";
						
						if (category.equalsIgnoreCase("B")) {
							
							List<String> types = train.find(name, "Base");
							String[] baseSplit = types.get(0).split("/");
							String[] statSplit = types.get(1).split("/");
							train.edit(name, String.valueOf(Integer.parseInt(statSplit[0]) + 1) + "/" + statSplit[1], 
									  "Base", String.valueOf(Integer.parseInt(baseSplit[0]) + 1) + "/" + baseSplit[1]);
							
						} else if (category.equalsIgnoreCase("P")) {
	
							key = "Primary";
							String[] statSplit = train.find(key).split("/");
							train.edit(key, String.valueOf(Integer.parseInt(statSplit[0]) + 1) + "/" + statSplit[1]);
							
						} else if (category.equalsIgnoreCase("S")) {
							
							key = "Secondary";
							String[] statSplit = train.find(key).split("/");
							train.edit(key, String.valueOf(Integer.parseInt(statSplit[0]) + 1) + "/" + statSplit[1]);
							
						} else {
							
							other = true;
							String[] statSplit = train.find(name).split("/");
							train.edit(name, String.valueOf(Integer.parseInt(statSplit[0] + 1) + "/" + statSplit[1]));
							
						}
						
					} else {
						
						String key = "";
						
						if (category.equalsIgnoreCase("B")) {
							
							List<String> types = train.find(name, "Base");
							String[] baseSplit = types.get(0).split("/");
							String[] statSplit = types.get(1).split("/");
							train.edit(name, String.valueOf(Integer.parseInt(statSplit[0]) - 1) + "/" + statSplit[1], 
									  "Base", String.valueOf(Integer.parseInt(baseSplit[0]) - 1) + "/" + baseSplit[1]);
							
						} else if (category.equalsIgnoreCase("P")) {
	
							key = "Primary";
							String[] statSplit = train.find(key).split("/");
							train.edit(key, String.valueOf(Integer.parseInt(statSplit[0]) - 1) + "/" + statSplit[1]);
							
						} else if (category.equalsIgnoreCase("S")) {
							
							key = "Secondary";
							String[] statSplit = train.find(key).split("/");
							train.edit(key, String.valueOf(Integer.parseInt(statSplit[0]) - 1) + "/" + statSplit[1]);
							
						} else {
							
							String[] statSplit = train.find(name).split("/");
							train.edit(name, String.valueOf(Integer.parseInt(statSplit[0]) - 1) + "/" + statSplit[1]);
							
						}
						
					} if (!other) {
						
						if (rank.equals("Academy")) reward = 2;
						else if (max == 640) {
							if ((rank.equals("Initiate") && original > 79) ||
								(rank.equals("Captain") && original > 159) ||
								(rank.equals("General") && original > 319)) reward = 5;
							
						} else if ((max == 100 && original > 74) || (max != 100 && original > 39)) reward = 2;
					
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
				response = botFile.findAllKeys();
				break;
				
			case 1:
				response = botFile.findLines(name).replaceAll("%", ":");
				break;
		
			case 3: // create
				botFile.add(name + ": " + message.split("\'")[1].replaceAll(":", "%") + "\r\n\r\n");
				break;
				
			case 4:
				botFile.removeLines(name);
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
						else response += "     ○ " + index + ": **";
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
				break;
			
			case 2:
				botFile.create(getTraining());
				break;
				
			case 3: // add/remove
				String value = botFile.find(name);
				String[] valueSplit = value.split("/");
				int original = Integer.parseInt(valueSplit[0]);
				if (command[1].equalsIgnoreCase("remove")) amount -= amount * 2;
				int reward = original + amount;
				botFile.edit(name, reward + "/" + valueSplit[1]);
				response = name + ": " + original + " + " + amount + " = " + reward;
				break;
				
			case 4: // set
				botFile.edit(name, amount + "/" + botFile.find(name).split("/")[1]);
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
				botFile.edit("rank", newRank);
				BotFile stats = new BotFile("profiles/character/" + character + "/stats.info");
				String[] charges = stats.find("charges").split("-");
				if (newRank.equalsIgnoreCase("Academy")) charges[2] = "16";
				else if (newRank.equalsIgnoreCase("Initiate")) charges[2] = "30";
				else if (newRank.equalsIgnoreCase("Captain")) charges[2] = "50";
				else if (newRank.equalsIgnoreCase("General")) charges[2] = "70";
				else if (newRank.equalsIgnoreCase("Leader")) charges[2] = "70";
				stats.edit("charges", charges[0] + "-" + charges[1] + "-" + charges[2] + "-" + charges[3]);
				break;
		
		}
		
	}
	
	private void prefs(int operation) {
		
		switch (operation) {
		
			case 0: // $prefs list
				response = botFile.getLine("default character");
				break;
				
			case 1: // $prefs default <char>
				response = botFile.edit("default character", character);
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
