package doit.logic;

public class Score implements Comparable<Score> {
	
	private String fileName;
	private String[] categories;
	private String userName;
	private int quizLength;
	private long duration;
	private long date;
	private Double result;
	
	public Score() {
		
		fileName = null;
		categories = null;
		userName = null;
		quizLength = 0;
		duration = 0;
		date = 0;
		result = 0d;
	}
	
	public void setFileName(String fileName) {this.fileName = fileName;}
	public void setCategories(String[] category) {this.categories = category;}
	public void setUserName(String userName) {this.userName = userName;}
	public void setQuizLength(int quizLength) {this.quizLength = quizLength;}
	public void setDuration(long duration) {this.duration = duration;}
	public void setDate(long date) {this.date = date;}
	public void setResult(double result) {this.result = result;}
	
	public String getFileName() {return fileName;}
	public String[] getCategories() {return categories;}
	public String getUserName() {return userName;}
	public int getQuizLength() {return quizLength;}
	public long getDuration() {return duration;}
	public long getDate() {return date;}
	public double getResult() {return result;}
	
	@Override
	public int compareTo(Score score) {
		return this.result.compareTo(score.getResult());
	}
}
