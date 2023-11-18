package accountBook1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// icon images @ fontawesome.com
// svg to png @ svgtopng.com
// font size 20

public class Deposits extends JFrame implements ItemListener, ActionListener{
	Font fnt_title = new Font("SansSerif", Font.BOLD, 16);
	Font fnt_regular = new Font("SansSerif", Font.PLAIN, 14);
	
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	public Deposits(int year, int month, int clickedDay) {
		super("입금");
		
		// ======= 상단패널 ========
		
		// 날짜 아이콘
		String calImg = "src/Images/calendar20.png";
		JLabel calLabel = new JLabel(new ImageIcon(calImg));
		calLabel.setBounds(0,0,50,50);

		// 날짜 라벨
		JLabel dateLabel = new JLabel(String.format("%d년 %d월 %d일", year, month, clickedDay));
		dateLabel.setBounds(47,0,400,50);
		dateLabel.setFont(fnt_regular);
		
		// -------
		
		// 입금 금액 아이콘
		String amountImg = "src/Images/won_sign20.png";
		JLabel amountLabel = new JLabel(new ImageIcon(amountImg));
		amountLabel.setBounds(0,45,50,50);

		//입금 금액 텍스트필드
		JTextField amountTf = new HintTextField("입금할 금액");
		amountTf.setBounds(45,55,150,30);
		amountTf.setFont(fnt_regular);
		
		// -------
		
		// 상세내역 아이콘
		String descriptionImg = "src/Images/description20.png";
		JLabel descriptionLabel = new JLabel(new ImageIcon(descriptionImg));
		descriptionLabel.setBounds(0,90,50,50);

		// 상세 내역 텍스트필드
		JTextField descriptionTf = new HintTextField("상세 내역");
		descriptionTf.setBounds(45,100,400,30);
		descriptionTf.setFont(fnt_regular);
		
		// -------
		
		// 상단 패널 레이아웃
		GroupLayout layout = new GroupLayout(topPanel);
		topPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				   		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   				.addComponent(calLabel)
				   				.addComponent(amountLabel)
				   				.addComponent(descriptionLabel))
				   		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   				.addComponent(dateLabel)
				   				.addComponent(amountTf)
				   				.addComponent(descriptionTf))
				);
		layout.setVerticalGroup(
				   layout.createSequentialGroup()
				      	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				      			.addComponent(calLabel)
				      			.addComponent(dateLabel))
		      			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		      					.addComponent(amountLabel)
						        .addComponent(amountTf))
		      			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		      					.addComponent(descriptionLabel)
						        .addComponent(descriptionTf))
				);
		
		add(BorderLayout.NORTH, topPanel);
		
		// ======= 하단 패널 =======
		
		
		

		setSize(400,400);
		setLocationRelativeTo(null);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}

class HintTextField extends JTextField {
	private final String _hint;
	
    public HintTextField(String hint) {
        _hint = hint;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }
    
}
