package doit.logic;

import java.util.Date;

//Einfacher, passiver Timer (Tick-Counter)
//
//reset():void
//start():void
//stop():void
//isRunning():boolean
//getDuration():long

public class Timer {

	private long startTime;
	private long pauseTime;
	
	protected Timer() {reset();}
	
	//Timer reset
	public void reset() {
		startTime = 0;
		pauseTime = 0;
	}
	
	//Timer starten
	public void start() {
		if(startTime <= 0)//erster Start
			startTime = new Date().getTime();
		else if(pauseTime > 0)//fortsetzen
			startTime += new Date().getTime() - pauseTime;
		pauseTime = 0;
	}
	
	//Timer anhalten
	public void stop() {
		if(startTime > 0)
			pauseTime = new Date().getTime();
	}
	
	//Timer Status?
	public boolean isRunning() {
		return startTime > 0 && pauseTime <=0;
	}
	
	//Zeitspanne in Millisekunden
	public long getDuration() {
		if(pauseTime > 0)//pausiert
			return pauseTime - startTime;
		else if(startTime > 0)//laufend
			return new Date().getTime() - startTime;
		else return 0;//noch nie gestartet
	}
}
