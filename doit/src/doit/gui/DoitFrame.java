 package doit.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.util.Timer;
import java.util.TimerTask;
import doit.logic.Quiz;
import doit.xml.Parser;
import doit.logic.Highscore;
import doit.logic.Question;
import doit.gui.AnswerButton;

//hier erstellen wir die Hauptklasse DoitFrame
public class DoitFrame {

	private JFrame mainFrame;
	private JLabel timeLabel;
	private Panels panels;
	private Splitter splitter;
	private JButton actionButton, exitButton; 
	private JTextField progressText, userNameEntry, userNameInfo;
	private JTextPane questionPane;
	private Timer quizTimer;
	private Quiz currentQuiz;
	private Question currentQuestion;
	private AnswerButton[] answerButtons;
	private JToggleButton[] categoryButtons;
	private JProgressBar progressBar;
	private Dimension monitorDimension;
	private JTextArea categoryText, readMeText, highScoreArea;
	private MouseListener answerButtonMouseListener, clearDefaultNameEntry;
	private ProgressPanel progressPanel;
	private String highScoreText;
	
//	Für runnable JAR!!!
//	private final static String README_PATH = "info//readme.txt";
//	private final static String LOGO_PATH = "icons//DOIT.brainlogo.png";
//	private final static String ICON_PATH = "icons//DOIT.targetsize-48.png";
	
	private final static String README_PATH = "bin//doit//files//readme.txt";
	private final static String LOGO_PATH = "bin//doit//icons//DOIT.brainlogo.png";
	private final static String ICON_PATH = "bin//doit//icons//DOIT.targetsize-48.png";
	
	private final static String CATEGORY_MIX_FIAE = "Mix Anwendungsentwicklung";
	private final static String CATEGORY_MIX_FISI = "Mix Systemintegration";
	private final static String START_TEXT = "Willkommen bei DOIT!\n\nDOIT generiert Zufallsfragen aus den letzen IHK-Prüfungen für Fachinformatiker. "
				  + "Geben Sie bitte links einen Spielernamen ein. Anschließend wählen Sie eine der untenstehenden Kategorien aus.";

	class Panels {
		JPanel  logo, progress, login, question, category, proposals, timer, buttons;
	}
	
	class Splitter {
		JSplitPane h1, h2, h3, h4, v1, v2, v3;
	}
		
	public DoitFrame(){
		
		//Initialisierungen
		mainFrame = new JFrame("DO IT");
		actionButton = new JButton("Start");
		exitButton = new JButton("Exit");
		categoryText = new JTextArea();
		timeLabel = new JLabel();
		answerButtons = new AnswerButton[4];
	    timeLabel = new JLabel();
	    categoryButtons = new JToggleButton[6];
	    
		panels = new Panels();
		splitter = new Splitter();
		
		//Anzeige des Infotextes
	    readMeText = new JTextArea();
	    highScoreText= new String("");
	    highScoreArea = new JTextArea();
	    
	    readMeText.setColumns(40);
	    readMeText.setLineWrap(true);
	    readMeText.setWrapStyleWord(true);
	    readMeText.setSize(readMeText.getPreferredSize().width,1);
	    readMeText.setEditable(false);
	    readMeText.setFocusable(false);
	    
	    highScoreArea.setEditable(false);
	    highScoreArea.setFocusable(false);
	    highScoreArea.setBorder(null);
	    //highScoreArea.setColumns(60);
	    highScoreArea.setSize(100, 100);
	    //highScoreArea.setFont(f);
	    
	    //Definition des Hauptfensters
		mainFrame.setSize(800, 600);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setJMenuBar(new Menu());
				
		//Platzierung der GUI in der Mitte
	    monitorDimension = Toolkit.getDefaultToolkit().getScreenSize();  //die auflösung des monitors wird ermittelt
	    mainFrame.setLocation(((monitorDimension.width-800)/2) ,((monitorDimension.height-600)/2)); //wo soll die GUI auftauchen
	    
	    //Fortschritts-Info
	    progressText = new JTextField();
	    progressText.setEditable(false);
	    progressText.setFocusable(false);
	    progressText.setBackground(Color.white);
        clearDefaultNameEntry = new ClearDefaultNameEntry();
	    
        //Eingabe&Anzeige des Benutzernamens
	    userNameEntry = new JTextField("Spieler");
	    userNameEntry.setBackground(Color.lightGray);
	    userNameEntry.setColumns(13);
	    userNameEntry.setBorder(null);
	    setMouseListenerUserName(true);
	    
	    //Anzeige des Benutzernamens
	    userNameInfo = new JTextField("Name:");
	    userNameInfo.setEnabled(false);
	    userNameInfo.setFocusable(false);
	    userNameInfo.setBorder(null);
	    userNameInfo.setColumns(13);
	    userNameInfo.setDisabledTextColor(Color.BLACK);
	    
	    mainFrame.setIconImage(new ImageIcon(ICON_PATH).getImage());

	    //Anzeige der Kategorie
		categoryText.setEditable(false);
		categoryText.setFocusable(false);
		
		categoryText.setWrapStyleWord(true);
		categoryText.setFont(new Font("SansSerif",Font.BOLD,14));
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		categoryText.setBorder(BorderFactory.createCompoundBorder(border , 
		BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		Dimension categoryDimension = new Dimension(190,90);
		categoryText.setPreferredSize(categoryDimension);
		 
	    //Konfiguration der Panels
		//Tools.makeJPanel(Breite, Höhe, Farbe, Layout)
		panels.logo = Tools.makeJPanel(190, 90, Color.white, null);
		panels.progress = Tools.makeJPanel(590, 90, Color.white, new GridLayout(2,1));
		panels.login = Tools.makeJPanel(190, 190, Color.white, null);
		panels.question = Tools.makeJPanel(590, 190, null, new GridLayout());
		panels.category = Tools.makeJPanel(190, 210, Color.white, null);
		panels.proposals = Tools.makeJPanel(590, 210, null, new GridLayout(6,1));
		panels.timer = Tools.makeJPanel(190, 40, Color.white, null);
		panels.buttons = Tools.makeJPanel(590, 40, Color.white, new GridLayout(1, 2, 10, 10));  	    
		
		
		
		//Konfiguration der SplitPanes
		//Tools.makeJSplitPane(Ausrichtung, Position, erstes Objekt, zweites Objekt)
		splitter.h1 = Tools.makeJSplitPane(true, 200, panels.logo, panels.progress);
		splitter.h2 = Tools.makeJSplitPane(true, 200, panels.login, panels.question);
	   	splitter.h3 = Tools.makeJSplitPane(true, 200, panels.category, panels.proposals);
	    splitter.h4 = Tools.makeJSplitPane(true, 200, panels.timer, panels.buttons);
	    
	    splitter.h2.setBorder(null);
	    splitter.h3.setBorder(null);
	    splitter.h4.setBorder(null);
	    splitter.h1.setBorder(null);
	    
	    //hier werden die 4 (splitter.h1-h4) vertikal zusammengesetzt, das Gittermodel nimmt gestallt an.
	   	splitter.v1 = Tools.makeJSplitPane(false, 50, splitter.h1, splitter.h2);
	   	splitter.v2 = Tools.makeJSplitPane(false, 200, splitter.v1, splitter.h3);
	  	splitter.v3 = Tools.makeJSplitPane(false, 480, splitter.v2, splitter.h4);
	  	
	    //das Gittermodell wird in der GUI plaziert
	    mainFrame.getContentPane().add(splitter.v3);

	    questionPane = Tools.createTextPaneQuestion();
	    panels.question.add(questionPane);
	    Tools.setTextQuestion(START_TEXT, questionPane);
	       
	    panels.buttons.add(exitButton);
	    panels.buttons.add(actionButton);
	    
	    actionButton.addActionListener(new ButtonAction());
	    exitButton.addActionListener(new ButtonAction());
	    
	    panels.login.add(new JLabel(new ImageIcon(LOGO_PATH)));
	    panels.logo.add(categoryText);
	    panels.category.add(userNameInfo);
	    panels.category.add(userNameEntry);
		
		exitButton.setVisible(false);
		actionButton.setVisible(false);
	    
        answerButtonMouseListener = new AnswerButtonAction();                	    
        
        //Kategorie-Auswahl-Buttons
	    String [] categories = Parser.getCategoryList();
	    for (int i = 0; i < 6; i++) {
	    	
	    	categoryButtons[i] = new JToggleButton("Kategorie " + (i+1));
	    	categoryButtons[i].setFont(new Font("SansSerif",Font.ITALIC,14));
	    	categoryButtons[i].addActionListener(new CategoryListener());
	    	categoryButtons[i].setBackground(Color.white);
	    	if(i<4)
	    		categoryButtons[i].setText("" + categories[i].replace('_', ' '));
	    	panels.proposals.add(categoryButtons[i]); 
	    	categoryButtons[i].setVisible(true);
	    }
	    categoryButtons[4].setText(CATEGORY_MIX_FIAE);
	    categoryButtons[5].setText(CATEGORY_MIX_FISI);
	    
	    mainFrame.setVisible(true);	
	 }

	 private void fillReadMeText() {
		 String readMe;
	     try {
	    	 FileReader fr = new FileReader(README_PATH);
	    	 BufferedReader br = new BufferedReader(fr);
	    	 while((readMe = br.readLine()) != null)                                   
	    		 readMeText.append(readMe + "\n");
	    	 fr.close();
	    	 }
	     catch(IOException e) {
	    	 readMeText.setText(e.toString());
	    	 }
	     catch(Exception e) {
	    	 readMeText.setText(e.toString()); 
	     }
	 }  

	 //Einstellung, ob die AnswerButtons auf Mausklick die Hintergrundfarbe ändern oder nicht
	 private void setMouseListenerAnswerButton(boolean state)
	 {
		for(int i=0; i < 4 ; i++)
		{
			if(state)
				answerButtons[i].addMouseListener(answerButtonMouseListener);
			else
				answerButtons[i].removeMouseListener(answerButtonMouseListener);
		}
	 }

	 private void setMouseListenerUserName(boolean state)
	 {
		
		if(state)
			userNameEntry.addMouseListener(clearDefaultNameEntry);
		else
			userNameEntry.removeMouseListener(clearDefaultNameEntry);
	 }

	 private void setButtonUnselected()
	 {
		 for(int i=0; i < 4 ; i++)
			 answerButtons[i].setSelected(false);		 
	 }
	 
	 private void setAnswerButtonText()
	 {
		String[] antwort = currentQuestion.getProposals();
		for(int i=0; i < 4 ; i++)
			answerButtons[i].setText(removeHash(antwort[i]));	 
	 }
	 
	 private void setButtonsVisible(boolean visible)
	 {	 
		 if(visible != answerButtons[0].isVisible())
			 for(int i=0; i < 4 ; i++)
				 answerButtons[i].setVisible(visible);
	 }
	 
	 private String removeHash(String antwort)
	 {
	  if(antwort.substring(0,1).equals("#"))
			antwort = antwort.substring(1);
	  return antwort;
	 }
	 
	 private void colorCorrectAnswer(boolean check)
	 { 
		 if(check)
	     {
			 String[] antwort=currentQuestion.getProposals();
		     for(int i = 0; i < 4; i++)
			   if(antwort[i].substring(0,1).equals("#"))
		   		  answerButtons[i].setBorderColor(Color.GREEN);
	     }
		 else
			 for(int i = 0; i < 4; i++)
				 answerButtons[i].setBorderColor(Color.GRAY);
	 }
	 
	 private boolean[] getButtonsState() {
		 boolean[]stateArray= new boolean[4];
		 for(int i=0; i < 4 ; i++){
			 stateArray[i]=answerButtons[i].isSelected();
  		  	}
		 return stateArray;
	 }

	 private void startTimer(){
		 quizTimer = new Timer();
		 quizTimer.schedule(new TimerTask() {
	    	@Override
	    	public void run() {
		    	//wird im Sekundentakt ausgeführt!
	    		if(currentQuiz != null)
				    timeLabel.setText(Tools.getTimerString(currentQuiz.timer.getDuration()));
	    	}
	    }, 1000, 1000);
	 }
	 
	//innere Klassen
	//hier machen wir die Menü-Klasse, erbt von JMenuBar
	class Menu extends JMenuBar{
	    
		private static final long serialVersionUID = 1L;
		
		JMenu mainMenu;
	    JMenuItem exit;
	    JMenuItem readme;
	    
	    //Konstruktor von Menu1
	    Menu(){
	    	super();
	    	//Das Menü wird auf der GUI als "Menü" beschriftet
	    	mainMenu = new JMenu("Menü");
	    	//Das M im Menü ist mit einem unterstrich
	    	mainMenu.setMnemonic('M');         
	    	//das Exit im menü wird erstell
	    	exit = new JMenuItem("Exit");
	    	//Das E von Exit hat einen Unterstrich
	    	exit.setMnemonic('E');
	    	//das readme im menü wird erstell
	    	readme = new JMenuItem("Read Me");
	    	//Das readme von Exit hat einen Unterstrich
	    	readme.setMnemonic('R');
	      
	    	//der Unterpunkt Read Me wird dem Menü zugefügt und sichtbar gemacht
	    	mainMenu.add(readme);
	    	//Trennlinie hinzufügen
	    	mainMenu.addSeparator();
	    	//der Unterpunkt Exit wird dem Menü zugefügt und sichtbar gemacht
	    	mainMenu.add(exit);
   
	    	//Das Menü wird dem JFrame zugefügt und sichtbar gemacht
	    	this.add(mainMenu);
	      
	    	//Tastaturkürzel
	    	//was passiert wenn man E drückt
	    	exit.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));
	    	//Der Unterpunkt im Menü,Exit, wird dem Actionlistener zugewiesen
	    	exit.addActionListener(new MenuAction());
	    	//was passiert wenn man R drückt
	    	readme.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK));
	    	//Der Unterpunkt im Menü,Exit, wird dem Actionlistener zugewiesen
	    	readme.addActionListener(new MenuAction());
	         
	    }
	}

	class ClearDefaultNameEntry extends MouseAdapter{
		public void mouseClicked(MouseEvent e) { 
			if(userNameEntry.getText().equals("Spieler"))
				userNameEntry.setText("");
		}
	}
	
	
	// AnswerButtonAction realisiert die Toggleraktion der AnswerButton auf Mausklick
	class AnswerButtonAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
		Component answerbutton = e.getComponent();
			for (int i = 0; i < 4; i++)
				if(answerbutton == answerButtons[i])
					answerButtons[i].switchSelectionState();
			}
		}

	class CategoryListener implements ActionListener {
		@Override
		//Auswahl anderer Kategorien zurücksetzen
		public void actionPerformed(ActionEvent e) {
			for(JToggleButton button : categoryButtons) {
				if(!button.equals(e.getSource()))
					button.setSelected(false);
			}
			JToggleButton buttonSelected = (JToggleButton) e.getSource();
			buttonSelected.setSelected(true);
			if(buttonSelected.isSelected())
				actionButton.setVisible(true);
		}
	}
	
	class MenuAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "Exit"://wenn mann exit drückt, wird das programm beendet
				System.exit(0);
				break;
			case "Read Me"://wenn mann readme drückt, wird ein Dialogfeld geöffnet
	        	fillReadMeText();
	        	JScrollPane readmeScroll = new JScrollPane(readMeText);
	        	readmeScroll.setPreferredSize(new Dimension(520, 380));
	        	JOptionPane.showMessageDialog(mainFrame, readmeScroll, "Readme", JOptionPane.PLAIN_MESSAGE);
				break;
			}
		}
	}
	
	 //die Klasse ButtonAction erbt die Methoden von Actionlistener
	 class ButtonAction implements ActionListener {
		 @Override
		 public void actionPerformed(ActionEvent e) {
			 switch(e.getActionCommand()) {
			 
			 case "Start":
				 
	        	 if(evaluateUserEntry()) {
	        		 startQuiz();
	        		 startTimer();
	        		 categoryText.setLineWrap(true);
	        		 currentQuestion = currentQuiz.getNextQuestion();
		             categoryText.setText(currentQuestion.getCategory().replace("_", " "));
	        		 if(currentQuestion != null)
	        			 startNewQuestion();
	        		 else endOfQuiz("Quiz konnte nicht erstellt werden");
	        		 }
	        	 
				 break;
				 
			 case "Weiter":
				 
	        	 if(currentQuestion != null)
	        		 actionButton.setText("Beantworten");
	        	 currentQuestion = currentQuiz.getNextQuestion();
	        	 if(currentQuestion != null)
	        		 nextQuestion();
	        	 else endOfQuiz("Keine weiteren Fragen");
	        	 
				 break;
				 
			 case "Beantworten":
				 
	        	 currentQuiz.timer.stop();
	        	 currentQuiz.evaluateAnswers(currentQuestion, getButtonsState());
	        	 setMouseListenerAnswerButton(false);
	        	 colorCorrectAnswer(true);
	        	 if(currentQuestion.isCorrect())
	        		 progressPanel.colorStatusPanel((currentQuiz.getDoneQuestionsCount()-1), new Color(26,103,104));
	        	 else
	        		 progressPanel.colorStatusPanel((currentQuiz.getDoneQuestionsCount()-1), new Color(193,1,43));
	        	 actionButton.setText("Weiter");
	        	 
				 break;
			case "Neustart":
				  mainFrame.setVisible(false);
	        	  mainFrame.dispose();
	        	  new DoitFrame(); 
			break;
			case "Exit":
				System.exit(0);
				break;
			 }
	      }
	 }
	 
	 private boolean evaluateUserEntry()
	 {
		 String userNameText = userNameEntry.getText();
		 if(userNameText.length()>15)
		 {
     		 userNameEntry.setBorder(new LineBorder(Color.RED));
     		 JOptionPane.showMessageDialog(mainFrame,
     				 "Bitte geben Sie einen Spielernamen mit bis zu 15 Zeichen ein.",
     				 "Spielername ist ungültig", JOptionPane.WARNING_MESSAGE);
     		 userNameEntry.setBorder(null);
     		 userNameEntry.setText("Spieler");
     		 return false;
		 }
		 return true;
	 }
		
	 //Gemischte Kategorie-Arrays für Quiz-Konstruktor erstellen
	 private String[] createMixCategories(String [] categories, String mix) {
		 String [] mixedCategories = new String[categories.length - 1];
		 int index = 0;
		 for(int i = 0; i < categories.length; i++) {
			 if(categories[i].indexOf(mix) < 0){
				 mixedCategories[index] = categories[i];
			     index++;
			 } 
		 }
		 return mixedCategories;
	 }
	 
	 private String[] getCategoryList() {
		 String [] categoryList = null;  
		 for (int i = 0; i < 6; i++) {
			 if(categoryButtons[i].isSelected()) {
				 String text = categoryButtons[i].getText();
				 if(text == CATEGORY_MIX_FIAE)
					 categoryList = createMixCategories(Parser.getCategoryList(), "System");
				 else if(text == CATEGORY_MIX_FISI)
					 categoryList = createMixCategories(Parser.getCategoryList(), "Anwendung");
				 else categoryList = new String[]{text};
				 }
			 categoryButtons[i].setVisible(false);
			 }
		 return categoryList;
	 }
	 
	 private void startQuiz() {
		 
		 currentQuiz = new Quiz(getCategoryList(), 10, true);
		 currentQuiz.setUsername(userNameEntry.getText());
		 actionButton.setText("Beantworten");
		 
		 progressPanel = new ProgressPanel(currentQuiz.getTotalQuestionsCount());
		 progressBar = new JProgressBar(0, currentQuiz.getTotalQuestionsCount());
		 progressBar.setValue((currentQuiz.getDoneQuestionsCount()+1));
		 progressBar.setStringPainted(true);

		 //Nur beim 1. Start ausführen!
		 panels.progress.add(progressPanel);
		 panels.progress.add(progressText);
		 panels.timer.add(timeLabel);
		 
		 progressBar.setVisible(true);
		 progressText.setVisible(true);
		 panels.progress.setVisible(true);
		 progressText.setBorder(new LineBorder(Color.WHITE));
		 
		 userNameEntry.setBackground(Color.white);
         userNameEntry.setEditable(false);
         userNameEntry.setFocusable(false);
         userNameInfo.setText("Name:");
         setMouseListenerUserName(false);
		 
		 panels.proposals.removeAll();
		 panels.proposals.setLayout(new GridLayout(4,1));
		 
		 //hier werden unsere Antwort-Buttons erstellt
		 for (int i = 0; i < 4; i++) {
			 answerButtons[i] = new AnswerButton();
			 answerButtons[i].setFont(new Font("SansSerif",Font.ITALIC,14));
  		     answerButtons[i].setBackground(Color.white);
  		     panels.proposals.add(answerButtons[i]); 
  		     answerButtons[i].setVisible(false);
  		     }
		 
		 setMouseListenerAnswerButton(true);
	 }
	 
	 private void nextQuestion() {
		 
		 startNewQuestion();
		 setButtonUnselected();
		 currentQuiz.timer.start();
		 colorCorrectAnswer(false);
		 setMouseListenerAnswerButton(true);
		 categoryText.setText(currentQuestion.getCategory().replace("_", " "));
	 }
	 
	 private void startNewQuestion() {
		 Tools.setTextQuestion(currentQuestion.getQuestion(), questionPane);
		 setAnswerButtonText();
		 setButtonsVisible(true);
		 progressPanel.colorStatusPanel(currentQuiz.getDoneQuestionsCount(), Color.LIGHT_GRAY);		  
		 progressText.setText("Frage " 
		      + (currentQuiz.getDoneQuestionsCount()+1)+ " von "
		      + currentQuiz.getTotalQuestionsCount()+"          Sie haben "
		      + currentQuiz.getCorrectQuestionsCount()+" richtige Antworten!");
	 }
	 
	 private void endOfQuiz(String message) {
		 
		 currentQuiz.timer.stop();
		 Tools.setTextQuestion(message, questionPane);
		 exitButton.setVisible(true);
		 categoryText.setVisible(false);
		 setButtonsVisible(false);
		 doHighscore();
	 }
	 
	 private void doHighscore() {
		 
    	  Highscore score = new Highscore(currentQuiz);
		  String currentScore = score.getCurrentScore();
		  String[] highScore = score.getHighscores();
		  
		  Tools.setTextQuestion("Auswertung\n" + currentScore +"\n"+ currentQuiz.getQuizResult().getResultIHK(), questionPane);
		  actionButton.setText("Neustart");
		  exitButton.setVisible(true);
		  timeLabel.setVisible(false);
		  userNameEntry.setVisible(false);
		  userNameInfo.setVisible(false);
		  setButtonsVisible(false);
		  
		  highScoreText = "Highscoreliste\n\n";
		  for(int i = 0; i < highScore.length; i++)
			  highScoreText += highScore[i] + "\n";	
		  
		  panels.proposals.removeAll();
		  panels.proposals.setLayout(new GridLayout(1,1));
		  panels.proposals.setFont(new Font("SansSerif",Font.BOLD,16));
		  
		  panels.proposals.add(highScoreArea);
		  highScoreArea.setText(highScoreText);
	}
}
