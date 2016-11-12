package doit.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//Datensatz für den Highscore

public class Highscore {
	
	private final static String PATH = "bin\\doit\\logic\\scores\\";
//	private final static String PATH = "csv//";//Für runnable JAR!!!
	private List<Score> scoreList;
	private Score currentScore;
	private Quiz currentQuiz;
	private String fileName;

	public Highscore(Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
		this.scoreList = new ArrayList<Score>();
		this.fileName = makeFileName(currentQuiz.getCategories());
		readScoreList();
		writeScoreList();
	}
	
	public String getCurrentScore() {
		DateFormat durationFormat = new SimpleDateFormat("mm:ss");
		return "Name: " + currentScore.getUserName() + "\n"
				+ "Ergebnis: " + currentScore.getResult() * 100 + "\u0025\n"
				+ "Dauer: " + durationFormat.format(new Date(currentScore.getDuration()));
	}
	
	public String[] getHighscores() {
		int index = 0;
		String[] arr = new String[scoreList.size()];
		DateFormat durationFormat = new SimpleDateFormat("mm:ss");
    	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    	String formatBackspace= new String();
    	for(Score score : scoreList) {
			if(index<9)
				formatBackspace="  ";
			else
				formatBackspace="";
			arr[index] = formatBackspace+(index + 1) + ".   Name: " + score.getUserName() + "     "
					+ "Ergebnis: " + score.getResult() * 100 + "\u0025     "
					+ "Dauer: " + durationFormat.format(new Date(score.getDuration())) + "     "
					+ "Datum: " + dateFormat.format(new Date(score.getDate()));
			index++;
		}
		return arr;
	}
	
	private void writeScoreList() {
		currentScore = scoreFromQuiz(currentQuiz);
		scoreList.add(currentScore);
		Collections.sort(scoreList, Collections.reverseOrder());
		if(scoreList.size() > 10)
			scoreList = scoreList.subList(0, 10);
		try {
			FileWriter fw = new FileWriter(PATH + fileName + ".csv");
			BufferedWriter bw = new BufferedWriter(fw);
			for(Score s : scoreList) {
				bw.write(csvFromScore(s));
				bw.newLine();
			}
			bw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Score scoreFromQuiz(Quiz quiz) {
		Score score = new Score();
		score.setCategories(quiz.getCategories());
		score.setDate(new Date().getTime());
		score.setDuration(quiz.timer.getDuration());
		score.setFileName(makeFileName(quiz.getCategories()));
		score.setQuizLength(quiz.getTotalQuestionsCount());
		score.setResult(quiz.getQuizResult().getResultPercent());
		score.setUserName(quiz.getUsername());
		return score;
	}
	
	private String makeFileName(String[] categories) {
		String fileName = "";
		for(String category : categories)
			fileName += category.replace(' ', '_') + "_";
		fileName = fileName.substring(0, fileName.length() - 1);
		return fileName;
	}
		
	private String csvFromScore(Score score) {
		String s = score.getUserName() + ","
			+ score.getResult() + ","
			+ score.getDuration() + ","
			+ score.getQuizLength() + ","
			+ score.getDate();
		return s;
	}

	private void readScoreList(){
		try {
			String csv = null;
			scoreList = new ArrayList<Score>();
			String path = PATH + fileName + ".csv";
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			try {
				while((csv = br.readLine()) != null) {
					scoreList.add(scoreFromCSV(csv));
				}
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Score scoreFromCSV(String csvString) {
		String[] values = csvString.split(",");
		Score score = new Score();
		score.setUserName(values[0]);
		score.setResult(Double.valueOf(values[1]));
		score.setDuration(Long.valueOf(values[2]));
		score.setQuizLength(Integer.valueOf(values[3]));
		score.setDate(Long.valueOf(values[4]));
		return score;
	}
}