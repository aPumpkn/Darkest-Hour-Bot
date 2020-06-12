package pumpkin.darkest_dawn.core.utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

// List of useful tools the bot uses for menial tasks.
public class Tool {

	
	public int integer;
	NumberFormat numeric = NumberFormat.getNumberInstance(Locale.US);
	
	
	/* Returns a new, properly cased String. Uses split()
	 * to replace dashes and underscores with spaces in
	 * order to determine when to make a new capitalized
	 * word.
	 * 
	 * Parameters:
	 * content - String to be capitalized.
	 */
	public String capitalize(String content) {
		
		String[] array = content.split("[-_]");
		content = "";
		
		for (String a : array) content += a.substring(0, 1).toUpperCase() + a.substring(1).toLowerCase() + " ";
		return content.trim();
		
	}
	
	/* Divides the given List of Strings into a List
	 * in order to send messages without breaking the
	 * 2,000 character limit on Discord.
	 */
	public List<String> sect(String content) {
		
		List<String> list = new ArrayList<String>(0);
		Scanner scanner = new Scanner(content);
		int counter = 0;
		String part = "";
		
		while (scanner.hasNextLine()) {
			
			if (counter > 49) {
				
				list.add(part);
				part = "";
				counter = 0;
				
			} else {
				
				part += scanner.nextLine();
				counter++;
			
			}
			
		}

		scanner.close();
		if (!part.isEmpty()) list.add(part);
		return list;
		
	}

	/* Attempts to parse an Integer from the given
	 * string. If the passed String does not contain
	 * a character that can be parsed into an Integer,
	 * it returns false. Otherwise, it returns true.
	 */
	public boolean isInteger(String content) {
		
		try { integer = Integer.parseInt(content); }
		catch (NumberFormatException e) { return false; }
		return true;
		
	}
	
	public String format(int number) {
		
		return numeric.format(number);
		
	}
	
	public int getInteger() {
		
		return integer;
		
	}
	
}
