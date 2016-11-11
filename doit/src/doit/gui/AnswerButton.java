package doit.gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;

// AnswerButtons erbt von JTextArea und erhält die Eigenschaften eines Buttons

public class AnswerButton extends JTextArea {

	 private static final long serialVersionUID = 1L;
	 
	 public Color colorSelected = Color.lightGray;
	 public Color colorUnselected = Color.white;
	 public Color petrol = new Color(26,103,104);
	 
	 public AnswerButton()
	 {
		 super();
		 Border border = BorderFactory.createLineBorder(Color.GRAY);
		 this.setBorder(BorderFactory.createCompoundBorder(border , 
				 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		 this.setEditable(false);
		 this.setFocusable(false);
		 this.setLineWrap(true);
		 this.setWrapStyleWord(true);
		 this.setBackground(colorUnselected);
	 }
		 
	 public void setSelected(boolean selectedState)
	 {
		 if(selectedState)
			 this.setBackground(colorSelected);
		 else
			 this.setBackground(colorUnselected);
	 }
	    
	 
	 public boolean isSelected()
	 {
		 return this.getBackground() == colorSelected;
	 }
	 
	 public void switchSelectionState()
	 {
		 setSelected(!isSelected());
	 }
	 
	 public void setBorderColor(Color color)
	 {
		 Border border = BorderFactory.createLineBorder(color);
		 this.setBorder(BorderFactory.createCompoundBorder(border ,
		 BorderFactory.createEmptyBorder(5, 5, 5, 5))); 
	 }
}
