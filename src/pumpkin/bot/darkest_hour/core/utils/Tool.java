package pumpkin.bot.darkest_hour.core.utils;

// List of useful tools the bot uses for menial tasks.
public class Tool {

	/* Returns a new, properly cased String. Uses split()
	 * to replace dashes and underscores with spaces in
	 * order to determine when to make a new capitalized
	 * word.
	 * 
	 * Parameters:
	 * content - String to be capitalized.
	 */
	public String capitalize(String content) {
		
		String[] array = content.split("-_");
		content = "";
		
		for (String a : array) content += a.substring(0,1).toUpperCase() + a.substring(1).toLowerCase() + " ";
		return content.trim();
		
	}

}
