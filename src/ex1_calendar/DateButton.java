package ex1_calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DateButton extends JButton{
	JPanel pCenter;
	JButton jButton;
	JButton btBack;
	JButton btNext;
	
	public void removeButtonFromPanel(JButton jButton) {
	    pCenter.remove(jButton);
	    pCenter.revalidate();
	    pCenter.repaint();
	}
	
}
