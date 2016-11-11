package doit.logic;

import java.util.Date;

import doit.xml.QuestionXml;

//Erweiterte Klasse für die Übergabe
//und die Auswertung der Fragen.
//
//Erbt alle Eigenschaften von QuestionXml!

public class Question extends QuestionXml {
	
	private boolean isDone;//Frage bearbeitet?
	private boolean isCorrect;//Frage korrekt beantwortet?
	private boolean[] answers;//Wie wurde die Frage beantwortet?
	private long startTime;//Startzeit (Frage wurde übergeben)
	private long stopTime;//Stopzeit (Frage wurde evaluiert)
	
	public Question() {
		super();
		this.answers = null;
		this.isDone = false;
		this.isCorrect = false;
		this.startTime = 0;
		this.stopTime = 0;
	}
	
	//Abfrage der Bearbeitungszeit
	public long getDuration() {
		if(startTime > 0)
			if(stopTime > 0)
				return stopTime - startTime;//"fertige" Frage
			else return new Date().getTime() - startTime;//"laufende" Frage
		return 0;
	}	
	
	//Getter & Setter
	public boolean isDone() {return isDone;}
	public boolean isCorrect() {return isCorrect;}
	public boolean[] getAnswers() {return answers;}
	protected void setDone(boolean isDone) {this.isDone = isDone;}
	protected void setCorrect(boolean isCorrect) {this.isCorrect = isCorrect;}
	protected void setAnswers(boolean[] answers) {this.answers = answers;}
	protected void setStartTime(long startTime) {this.startTime = startTime;}
	protected void setStopTime(long stopTime) {this.stopTime = stopTime;}
}
