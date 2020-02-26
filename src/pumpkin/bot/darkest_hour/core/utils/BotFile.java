package pumpkin.darkest_dawn.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pumpkin.darkest_dawn.core.Listener;

// Reads and interacts with files and folders from the bot. 
public class BotFile extends Listener {
	
	
	/* Assigned Fields */
	private File file; // The file/folder being accessed.
	private String dir; // The file/folder directory in the form of a string. 
	private boolean isFile; // Whether this is a file or a folder.
	
	
	
	/* Constructor for this class. Assigns the field 'file'
	 * as a directory to the file/folder, which is determined by the
	 * file type. This 'path' is used to access the requested file
	 * by the class methods.
	 * 
	 * Parameters:
	 * file - Represents the directory of the file/folder
	 */
	public BotFile(String file, boolean isFile) {
		
		this.file = new File(file);
		this.dir = file;
		this.isFile = isFile;
		
	}
	
	/* Creates a file/folder in the bot. This method
	 * does not write any data to this file/folder.
	 *
	 * Parameters:
	 * content - What will be written to this file
	 */
	public void create() {
		
		if (isFile) {
				
			try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {}
			catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
			
		} else file.mkdir();
	}
	
	/* Uses the current instance of BotFile as a
	 * way to create a new file with the same data
	 * and name as the file indicated by the directory
	 * passed to this method in the form of a String.
	 */
	public void copyAs(String origin) {
		
		try (Scanner scanner = new Scanner(new File(origin));
			 PrintWriter writer = new PrintWriter(file, "UTF-8")) {
			
			while (scanner.hasNextLine()) writer.println(scanner.nextLine());
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
	}
	
	/* Uses the current instance of BotFile to 
	 * create a new file using the name and data
	 * of the file indicated by the directory
	 * passed to this method in the form of a
	 * String.
	 */
	public void copyTo(String dest) {
		
		try (Scanner scanner = new Scanner(file);
			 PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
			
			while (scanner.hasNextLine()) writer.println(scanner.nextLine());
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
	}
	
	/* Overflow version of the original method. 
	 * Creates a file/folder in the bot. Will write
	 * data provided by the passed string only if
	 * its a file.
	 *
	 * Parameters:
	 * content - What will be written to this file
	 */
	public void create(String content) {
		
		if (isFile) {
				
			try (PrintWriter writer = new PrintWriter(file, "UTF-8")) { writer.println(content); }
			catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
			
		} else file.mkdir();
		
	}
	
	/* Deletes a file/folder from the bot. */
	public boolean delete() {
		
		return file.delete();
		
	}
	
	/* Edits a String value from a profile. In any
	 * file, a key and its value are separated by a
	 * colon. The left-hand side of the colon represents
	 * the key. A key is the name and holder of a value,
	 * and is what the bot looks for when trying to find
	 * a specific value. A value is the right-hand part
	 * of the colon, and its a modifiable number or word.
	 */
	public void edit(String key, String value) {
		
		String tempfile = dir.split("[.]")[0] + ".temp";
		
		try (Scanner scanner = new Scanner(file);
			 PrintWriter writer = new PrintWriter(tempfile, "UTF-8")) {
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
					
					if (split[0].equalsIgnoreCase(key)) writer.println(split[0] + value);
					else writer.println(line);
					
				} else writer.println(line);
				
			}
			
			Scanner scanner2 = new Scanner(new File(tempfile));
			PrintWriter writer2 = new PrintWriter(file);
			
			while (scanner2.hasNextLine()) writer2.println(scanner2.nextLine());
			
			scanner2.close();
			writer2.close();
			new File(tempfile).delete();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
	}
	
	/* Overflow version of the original method. Takes an indefinite
	 * amount of keys and values for editing. The total number of 
	 * parameters passed must be an even number in order to avoid
	 * passing a key without passing its value. When passing the
	 * parameters, whether the String represents a key or value
	 * is determined by what position its passed in. The
	 * alternation of which is simply...
	 * 
	 * edit("key", "value", "key2", "value2", "key3", "value3");
	 */
	public void edit(String... keysAndVals) {
		
		try {

			String tempfile = dir.split("[.]")[0] + ".temp";
			Scanner scanner = new Scanner(file);
			PrintWriter writer = new PrintWriter(tempfile, "UTF-8");
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
					String value = split[1];
					
					for (int i = 0; i < keysAndVals.length; i++) {
						
						if (split[0].equalsIgnoreCase(keysAndVals[i])) {
						
							value = keysAndVals[i + 1];
							break;
							
						} else i++;
						
					}
					
					writer.println(split[0] + ": " + value);
					
				} else writer.println(line);
				
			}
			
			scanner.close();
			writer.close();
			
			Scanner scanner2 = new Scanner(new File(tempfile));
			PrintWriter writer2 = new PrintWriter(file, "UTF-8");
			
			while (scanner2.hasNextLine()) writer2.println(scanner2.nextLine());
			
			scanner2.close();
			writer2.close();
			new File(tempfile).delete();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
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
		
		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
						
					if (split[0].equalsIgnoreCase(key)) {
						
						value = split[1];
						break;
						
					}
				
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
		
		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
					
					for (String index : key) {
						
						if (split[0].equalsIgnoreCase(index)) {
							
							value.add(split[1]);
							break;
							
						}
						
					}
				
				}
				
			}
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return value;
		
	}
	
	/* Gives a list of all other files within
	 * this current directory. Does not work
	 * if the given File isn't recognized as
	 * a folder.
	 */
	public List<String> list() {
		
		List<String> list = new ArrayList<String>(0);
		
		for (File index : file.listFiles()) list.add(index.getName());
		return list;
		
	}
	
	/* Renames a file/folder from the bot. */
	public boolean rename(String name) {
		
		return file.renameTo(new File(file.getParentFile() + "/" + name));
		
	}

}
