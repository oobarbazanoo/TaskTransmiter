import java.io.File;
import java.io.Serializable;



public class Task implements Serializable
{
	private String taskText, taskName;
	private String[] answers; // appropriate answers
	private File taskImage;
	private int pointsForSolving;
	
	public Task()
	{}
	
	public Task(String taskName, String taskText, File taskImage, String[] answers, int pointsForSolving)
	{
		this.taskName = taskName;
		this.taskText = taskText;
		this.taskImage = taskImage;
		this.answers = answers;
		this.pointsForSolving = pointsForSolving;
	}

	public String getTaskName()
	{return this.taskName;}
	
	public void setTaskName(String newName)
	{this.taskName = newName;}
	
	public int getPointsForSolving() 
	{return pointsForSolving;}
	
	public void setPointsForSolving(int newPoints)
	{this.pointsForSolving = newPoints;}
	
	public String getTaskText() 
	{return taskText;}

	public void setTaskText(String taskText)
	{this.taskText = taskText;}

	public String[] getAnswers()
	{return answers;}

	public void setAnswers(String[] answers)
	{this.answers = answers;}

	public File getTaskImage()
	{return taskImage;}

	public void setTaskImage(File taskImage)
	{this.taskImage = taskImage;}
	
	@Override
	public String toString()
	{return this.taskName;}
}
