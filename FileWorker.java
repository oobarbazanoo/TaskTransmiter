import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileWorker
{
	public static void copyFile(File from, File to)
	{
	    try 
	    {Files.copy( from.toPath(), to.toPath() );} 
	    catch (IOException e)
	    {e.printStackTrace();}
	}
	
	/**
	 * If the directory already exists get this one.
	 * @param name of the directory
	 * @return directory as File
	 */
	public static File createDirectoryNearRunnableJar(String name)
	{
		File dir;
		if(System.getProperty("file.separator").equals("\\"))
		{dir = new File(System.getProperty("user.dir") + "\\" + name);}
		else
		{dir = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + name);}
		dir.mkdir();
		return dir;
	}
	
	/*
	 * 	File file1 = new File(dir.getAbsolutePath() + "\\copied2.jpg"),
			 file2 = new File("E:\\Cloud\\2015 - 2016\\images\\All1\\notISnake\\2.jpg");
		
		copyFile(file2, file1);
	 */
}
