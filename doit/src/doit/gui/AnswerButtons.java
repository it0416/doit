package doit.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

// AnswerButtons erbt von JTextArea und erhält die Eigenschaften eines Buttons

public class AnswerButtons extends JTextArea {

	 private static final long serialVersionUID = 4409054387008721215L;
	

	 public Color selectedColor = Color.lightGray;
	 public Color unselectedColor = Color.white;
	 
	 public AnswerButtons()
	 {
		 super();
		 Border border = BorderFactory.createLineBorder(Color.GRAY);
		 this.setBorder(BorderFactory.createCompoundBorder(border , 
		 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		 this.setEditable(false);
		 this.setFocusable(false);
		 this.setLineWrap(true);
		 this.setWrapStyleWord(true);
		 this.setBackground(unselectedColor);
		 this.setFont(new Font("SansSerif",Font.ITALIC,14));
		
		 
		 
		//answerButton Text zentrieren fehlt noch
	 }
	 
	 public AnswerButtons(String text)
	 {
		 super();
		 this.setEditable(false);
		 this.setFocusable(false);
		 this.setLineWrap(true);
		 this.setWrapStyleWord(true);
		 this.setBackground(unselectedColor);
		 this.setBorder(new LineBorder(Color.GRAY)); 
		 Border border = BorderFactory.createLineBorder(Color.GRAY);
		 this.setBorder(BorderFactory.createCompoundBorder(border ,
		 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	 }
		 
	 public void setSelected(boolean stateSelect)
	 {
		 if(stateSelect){
			 this.setBackground(selectedColor);
		 }
		 else
			 this.setBackground(unselectedColor);
	 }
	    
	 
	 public boolean isSelected()
	 {
		 boolean choosen;
		 if(this.getBackground()== selectedColor)
		 {
			 choosen=true;
		 }
		 else
			 choosen=false;
		 return choosen;	 
	 }
	 
	 public void switchSelectionState()
	 {
		 if(this.getBackground()==unselectedColor)	
			 this.setBackground(selectedColor);
				else
					this.setBackground(unselectedColor);
	 }
	 
	 public void setBorderColor(Color changeColor)
	 {
		 Border border = BorderFactory.createLineBorder(changeColor);
		 this.setBorder(BorderFactory.createCompoundBorder(border ,
		 BorderFactory.createEmptyBorder(5, 5, 5, 5))); 
	 }
	 

}
//class Testlistener extends MouseAdapter{
//	 @Override
//	
//	 public void mouseClicked(MouseEvent e){
//		
//	
//		
//	 }
//}
