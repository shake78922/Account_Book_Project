package accountBook1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

//달력의 반복되는 판넬 객체화
public class DateBox extends JPanel{
	String day;
	Color color;
	int width;
	int height;
	JButton jb = new JButton();
	DateButton click;
	
	public DateBox(String day, Color color, int width, int height) {
		this.day = day;
		this.color = color;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));	
	}
	
	
	public DateBox(String day, Color color, int width, int height,JButton jb,DateButton click) {
		this.day = day;
		this.color = color;
		this.width = width;
		this.height = height;
		this.jb = jb;
		this.click = click;
		setPreferredSize(new Dimension(width, height));	
		jb.setSize(width, height);
		jb.setLayout(null);
		jb.addMouseListener(click);
		add(jb);
	}
	

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);//상자채우기
		
		g.setColor(Color.black);
		
		g.drawString(day, 10, 20);
	}
	
	
}
