package pumpkin.darkest_dawn.core.tasks;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
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
	
	private String[] command;
	private int args; // Amount of arguments in the command
	private int value; // The numeric argument in the command
	private String mention; // Mentioned user in the command
	private String user; // User who typed the command
	private String character;
	private String response;
	private boolean privateMsg;
	private Tool tool = new Tool();
	private BotFile botFile;
	
	public Command(User user, Message message) {

		this.user = user.getId();
		List<User> mentions = message.getMentionedUsers();

		if (mentions.isEmpty()) mention = "";
		else mention = mentions.get(0).getId();
		
		command = message.getContentRaw().trim().replaceAll(" +", " ").toLowerCase().split(" ");
		character = "";
		response = null;
		privateMsg = false;
		args = command.length;
		
		switch (command[0]) {
		
			/* 
			 * $char create <@user> <name>
			 * $char delete <name>
			 * $char backup <name>
			 * $char backup *
			 * $char restore <name>
			 * $char list
			 */
			case "$char":
				switch (args) {
				
					case 4:
						character = "profiles/character/" + tool.capitalize(command[3]) + "/";
						if (command[1].equals("create")) character(0);
						else; // Error Handling: (Syntax Error)
						
						
						
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
						botFile = new BotFile("profiles/character/" + new BotFile("profiles/player/" + this.user + ".profile").find("default character") + "/general.info");
						balance("wallet", 0);
						break;
						
					case 2:
						botFile = new BotFile("profiles/character/" + tool.capitalize(command[1]) + "/general.info");
						balance("wallet", 0);
						break;
						
					case 4:
						botFile = new BotFile("profiles/character/" + tool.capitalize(command[2]) + "/general.info");
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
						botFile = new BotFile("profiles/player/" + this.user + ".profile");
						balance("dmp", 0);
						break;
						
					case 2:
						botFile = new BotFile("profiles/player/" + mention + ".profile");
						balance("dmp", 0);
						break;
						
					case 4:
						botFile = new BotFile("profiles/player/" + mention + ".profile");
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
						botFile = new BotFile("profiles/character/" + new BotFile("profiles/player/" + this.user + ".profile").find("default character") + "/general.info");
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
						botFile = new BotFile("profiles/character/" + command[2] + "/general.info");
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
						botFile = new BotFile("profiles/character/" + command[2] + "/general.info");
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
			case "$stats": // TODO:
				switch (args) {
				
					case 2:
						character = new BotFile("profiles/player/" + this.user + ".profile").find("default character");
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						stats(0, command[1]);
						break;
				
					case 3:
						character = tool.capitalize(command[2]);
						botFile = new BotFile("profiles/character/" + character + "/stats.info");
						stats(0, command[1]);
						break;
						
					case 4:
						switch (command[1]) {
						
							case "add":
								break;
								
							case "remove":
								break;
							
							case "delete":
								break;
								
							default:
								
							
						} break;
						
					case 5:
						switch (command[1]) {
						
							case "add":
								break;
								
							case "remove":
								break;
								
							case "create":
								break;
								
							default:
								
						
						} break;
						
					default:
						
				
				} break;
				
				
				
				
			/* $train
			 * $train <char>
			 * $train add <char> <stat> <amount>
			 * $train remove <char> <stat> <amount>
			 * $train reset <char>
			 * $train reset *
			 */
			case "$train": // TODO:
				switch (args) {
				
					case 1:
						break;
						
					case 2:
						break;
				
					case 3:
						if (command[1].equals("reset"));
						break;
						
					case 5:
						switch (command[1]) {
						
						case "add":
							break;
							
						case "remove":
							break;
					
						} break;
						
					default:
						
				
				} break;
				
				
				
				
			/* $help
			 * $help <page>
			 */
			case "$help": // TODO:
				switch (args) {
				
					case 1:
						break;
						
					case 2:
						break;
						
					default:
						
				
				} break;
				
			
				
				
			/* $inv
			 * $inv <char>
			 */
			case "$inv": // TODO:
				switch (args) {
				
					case 1:
						break;
						
					case 2:
						break;
						
					default:
						
				
				} break;
				
				
				
				
			/*$charges
			 * $charges <char>
			
			case "$charges":
				switch (args) {
				
					case 1:
						break;
						
					case 2:
						break;
						
					default:
						
				
				} break;
				
				
				
				
			/* $tech list
			 * $tech list <char>
			 * $tech <name>
			 * $tech <char> <name>
			 * $tech create <char> <name>
			 
			case "$tech":
				switch (args) {
				
					case 1:
						break;
						
					case 2:
						break;
				
					case 3:
						break;
						
					case 4:
						break;
						
					case 5:
						break;
						
					default:
						
				
				} break;
				
				
				
				
				
			default: */
			
		}
		
		response = "Lorem ipsum dolor sit amet...";
		
	}
	
	private void character(int operation) {
		
		switch (operation) {
		
			case 0: // $char create <@user> <name>
				new BotFile(character).create();
				new BotFile(character + "backup/").create();
				new BotFile(character + "general.info").create("Player: " + mention + "\r\n" + getGeneral());
				new BotFile(character + "stats.info").create(getStats());
				new BotFile(character + "techniques.info").create();
				new BotFile(character + "training.info").create(getTraining());
				new BotFile(character + "inventory.info").create(getInventory());
				new BotFile(character + "backup/general.info").create("Player: " + mention + "\r\n" + getGeneral());
				new BotFile(character + "backup/stats.info").create(getStats());
				new BotFile(character + "backup/techniques.info").create();
				new BotFile(character + "backup/training.info").create(getTraining());
				new BotFile(character + "backup/inventory.info").create(getInventory());
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
				
			case 6:
				// TODO: $char info
				
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
					
					if (value < 0) {
							
						if (botFile.exists()) response = "" + botFile.edit(type, value);
						else; // Error Handling (File Error: Player doesn't exist.) OR
						// Error Handling (File Error: Character Doesn't Exists)
						
					} else; // Error Handling (Calculation Error: Value cannot be negative.)
					
				} else; // Error Handling (Calculation Error: Fourth parameter could not be indentified as an integer. You've either excluded a number from this argument, or the number was too large.)
				break;
				
				
				
			case 2: // $wallet/dmp add
				if (tool.isInteger(command[3])) {
					
					value = tool.getInteger();
					
					if (value < 0) {
						
						if (botFile.exists()) {
							
							int original = Integer.parseInt(botFile.find(type));
							int result = value + original;
							response = original + " " + result + " = " + botFile.edit(type, result);
							
						} else; // Error Handling (File Error: Character Doesn't Exists)
				
					} else; // Error Handling (Calculation Error: Value cannot be negative.)
					
				} else; // Error Handling (Calculation Error: Fourth parameter could not be indentified as an integer. You've either excluded a number from this argument, or the number was too large.)
				break;
				
				
				
			case 3: // $wallet/dmp remove
				if (tool.isInteger(command[3])) {
					
					value = tool.getInteger();
					
					if (value < 0) {
							
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
		String id = keyval[0].split("|")[0];
		int cost = Integer.parseInt(splitdash[1]) * Integer.parseInt(command[2]);
		int quantity = Integer.parseInt(splitdash[0]) * Integer.parseInt(command[2]);
		String value = "";
		
		switch (operation) {
		
			case 0:
				response = shop.findAllKeys();
				break;
		
			case 1: // buy
				botFile.edit("wallet", Integer.parseInt(botFile.find("wallet")) - cost);
				botFile = new BotFile("profiles/character/" + tool.capitalize(command[3]) + "/inventory.info");
				value = botFile.find(id + item);
				if (value.isEmpty()) botFile.add(id + item + ": " + quantity);
				else botFile.edit(id + item, value + quantity);
				break;
		
			case 2: // sell
				botFile.edit("wallet", Integer.parseInt(botFile.find("wallet")) + cost);
				botFile = new BotFile("profiles/character/" + tool.capitalize(command[3]) + "/inventory.info");
				botFile.find(id + item);
				if (value.isEmpty()); // ERROR: You do not possess this item.
				else botFile.edit(id + item, Integer.parseInt(value) - quantity);
		
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
	private void stats(int operation, String type) {
		
		BotFile stats = new BotFile("resources/stats.list");
		BotFile train = new BotFile("profiles/character/" + tool.capitalize(command[2]) + "/train.info");
		String rank = botFile.find("rank");
		int original = Integer.parseInt(botFile.find(command[3]));
		int diminish = 0;
		int max = 0;
		int reward = 0;
		
		switch (operation) {
		
			case 0: // $stats <type> OR $stats <type> <char>
				int i = 0;
				switch (type) {
				
					case "base":
						for (String index : botFile.find("perception", "control", "quickness", "brawn", "durability")) {
						
							if (i == 0) response += "Perception: **" + index + "**\r\n";
							else if (i == 1) response += "Control: **" + index + "**\r\n";
							else if (i == 2) response += "Quickness: **" + index + "**\r\n";
							else if (i == 3) response += "Brawn: **" + index + "**\r\n";
							else response += "Durability: **" + index +"**";
							i++;
						
						} break;

					case "primary":
						for (String index : botFile.find("ninjutsu", "taijutsu", "medical", "fuinjutsu", "puppetry", "genjutsu", "explosives")) {
							
							if (i == 0) response += "Ninjutsu: **" + index + "/100**\r\n";
							else if (i == 1) response += "Taijutsu: **" + index + "/100**\r\n";
							else if (i == 2) response += "Medical: **" + index + "/100**\r\n";
							else if (i == 3) response += "Fuinjutsu: **" + index + "/100**\r\n";
							else if (i == 4) response += "Puppetry: **" + index + "/100**\r\n";
							else if (i == 5) response += "Genjutsu: **" + index + "/100**\r\n";
							else response += "Explosives: **" + index + "/100**";
							i++;
							
						} for (String index : botFile.getList("P")) {
							
							if (index.contains("(")) response += index.split(")")[1] + ": **";
							else response += index + "/100**\r\n";
							
						} break;

					case "secondary":
						for (String index : botFile.find("traps", "stealth", "tracking", "scouting", "poisons", "blacksmithing", "architecture")) {
							
							if (i == 0) response += "Traps: **" + index + "/50**\r\n";
							else if (i == 1) response += "Stealth: **" + index + "/50**\r\n";
							else if (i == 2) response += "Tracking: **" + index + "/50**\r\n";
							else if (i == 3) response += "Scouting: **" + index + "/50**\r\n";
							else if (i == 4) response += "Poisons: **" + index + "/50**\r\n";
							else if (i == 5) response += "Blacksmithing: **" + index + "/75**\r\n";
							else response += "Architecture: **" + index + "/50**";
							i++;
							
						} for (String index : botFile.getList("S")) {
							
							if (index.contains("[")) response += index.split("]")[1] + ": **";
							else response += index + "/50**\r\n";
							
						} break;

					case "custom":
						for (String index : botFile.getList("S", "P")) {
							
							if (index.startsWith("[P")) response += index.split(")")[1] + ": /100**";
							else if (index.startsWith("[S")) response += index.split(")")[1] + ": /50**";
							else if (response.endsWith("50")) response = response.split("/")[0] + index + "/50**";
							else response = response.split("/")[0] + index + "/100**";
						
						} break;
						
					default:
						
						
				} break;
		
			case 1: // $stats create <char> <name> <type>
				botFile.add(tool.capitalize(command[4]) + "|" + tool.capitalize(command[2]));
				break;
				
			case 2: // delete
				botFile.remove(tool.capitalize(command[3]));
				break;
				
			case 3: // add
				String[] valsplit = stats.find(command[3]).split("-");
				
				if (valsplit.length == 2) { // Primary/Secondary
					
					 diminish = Integer.parseInt(valsplit[0]);
					 max = Integer.parseInt(valsplit[1]);
					 
					 if (max == 100) { // Primary
						 
						 
						 
					 } else { // Secondary
						 
						 
						 
					 }
					
				} else { // Base
				
					max = 640;
					reward = 10;
					
					switch (rank) {
					
						case "Academy":
							reward = 2;
							break;

						case "Initiate":
							if (original > 79) reward = 5;
							break;

						case "Captain":
							if (original > 159) reward = 5;
							break;

						case "General":
							if (original > 319) reward = 5;
							break;

						case "Leader":
							break;
					
					}
				
				} break;
				
			case 4: // remove
				break;
				
			case 5: // set
				break;
		
		}
		
	}
	
	private void assignStat() {
		
		
		
	}
	
	public boolean isPrivateMsg() {
		
		return privateMsg;
		
	}

	public List<String> getResponse() {
		
		return tool.sect(response);
		
	}

}
