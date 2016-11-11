package doit.xml;

//Klasse für das Auslesen der XML-Datensätze.

public class QuestionXml {
	
	private String id;//Index der Frage
	private String diff;//*Schwierigkeitsgrad?
	private String type;//*Fragetyp (single, multi)
	private String category;//Kategorie (Dateiname!)
	private String question;//Eigentlicher Fragetext
	private String[] proposals;//Antworten (korrekte beginnen mit #)
	
	protected QuestionXml() {
		
		id = null;
		diff = null;
		type = null;
		category = null;
		question = null;
		proposals = null;
	}
	
	//Getter & Setter
	public String getId() {return id;}
	public String getDiff() {return diff;}
	public String getType() {return type;}
	public String getCategory() {return category;}
	public String getQuestion() {return question;}
	public String[] getProposals() {return proposals;}
	protected void setId(String id) {this.id = id;}
	protected void setDiff(String diff) {this.diff = diff;}
	protected void setType(String type) {this.type = type;}
	protected void setCategory(String category) {this.category = category;}
	protected void setQuestion(String question) {this.question = question;}
	protected void setProposals(String[] proposals) {this.proposals = proposals;}
}