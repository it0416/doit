package doit.xml;

import doit.Doit;
import doit.logic.Question;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.JOptionPane;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//Die Parser-Klasse bietet Methoden um Fragenbezogene
//Daten aus den XML-Dateien zu erhalten
//
//Öffentliche Methoden:
//
//getCategoryList():String[]
//getQuestionCount(String categoryName):int
//getQuestion(String categoryName, String questionID):Question
//getQuestionXml(String categoryName, String questionID):QuestionXml

public class Parser {
	
	//relativer Pfad der XML-Dateien (Ordner)
	private final static String PATH = "xml//";//JAR!!!
//	private final static String PATH = "doit//xml//files//";
	
	public static File getFileResource(String path) {
		ClassLoader classLoader = Doit.class.getClassLoader();
		return new File(classLoader.getResource(path).getFile());
	}
	
	//erstellt ein DOM-Objekt aus einer XML-Datei
	//und gibt es zur weiteren Verwendung zurück
	private static Document getDocument(String categoryName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			File file = getFileResource(PATH + categoryName.replace(' ', '_') + ".xml");
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			return document;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());//Dateifehler
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());//XML-Fehler
		}
		return null;
	}
	
	//erstellt aus den XML-Dateien (Dateinamen) eine Liste der 
	//verfügbaren Kategorien und gibt sie als String-Array zurück
	public static String[] getCategoryList() {
		File directory = getFileResource(PATH);
		if(directory.exists()) {
			String[] categories = directory.list();
			for(int i=0; i<categories.length; i++)//Dateierweiterung entfernen!
				categories[i] = categories[i].substring(0, categories[i].lastIndexOf("."));
			return categories;
		}
		return null; 
	}
	
	//ermittelt die Anzahl der Fragen einer bestimmten Kategorie
	public static int getQuestionsCount(String categoryName) {
		Document document = getDocument(categoryName);
		if(document != null) {
			NodeList questionNodeList = document.getElementsByTagName("id");
			return questionNodeList.getLength();
		}
		return -1;
	}
	
	//Erstellt ein neues Question-Objekt und füllt es
	//mit dem entsprechendem Datensatz aus der XML-Datei
	public static Question getQuestion(String categoryName, String questionID) {
		Document document = getDocument(categoryName);
		if(document != null) {
			//Liste aller Datensätze der Datei
			NodeList nodeList = document.getElementsByTagName("question");
			//für alle Datensätze der Liste...
			for(int i=0; i<nodeList.getLength(); i++) {
				Element element = (Element)nodeList.item(i);
				if(getContent(element, "id") != null) {
					if(getContent(element, "id").equals(questionID)) {
						//wenn Datensatz-ID übereinstimmt...
						//Datensatz in neues Question-Objekt einlesen
						Question question = new Question();
						question.setId(questionID);
						question.setCategory(categoryName);
						question.setDiff(getContent(element, "diff"));
						question.setType(getContent(element, "type"));
						question.setQuestion(getContent(element, "question"));
						//neue Liste (Antworten)
						nodeList = element.getElementsByTagName("proposals");
						element = (Element)nodeList.item(0);
						//Antworten-Array erstellen...
						String[] proposals = new String[] {
							getContent(element, "a"),
							getContent(element, "b"),
							getContent(element, "c"),
							getContent(element, "d")};
						//Antworten mischen und Question-Objekt zurückgeben
						question.setProposals(shuffleProposals(proposals));
						return question;
					}
				}
			}
		}
		return null;//nicht gefunden
	}
	
	//Hilfsmethode: TextContent aus XML-Element
	private static String getContent(Element element, String tag) {
		Node node = element.getElementsByTagName(tag).item(0);
		if(node != null)
			return node.getTextContent();
		return null;
	}	
	
	//Hilfsmethode zum mischen der Antworten
	private static String[] shuffleProposals(String[] proposals) {
		List<String> list = Arrays.asList(proposals);
		Collections.shuffle(list);
		proposals = list.toArray(new String[list.size()]);
		return proposals;
	}
}
