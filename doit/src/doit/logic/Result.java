package doit.logic;

//Datensatz für Auswertung und Übergabe

public class Result{
	
	private int quizDuration;
	private double resultPercent;
	private String resultIHK;
	
	protected Result() {
		quizDuration = 0;
		resultPercent = 0;
		resultIHK = null;
	}
	
	//Getter & Setter
	public int getQuizDuration() {return quizDuration;}
	public double getResultPercent() {return resultPercent;}
	public String getResultIHK() {return resultIHK;}
	protected void setQuizDuration(int quizDuration) {this.quizDuration = quizDuration;}
	protected void setResultPercent(double resultPercent) {this.resultPercent = resultPercent;}
	protected void setResultIHK(String resultIHK) {this.resultIHK = resultIHK;}
}

//
//Hilfsfunktionen für Quiz-Auswertung
//

class ResultFunctions {
	
	//IHK-Note aus Prozentsatz ermitteln...
	protected static String getResultIHK(double resultPercent) {
		
		int result = (int)Math.round(resultPercent * 100);
		
		if(result >= 92)
			return "Note: 1 (sehr gut)";
		else if(result >= 81)
			return "Note: 2 (gut)";
		else if(result >= 67)
			return "Note: 3 (befriedigend)";
		else if(result >= 50)
			return "Note: 4 (ausreichend)";
		else if(result >= 30)
			return "Note: 5 (mangelhaft)";
		return "Note: 6 (ungenügend)";
	}
	
	
}
