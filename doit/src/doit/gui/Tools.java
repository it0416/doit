package doit.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class Tools {
	
	//Hilfsmethode Question-Textfeld erzeugen
	public static JTextPane createTextPaneQuestion() {
		
		StyleContext sc = new StyleContext();
		final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
		
		JTextPane pane = new JTextPane(doc);
		pane.setEditable(false);
		
		final Style heading2Style = sc.addStyle("Heading2", null);
		heading2Style.addAttribute(StyleConstants.Foreground, Color.DARK_GRAY);
		heading2Style.addAttribute(StyleConstants.FontSize, new Integer(18));
		heading2Style.addAttribute(StyleConstants.FontFamily, "SansSerif");
		heading2Style.addAttribute(StyleConstants.Bold, new Boolean(true));
		doc.setParagraphAttributes(0, 1, heading2Style, false);
		
		return pane;
	}
	
	//Hilfsmethode Question-Text ändern
	public static void setTextQuestion(String text, JTextPane pane) {

	    DefaultStyledDocument doc = (DefaultStyledDocument)pane.getStyledDocument();
	    try {
	    	pane.setText(null);
			doc.insertString(0, text, null);
		} catch (BadLocationException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}
	
	//Hilfsmethode JPanel erzeugen (Breite, Höhe, Farbe, Layout)
	public static JPanel makeJPanel(int width, int height, Color color, GridLayout layout) {
		
		JPanel panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(width, height));
		if(color != null)
			panel.setBackground(color);
		if(layout != null)
			panel.setLayout(layout);
			
		return panel;
	}
    
	//Hilfsmethode JSplitPane erzeugen (Ausrichtung, Position, erstes Objekt, zweites Objekt)
	public static JSplitPane makeJSplitPane(boolean horizontal, int location, Object first, Object second) {

		JSplitPane pane;
	
		if(horizontal) {
			pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    		pane.add((JPanel)first, JSplitPane.LEFT);
    		pane.add((JPanel)second, JSplitPane.RIGHT);
		}
		else {
			pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    		pane.add((JSplitPane)first, JSplitPane.TOP);
    		pane.add((JSplitPane)second, JSplitPane.BOTTOM);
		}
		pane.setDividerLocation(location);
		pane.setDividerSize(0);
		pane.setEnabled(false);
	
		return pane;
   	 }
    
    public static String getTimerString(long milli) {
    	DateFormat df = new SimpleDateFormat("mm:ss");
    	return df.format(new Date(milli));
    }
}
