package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		JButton btn1 = new JButton("다음 프레임");
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f2 = new JFrame();
				f2.setVisible(true);
				f.setBounds(0, 0, 1000, 1000);
				
			}
		});
		
		f.add(btn1);
		
		
		f.setVisible(true);
		f.setBounds(0, 0, 500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
