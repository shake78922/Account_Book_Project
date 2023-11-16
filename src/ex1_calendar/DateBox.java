package ex1_calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DateBox extends JPanel{
	String day;
	Color color;
	int width;
	int height;
	
	public DateBox(String day, Color color, int width, int height) {
		this.day = day;
		this.color = color;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));		
	}
	

	


	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);//상자채우기
		
		g.setColor(Color.black);
		
		g.drawString(day, 10, 20);
	}
}
