
public class Constants
{
	public static final String NAME_OF_DATA_DIR = "Data",
							   NAME_OF_TASKS_LIST = "TasksData.txt",
							   SYSTEM_SEPARATOR = (System.getProperty("file.separator").equals("\\"))?"\\\\":System.getProperty("file.separator");
	public static final int LENGTH_OF_POSSIBLE_NAME = 70;
}
