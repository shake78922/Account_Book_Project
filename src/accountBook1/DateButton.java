package accountBook1;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DateButton extends JFrame implements MouseListener{
	JButton jb = new JButton();
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jb.setBackground(Color.pink);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		jb = (JButton)e.getSource();
		jb.setBackground(Color.pink);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		jb = (JButton)e.getSource();
		jb.setOpaque(false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		jb = (JButton)e.getSource();
		jb.setOpaque(false);
		
	}
	
}
