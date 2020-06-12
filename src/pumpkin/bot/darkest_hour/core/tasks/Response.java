package pumpkin.darkest_dawn.core.utils;

// Contains all stored bot messages/strings.
public class Response {
	
	/* Commands */
	protected String command1 = "$char create <@user> <name> <type> - Create a new character. If the argument \"type\" is not provided, it will use the default template.\r\n" + 
			"$char delete <name> - Permanently deletes a character, backup and all. This action cannot be revoked.\r\n" + 
			"$char recycle <name> - Creates a new backup, then deletes current character information.\r\n" + 
			"$char backup <name> - Saves current character information to a backup. If the backup already exists, this overwrites it.\r\n" + 
			"$char backup * - Creates a backup for all characters.\r\n" + 
			"$char restore <name> - Overwrites current character information using the backup.\r\n" + 
			"$char restore * - Restores all current characters to their backups.\r\n" + 
			"$char list - Returns a list of all characters.\r\n" + 
			"\r\n" + 
			"$wallet <char> - Displays the character's wallet balance. If the argument \"char\" is not provided, it will select your default character.\r\n" + 
			"$wallet add <char> <amount> - Increases the character's balance by the specified amount.\r\n" + 
			"$wallet remove <char> <amount> - Decreases the character's balance by the specified amount.\r\n" + 
			"$wallet set <char> <amount> - Sets the character's balance to the specified amount.\r\n" + 
			"\r\n" + 
			"$dmp <@user> - Displays the player's dmp balance. If the argument \"char\" is not provided, it will select your default character.\r\n" + 
			"$dmp add <@user> <amount> - Increases the character's balance by the specified amount.\r\n" + 
			"$dmp remove <@user> <amount> - Decreases the character's balance by the specified amount.\r\n" + 
			"$dmp set <@user> <amount> - Sets the character's balance to the specified amount.\r\n" + 
			"** **";
	protected String command2 = "$shop list - Returns a list of all items available for purchase.\r\n" + 
			"$shop buy <item> - Purchases a single item using your default character.\r\n" + 
			"$shop buy <item> <char> - Purchases a single item using the specified character.\r\n" + 
			"$shop buy <item> <amount> - Purchases a specified amount of the item using your default character.\r\n" + 
			"$shop buy <item> <char> <amount> - Purchases a specified amount of the item using the specified character.\r\n" + 
			"$shop sell <item> - Sells a single item using your default character.\r\n" + 
			"$shop sell <item> <char> - Sells a single item using the specified character.\r\n" + 
			"$shop sell <item> <amount> - Sells a specified amount of the item using your default character.\r\n" + 
			"$shop sell <item> <char> <amount> - Purchases a specified amount of the item using the specified character.\r\n" + 
			"** **";
	protected String command3 = "$stats <type> <char> - Displays the character's stats of the specified type. If the argument \"char\" is not provided, it will select your default character.\r\n" + 
			"$stats create <char> <type> <name> - Creates a new stat for the specified character. The argument \"type\" determines the different aspects to this stat.\r\n" + 
			"$stats delete <char> <stat> - Deletes a custom stat from the specified character.\r\n" + 
			"$stats add <char> <stat> <amount> - Increases the specified character's stat value by the specified amount.\r\n" + 
			"$stats remove <char> <stat> <amount> - Decreases the specified character's stat value by the specified amount.\r\n" + 
			"$stats set <char> <stat> <amount> - Sets the specified character's stat value to the specified amount.\r\n" + 
			"$stats up <char> <stat> <amount> - Automatically determines the correct amount of points to add per training instance, increases the stat by this amount, then deducts a training point from the character. The argument \"amount\" determines how many times this action should be done, and if its not provided, it will default to a single time.\r\n" + 
			"$stats down <char> <stat> <amount> - Automatically determines the correct amount of points to remove per training instance, decreases the stat by this amount, then adds a training point to the character. The argument \"amount\" determines how many times this action should be done, and if its not provided, it will default to a single time.\r\n" + 
			"** **";
	protected String command4 = "$train <char> - Displays the character's available training left. If the argument \"char\" is not provided, it will select your default character.\r\n" + 
			"$train add <char> <stat> <amount> - Increases the amount of trainings a character has left by the specified amount.\r\n" + 
			"$train remove <char> <stat> <amount> - Decreases the amount of trainings a character has left by the specified amount.\r\n" + 
			"$train set <char> <stat> <amount> - Sets the amount of trainings a character has left to the specified amount.\r\n" + 
			"$train reset <char> - Resets the specified character's trainings to the max amount.\r\n" + 
			"$train reset * - Resets all character's trainings to the max amount.\r\n" + 
			"\r\n" +
			"$prefs list - Gives a list of preferences. Its... Kinda short.\r\n" +
			"$prefs default - Sets your default character.\r\n" +
			"\r\n" + 
			"$charges <char> - Displays the amount of charges a character has. If the argument \"char\" is not provided, it will select your default character.\r\n" + 
			"\r\n" + 
			"$inv <char> - Returns a list of all items a character has in their inventory. If the argument \"char\" is not provided, it will select your default character.\r\n" + 
			"\r\n" + 
			"$help - Gives you this entire list of commands.";
	
	
	/* Templates */
	private String prefs = 
			"DMP: 0\r\n" + 
			"Default Character: None\r\n" + 
			"Timezone: Unknown";
	private String stats = "Charges: 1-1-30-0\r\n" + 
			"\r\n" + 
			"B|Perception: 0-10-640-0\r\n" + 
			"B|Control: 0-10-640-0\r\n" + 
			"B|Quickness: 0-10-640-0\r\n" + 
			"B|Brawn: 0-10-640-0\r\n" + 
			"B|Durability: 0-10-640-0\r\n" + 
			"\r\n" + 
			"S|Traps: 0-3-50-40\r\n" + 
			"S|Stealth: 0-3-50-40\r\n" + 
			"S|Tracking: 0-3-50-40\r\n" + 
			"S|Scouting: 0-3-50-40\r\n" + 
			"S|Poisons: 0-3-50-40\r\n" + 
			"S|Blacksmithing: 0-3-75-40\r\n" + 
			"S|Architecture: 0-3-50-40\r\n";
	private String shinobiStats = "\r\n" + 
			"P|Ninjutsu: 0-4-100-75\r\n" + 
			"P|Taijutsu: 0-4-100-75\r\n" + 
			"P|Medical: 0-4-100-75\r\n" + 
			"P|Fuinjutsu: 0-4-100-75\r\n" + 
			"P|Puppetry: 0-4-100-75\r\n" + 
			"P|Genjutsu: 0-4-100-75\r\n" + 
			"P|Explosives: 0-4-100-75\r\n" + 
			"\r\n";
	private String watchUserStats = "\r\n" +
			"P|Trix: 0-4-100-75\r\n" + 
			"\r\n";
	private String osmosianStats = "\r\n" +
			"P|Osmosian: 0-4-100-75\r\n" +
			"P|Taijutsu: 0-4-100-75\r\n";
	private String magicianStats = "\r\n" +
			"P|Magician: 0-4-100-75\r\n" +
			"P|Taijutsu: 0-4-100-75\r\n";
	private String anoditeStats = "\r\n" +
			"P|Magician: 0-4-100-75\r\n";
	private String pureHumanStats = "\r\n" +
			"P|Tinker: 0-4-100-75\r\n" +
			"P|Semblance: 0-4-100-75\r\n" +
			"P|Taijutsu: 0-4-100-75\r\n";
	private String pureFaunusStats = "\r\n" +
			"P|Semblance: 0-4-100-75\r\n" +
			"P|Taijutsu: 0-4-100-75\r\n";
	private String mixedHeritageStats = "\r\n" +
			"P|Tinker Researcher: 0-4-100-75\r\n" +
			"P|Semblance: 0-4-100-75\r\n" +
			"P|Taijutsu: 0-4-100-75\r\n";
	private String halfaStats = "\r\n" +
			"P|Intangibility: 0-4-100-75\r\n" +
			"P|Invisibility: 0-4-100-75\r\n" +
			"P|Flight: 0-4-100-75\r\n" +
			"P|Possession: 0-4-100-75\r\n" + 
			"P|Ectomanipulation: 0-4-100-75\r\n" +
			"P|Duplication: 0-4-100-75\r\n" +
			"P|Teleportation: 0-4-100-75\r\n" +
			"P|Enhancement: 0-4-100-75\r\n" +
			"P|Separation: 0-4-100-75\r\n" +
			"P|Healing: 0-4-100-75\r\n" +
			"P|Transformation: 0-4-100-75\r\n";
	private String ghostStats = "\r\n" +
			"P|Intangibility: 50-4-100-75\r\n" +
			"P|Flight: 100-4-100-75\r\n" +
			"P|Invisibility: 100-4-100-75\r\n" + 
			"P|Possession: 100-4-100-75\r\n" +
			"P|Ectomanipulation: 25-4-100-75\r\n" +
			"P|Obssession: 0-4-100-75\r\n";
	private String hunterStats = "\r\n" +
			"P|Tinker: 0-4-100-75\r\n" +
			"P|Taijutsu: 0-4-100-75\r\n";
	
	public String getShinobiStats() {
		return shinobiStats;
	}

	public String getWatchUserStats() {
		return watchUserStats;
	}

	public String getOsmosianStats() {
		return osmosianStats;
	}

	public String getMagicianStats() {
		return magicianStats;
	}

	public String getAnoditeStats() {
		return anoditeStats;
	}

	public String getPureHumanStats() {
		return pureHumanStats;
	}

	public String getPureFaunusStats() {
		return pureFaunusStats;
	}

	public String getMixedHeritageStats() {
		return mixedHeritageStats;
	}

	public String getHalfaStats() {
		return halfaStats;
	}

	public String getGhostStats() {
		return ghostStats;
	}

	public String getHunterStats() {
		return hunterStats;
	}

	public String getNewMember() {
		return newMember;
	}

	public String[] getBlockedMessage() {
		return blockedMessage;
	}

	private String inventory = "Wallet: 1000\r\n"
			+ "Kunai: 10\r\n"
			+ "Shuriken: 10\r\n"
			+ "Ninja Wire: 5";
	private String general = "Name: Unknown\r\n" + 
			"Rank: Unknown\r\n" + 
			"Age: Unknown\r\n" + 
			"Gender: Unknown\r\n" + 
			"Height: Unknown\r\n" + 
			"Weight: Unknown\r\n" + 
			"Relatives: Unknown\r\n" + 
			"Personality: Unknown\r\n" + 
			"Hobbies: Unknown\r\n" + 
			"Dislikes: Unknown\r\n" + 
			"Goals: Unknown\r\n" + 
			"Backstory: Unknown\r\n" + 
			"Appearance: Unknown\r\n" + 
			"Other: Unknown\r\n" + 
			"Theme Song: Unknown";
	private String training = "Base: 4/4\r\n" + 
			"Perception: 2/2\r\n" + 
			"Control: 2/2\r\n" + 
			"Quickness: 2/2\r\n" + 
			"Brawn: 2/2\r\n" + 
			"Durability: 2/2\r\n" + 
			"\r\n" + 
			"Primary: 3/3\r\n" + 
			"Secondary: 2/2\r\n" + 
			"\r\n" + 
			"Charges: 1/1";
	
	/* Help */

	/* Errors */
	private String internalErr = "Internal Error: An issue pertaining to the bot's side of things has come about. Please notify a @Developer.";
	
	/* Misc */
	private String verification = "**Verification Question:** ";
	private String verifyFailure = "**Verification Failed...** The answer you gave was incorrect. Be sure to include correct spelling and exclude plural answers. Remember, the answer is only one word, and it doesn't have numbers nor punctuation.";
	private String verifySuccess = "**Verification Success!** You're now registered into the server. The next step is to create a character, and then you can finally begin. Use the #character-template to get started. The process usually takes about ten to thirty minutes depending on the detail put into it. Should you have any further questions, or wish to be aided through the process, contact a staff member.";
	private String newMember = " is now an official member of Breaking Dawn!";
	private String[] blockedMessage = {"Message deleted from ", ": ||", "||\r\n**sent by** "};
	
	
	
	/* Retrieves the welcome message. */
	public String getVerification() { return verification; }

	/* Retrieves the verification failure message. */
	public String getVerifyFailure() { return verifyFailure; }

	/* Retrieves the verification failure message. */
	public String getVerifySuccess() { return verifySuccess; }

	/* Retrieves the new member message. 
	 * 
	 * Parameters:
	 * member - user who has just been verified
	 * channel - where the bot directs the user
	 */
	public String getNewMember(String member) { 
		
		return member + newMember; 
		
	}
	
	public String getBlockedMessage(String channel, String member, String message) {
		
		return blockedMessage[0] + channel + blockedMessage[1] + message + blockedMessage[2] + member;
		
	}
	
	public String getPrefs() { return prefs; }

	public String getStats() { return stats; }

	public String getInventory() { return inventory; }

	public String getGeneral() { return general; }
	
	public String getTraining() { return training; }
	
	/* Retrieves the internal error message.*/
	public String getInternalErr() { return internalErr; }

}
