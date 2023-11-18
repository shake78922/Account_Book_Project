package accountBook1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

// icon images @ fontawesome.com
// svg to png @ svgtopng.com
// font size 20

public class Deposits extends JFrame implements ItemListener, ActionListener{
	Font fnt_title = new Font("SansSerif", Font.BOLD, 16);
	Font fnt_regular = new Font("SansSerif", Font.PLAIN, 14);
	
	JPanel topPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	JLabel dateLabel;
	JTextField amountTf, descriptionTf;
	JRadioButton r1_1,r1_2,r2,r3,r4,r5;
	JButton confirmButton, cancelButton;
	
	

	
	
	
	
	
	
	
	
	
	
	
	public Deposits(int year, int month, int clickedDay) {
		super("입금");
		
		// ======= 상단패널 ========
		
		// 날짜 아이콘
		String calImg = "src/Images/calendar20.png";
		JLabel calLabel = new JLabel(new ImageIcon(calImg));
		calLabel.setBounds(0,0,50,50);

		// 날짜 라벨
		dateLabel = new JLabel(String.format("%d년 %d월 %d일", year, month, clickedDay));
		dateLabel.setBounds(47,0,400,50);
		dateLabel.setFont(fnt_regular);
		
		// -------
		
		// 입금 금액 아이콘
		String amountImg = "src/Images/won_sign20.png";
		JLabel amountLabel = new JLabel(new ImageIcon(amountImg));
		amountLabel.setBounds(0,45,50,50);

		//입금 금액 텍스트필드
		amountTf = new HintTextField("입금할 금액");
		amountTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if(!Character.isDigit(ch)) {
					e.consume();
				}
			}
		});
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
		
		// ======= 중앙 패널 =======
		
		String r1Img = "src/Images/deposit_sack.png";
		String r2Img = "src/Images/deposit_bonus.png";
		String r3Img = "src/Images/deposit_piggy.png";
		String r4Img = "src/Images/deposit_wallet.png";
		String r5Img = "src/Images/deposit_etc.png";
		
		r1_1 = new JRadioButton("월급", new ImageIcon(r1Img));
		r1_2 = new JRadioButton("일급", new ImageIcon(r1Img));
		r2 = new JRadioButton("보너스", new ImageIcon(r2Img));
		r3 = new JRadioButton("적금", new ImageIcon(r3Img));
		r4 = new JRadioButton("용돈", new ImageIcon(r4Img));
		r5 = new JRadioButton("기타", new ImageIcon(r5Img));
		
		r1_1.setBorderPainted(true);
		r1_2.setBorderPainted(true);
		r2.setBorderPainted(true);
		r3.setBorderPainted(true);
		r4.setBorderPainted(true);
		r5.setBorderPainted(true);
		
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(r1_1);
		radioGroup.add(r1_2);
		radioGroup.add(r2);
		radioGroup.add(r3);
		radioGroup.add(r4);
		radioGroup.add(r5);
		
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(r1_1);
		centerPanel.add(r1_2);
		centerPanel.add(r2);
		centerPanel.add(r3);
		centerPanel.add(r4);
		centerPanel.add(r5);
		
		add(BorderLayout.CENTER, centerPanel);
		
		// ======= 하단 패널 =======
		
		confirmButton = new JButton("확인");
		cancelButton = new JButton("취소");
		
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(confirmButton);
		bottomPanel.add(cancelButton);
		
		add(BorderLayout.SOUTH, bottomPanel);
		
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		
		setResizable(false);
		setSize(400,400);
		setLocationRelativeTo(null);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == confirmButton) {
			
			Deposits.super.dispose();
		}else if(obj == cancelButton) {
			Deposits.super.dispose();
		}
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