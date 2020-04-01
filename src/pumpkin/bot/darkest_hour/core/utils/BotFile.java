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
	private File tempfile; // A temporary file used for updating information.
	private String dir; // The file/folder directory in the form of a string. 
	private boolean isFile; // Whether this is a file or a folder.
	private Scanner scanner; // Reads the contents of a File/String.
	private PrintWriter writer; // Creates/overwrites a file, then writes data to it.
	
	
	
	/* Constructor for this class. Assigns the field 'file'
	 * as a directory to the file/folder, which is determined by the
	 * file type. This 'path' is used to access the requested file
	 * by the class methods.
	 * 
	 * Parameters:
	 * file - Represents the directory of the file/folder
	 */
	public BotFile(String file) {
		
		this.file = new File(file);
		this.dir = file;
		isFile = this.file.isFile();
		tempfile = new File(dir.split("[.]")[0] + ".temp");
		
	}
	
	/* Creates an additional line at the very bottom, writing the 
	 * passed string to this new line.
	 */
	public void add(String content) {
		
		try {
			
			scanner = new Scanner(file);
			writer = new PrintWriter(tempfile, "UTF-8");
			
			while (scanner.hasNextLine()) writer.println(scanner.nextLine());
			writer.println(content);
			
			writer.close();
			scanner.close();
			update();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
	}
	
	/* Creates a copy of the current instance
	 * of BotFile to the directory indicated
	 * by the passed String. If a file exists
	 * with the same directory, name, and
	 * extension, it will be overwritten. This
	 * method is not intended for copying 
	 * folders.
	 */
	public void copy(String dest) {
		
		try (Scanner scanner = new Scanner(file);
			 PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
			
			while (scanner.hasNextLine()) writer.println(scanner.nextLine());
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
	}
	
	/* Creates a file/folder in the bot. This method
	 * does not write any data to this file/folder.
	 *
	 * Parameters:
	 * content - What will be written to this file
	 */
	public void create() {
		
		if (isFile) {
			
			try { file.createNewFile(); }
			catch (IOException e) { e.printStackTrace(); }
		
		} else file.mkdir();
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
	
	/* Edits a String value from a profile. In any file,
	 * a key and its value are separated by a colon. The
	 * left-hand side of the colon represents the key. A
	 * key is the name and holder of a value, and is what
	 * the bot looks for when trying to find a specific
	 * value. A value is the right-hand part of the colon,
	 * and its a modifiable number or word. Also returns
	 * the value parameter that was passed.
	 */
	public String edit(String key, String value) {
		
		try {
			
			scanner = new Scanner(file);
			writer = new PrintWriter(tempfile, "UTF-8");
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
					
					if (split[0].equalsIgnoreCase(key)) writer.println(split[0] + value);
					else writer.println(line);
					
				} else writer.println(line);
				
			}
			
			scanner.close();
			writer.close();
			update();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
		return value;
		
	}
	
	/* Overflow version of the original method. Prints
	 * and returns an integer as opposed to a String.
	 */
	public int edit(String key, int value) {
		
		try {
			
			scanner = new Scanner(file);
			writer = new PrintWriter(tempfile, "UTF-8");
					 
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
					
					if (split[0].equalsIgnoreCase(key)) writer.println(split[0] + value);
					else writer.println(line);
					
				} else writer.println(line);
				
			}
			
			scanner.close();
			writer.close();
			update();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
		return value;
		
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

			scanner = new Scanner(file);
			writer = new PrintWriter(tempfile, "UTF-8");
			
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
			update();
			
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
						
						if (split[0].startsWith(index)) {
							
							value.add(split[1]);
							break;
							
						}
						
					}
				
				}
				
			}
			
		} catch (IOException e) { e.printStackTrace(); }
		
		return value;
		
	}
	
	/* Returns a String of all existing keys within a
	 * file.
	 */
	public String findAllKeys() {
		
		String value = "";
		
		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String key = line.split(": ")[0];
					
					if (key.startsWith("[")) value += key.split("]")[1] + "\r\n";
					else value += key + "\r\n";
					
				}
				
			}
			
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		return value;
		
	}
	
	/* Similar to find(), this method searches for a key matching
	 * the String that has been passed. If it finds it, then it
	 * will return the entire line where it found the matching
	 * String. If startsWith is set to true, it will instead
	 * check for if the key begins with the passed String
	 * as opposed to matching it.
	 */
	public String getLine(boolean startsWith, String key) {
		
		String value = "";
		
		try (Scanner scanner = new Scanner(file)) {
			
			while (scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				
				if (!line.isEmpty()) {
					
					String[] split = line.split(": ");
					
					if (line.startsWith("[")) split[0] = split[0].split("]")[1];
					
					if (startsWith && split[0].startsWith(key)) {
					
						value = line;
						break;
							
					} else if (split[0].equalsIgnoreCase(key)) {
						
						value = line;
						break;
						
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
	public String list() {
		
		String list = "";
		
		for (File index : file.listFiles()) list += index.getName() + "\r\n";
		return list;
		
	}
	
	/* Renames a file/folder from the bot. */
	public boolean rename(String name) {
		
		return file.renameTo(new File(file.getParentFile() + "/" + name));
		
	}
	
	/* Internal method exclusive to this class.
	 * Copies over data from the temporary file to
	 * the original file, then deletes this temporary
	 * file.
	 */
	private void update() {
		
		try {
			
			writer = new PrintWriter(file, "UTF-8");
			scanner = new Scanner(tempfile);
			
			while (scanner.hasNextLine()) writer.println(scanner.nextLine());
			
			scanner.close();
			writer.close();
			tempfile.delete();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) { e.printStackTrace(); }
		
	}

}
