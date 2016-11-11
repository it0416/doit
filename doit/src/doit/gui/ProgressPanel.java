package doit.gui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ProgressPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JToggleButton [] toggleButtons;

	public ProgressPanel(int questlength){
        super();
		this.toggleButtons = new JToggleButton[questlength];
        this.setLayout(new GridLayout());
        this.setBorder(null);
         
       // Array mit JToggleButtons  wird erstellt
        for(int i =0; i < questlength; i++){
        	toggleButtons[i] = new JToggleButton();
        	toggleButtons[i].setBackground(Color.GRAY);
        	toggleButtons[i].setEnabled(false);
        	toggleButtons[i].setVisible(true);
        	this.add(toggleButtons[i]);
        }
        this.setVisible(true);
    }
	
	public Color colorStatusPanel(int position, Color color) {
		this.toggleButtons[position].setBackground(color);
        this.setVisible(true);
        return color;
    }
}