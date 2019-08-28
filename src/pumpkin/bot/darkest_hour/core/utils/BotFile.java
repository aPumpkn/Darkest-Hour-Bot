package pumpkin.bot.darkest_hour.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import pumpkin.bot.darkest_hour.core.Listener;

// Reads and interacts with files and folders from the bot. 
public class BotFile extends Listener {
	
	/* Assigned Fields */
	private File file; // The file/folder being accessed.
	
	
	
	/* Constructor for this class. Assigns the field 'file'
	 * as a directory to the file/folder. This 'file' is
   * used to access the requested file by this class'
   * methods.
	 * 
	 * Parameters:
	 * file - Represents the directory of the file/folder
	 */
	public BotFile(String file) {
		
		this.file = new File(file);
		
	}
	
	
	
	/* Checks if this file/folder exists. */
	public boolean exists() {
			
		return file.exists();
		
	}
	
	/* Reads from a file in the bot. Files follow a
	 * format of colons separating a key and its value.
	 * This checks for the left-side of the colon, the
	 * key. If the given parameter and the key match, it
	 * will return the value of this key. Capitalization
	 * does not matter when passing the parameter.
	 * 
	 * This returns a single String value from the key
	 * given. If the key is not found within the file,
	 * the String value will be blank.
	 * 
	 * Parameters:
	 * key - Word or phrase to look for
	 */
	public String find(String key) {
		
		String value = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			while (reader.lines() != null) {
				
				String[] split = reader.readLine().split(": ");
					
				if (split[0].equalsIgnoreCase(key)) {
					
					value = split[1];
					break;
					
				}
				
			}
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return value;
		
	}
	
	/* Overflow version of the original method. This
	 * takes an unspecified amount of parameters (must
	 * have more than one), unlike the prior method.
	 * 
	 * This returns a List of Strings, of which represent
	 * individual items searched. A specific String can
	 * be retrieved from this List by using .get() and
	 * passing an integer value to it (same index procedure
	 * as standard java Arrays).
	 * 
	 * Parameters:
	 * key - Words or phrases to look for
	 */
	public List<String> find(String... key) {
		
		List<String> value = new ArrayList<String>(0);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			while (reader.lines() != null) {
				
				String[] split = reader.readLine().split(": ");
				
				for (String index : key)
					if (split[0].equalsIgnoreCase(index)) {
						
						value.add(split[1]);
						break;
						
					}
				
			}
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return value;
		
	}
	
	/* Creates a file/folder in the bot. This
	 * method does not write data.
	 */
	public void create() {
		
		if (file.isDirectory()) file.mkdir();
		else {
			
			try { file.createNewFile(); }
			catch (IOException e) { e.printStackTrace(); }
		
		}
		
	}
	
	/* Creates a file/folder in the bot. Will write
	 * data only if its a file - folders do not hold
	 * data directly.
	 *
	 * Parameters:
	 * content - What will be written to this file
	 */
	public void create(String content) {
		
		if (file.isFile()) {
				
			try (PrintWriter writer = new PrintWriter(file, "UTF-8")) { writer.println(content); }
			catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
			
		} else file.mkdir();
	}
	
	/* Deletes a file/folder from the bot. */
	public boolean delete() {
		
		return file.delete();
		
	}
	
	/* Renames a file/folder from the bot. */
	public boolean rename(String name) {
		
		return file.renameTo(new File(name));
		
	}

}
