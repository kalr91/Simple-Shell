// ERRORS:  Program will not change to a subdirectory inside a subdirectory
// Will however change to a sub-subdirectory on first input of relative path
// Could not solve the issue


package kroberson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import java.util.Scanner;


public class Shell {
	
	public static Path path = Paths.get(System.getProperty("user.dir"));
	public static Path searchPath = null;
	
	
	public static void main(String[] args)
	{
		
		String userInput = null;
		Scanner input = new Scanner(System.in);
		boolean k = true;
			
		print("Welcome to Simple Shell.");
		print("");
		
		while(k)
		{
			
			print("Prompt > ");
			userInput = input.nextLine();
			String[] commandSplit = userInput.split(" ", 2);
			
			//print(commandSplit[0]);
			
			
			if(commandSplit[0].matches("help"))
			{
				help();
			}
			else if(commandSplit[0].matches("dir"))
			{
				dir();
			}
			else if(commandSplit[0].matches("cd"))
			{
				searchPath = Paths.get(commandSplit[1]);
				cd(searchPath);
			}
			else if(commandSplit[0].matches("show"))
			{
				Path filetoshow = Paths.get(commandSplit[1]);
				show(filetoshow);
			}
			else if(commandSplit[0].matches("exit"))
			{
				k = exit();
			}
			else
			{
				print("Not a command.");
				help();
			}
						
			
		}
		
		
	}
	
	// print command
	
	public static void print(String text)
	{
		System.out.println(text);
	}
	
	
	// help command
	
	public static void help()
	{
		
		TableBuilder helptb = new TableBuilder();
		
		print("COMMANDS:");
		print("");
		
		helptb.addRow("help", "     Show list of commands");
		helptb.addRow("dir", "     List contents of current directory");
		helptb.addRow("cd [dir]", "     Change to directory");
		helptb.addRow("show [file]", "     Show contents of file");
		helptb.addRow("exit", "     Exit the shell");
		
		print(helptb.toString());		
		
	}
	
	// dir command
	
	public static void dir()
	{
		
		print("Directory of \"" + path + "\"");
		print("");
		
		TableBuilder dirtb = new TableBuilder();
		File dir = new File(String.valueOf(path));		
		File[] filesList = dir.listFiles();
		
		for(File file : filesList)
		{
			if(file.isFile())
			{

				dirtb.addRow("     ", "    " + FileUtils.byteCountToDisplaySize(file.length()), "     " + file.getName()); 
				
			}
			else if(Files.isDirectory(path))
			{
				dirtb.addRow("d    ", " ", "     " + file.getName());
			}
		}
		
		print(dirtb.toString());
	}
	
	
	// cd command
	
	public static void cd(Path newDir)
	{
		
		File f = newDir.toFile();
		
		
		try
		{		
			
			boolean exists = f.exists();
			newDir = Paths.get(f.getCanonicalPath());
			print(String.valueOf(newDir));
				
				if(exists == true)
				{
					if(newDir != path)
					{					
					path = newDir;
					System.setProperty("user.dir", String.valueOf(path));
					print("SUCCESS: \"" + path + "\"");					
					}
					else
					{
						print("Cannot change to directory you are already in.");
					}
					
				}
				else if(exists == false)
				{
					print("moving to: " + String.valueOf(f));
					print("Does not exist");
				}
				
		}
		catch(Exception e)
		{
			print("Not a directory.");
		}
			
		
	}
	
	// show command
	
	public static void show(Path filetoshow)
	{
		
		File f = null;
		String filepath = "";
		
		try
		{
			f = new File(String.valueOf(filetoshow));
			
			filepath = f.getCanonicalPath();
			print(filepath);
			
		}
		catch(Exception e)
		{
			print("bloop");
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader(filepath)))
		{
			
			String line = null;
			while((line = br.readLine()) != null)
			{
				print(line);
			}
			
		}
		catch(IOException e)
		{
			print("File does not exist");
		}
		
	}
	
	// exit command
	
	public static boolean exit()
	{
		
		print("Goodbye.");
		return false;
	}

}
