package accountBook1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import a_loginFinal.DB;
import a_loginFinal.SessionManager;

// icon images @ fontawesome.com
// svg to png @ svgtopng.com
// menu img size 20px, button img size 30px

public class Deposits extends JFrame implements ItemListener, ActionListener {
	private Font fnt_title = new Font("SansSerif", Font.BOLD, 16);
	private Font fnt_regular = new Font("SansSerif", Font.PLAIN, 14);
	private Color myGreen = new Color(207, 250, 211);
	
	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();

	private JLabel dateLabel;
	private JTextField amountTf, descriptionTf;
	private JComboBox<String> paymentTypeCb;
	private JRadioButton r1_1, r1_2, r2, r3, r4, r5;
	private JButton confirmButton, cancelButton;

	private String year, month, clickedDay, depositType;
	private String[] depositData;
	private String dateId;
	private String sqlDateId;

	DB db = new DB();
	SessionManager sm;

	public Deposits(int year, int month, int clickedDay, SessionManager sm) {
		super("입금");
		this.sm = sm;
		this.year = String.valueOf(year);
		this.month = (month < 10 ? "0" : "") + month;
		this.clickedDay = (clickedDay < 10 ? "0" : "") + clickedDay;
		this.dateId = this.year + this.month + this.clickedDay;
		this.sqlDateId = this.year + "-" + this.month + "-" + this.clickedDay;

		// ======= 상단패널 ========

		// 날짜 아이콘
		String calImg = "src/Images/calendar20.png";
		JLabel calLabel = new JLabel(new ImageIcon(calImg));

		// 날짜 라벨
		dateLabel = new JLabel(String.format("%d년 %d월 %d일", year, month, clickedDay));
		dateLabel.setFont(fnt_regular);

		// -------

		// 입금 금액 아이콘
		String amountImg = "src/Images/won_sign20.png";
		JLabel amountLabel = new JLabel(new ImageIcon(amountImg));

		// 입금 금액 텍스트필드
		amountTf = new HintTextField("입금할 금액");
		amountTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!Character.isDigit(ch)) {
					e.consume();
				}
			}
		});
		amountTf.setFont(fnt_regular);

		// -------

		// 자산(결제수단) 아이콘

		String paymentTypeImg = "src/Images/payment_type20.png";
		JLabel paymentTypeLabel = new JLabel(new ImageIcon(paymentTypeImg));

		// 자산(결제수단) 콤보박스
		List<String> paymentTypes = db.getPaymentTypesForUser(sm.getID());
		paymentTypeCb = new JComboBox<>(paymentTypes.toArray(new String[0]));
		paymentTypeCb.setBackground(Color.WHITE);

		// -------

		// 상세내역 아이콘
		String descriptionImg = "src/Images/description20.png";
		JLabel descriptionLabel = new JLabel(new ImageIcon(descriptionImg));

		// 상세 내역 텍스트필드
		descriptionTf = new HintTextField("상세 내역");
		descriptionTf.setFont(fnt_regular);

		// -------
		// 테두리 세팅
		
		topPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		topPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, false));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, false));
		
		// -------

		// 상단 패널 레이아웃
		GroupLayout layout = new GroupLayout(topPanel);
		topPanel.setLayout(layout);
		topPanel.setBackground(myGreen);
		centerPanel.setBackground(Color.white);
		bottomPanel.setBackground(Color.white);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(calLabel)
						.addComponent(amountLabel).addComponent(paymentTypeLabel).addComponent(descriptionLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(dateLabel)
						.addComponent(amountTf).addComponent(paymentTypeCb).addComponent(descriptionTf)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(calLabel)
						.addComponent(dateLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(amountLabel)
						.addComponent(amountTf))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(paymentTypeLabel)
						.addComponent(paymentTypeCb))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(descriptionLabel)
						.addComponent(descriptionTf)));

		add(BorderLayout.NORTH, topPanel);

		// ======= 중앙 패널 =======

		String r1Img = "src/Images/deposit/deposit_sack.png";
		String r2Img = "src/Images/deposit/deposit_bonus.png";
		String r3Img = "src/Images/deposit/deposit_piggy.png";
		String r4Img = "src/Images/deposit/deposit_wallet.png";
		String r5Img = "src/Images/deposit/deposit_etc.png";

		r1_1 = new JRadioButton("월급", new ImageIcon(r1Img));
		r1_2 = new JRadioButton("일급", new ImageIcon(r1Img));
		r2 = new JRadioButton("보너스", new ImageIcon(r2Img));
		r3 = new JRadioButton("적금", new ImageIcon(r3Img));
		r4 = new JRadioButton("용돈", new ImageIcon(r4Img));
		r5 = new JRadioButton("기타", new ImageIcon(r5Img));
		
		r1_1.setForeground(Color.GRAY);
		r1_2.setForeground(Color.GRAY);
		r2.setForeground(Color.GRAY);
		r3.setForeground(Color.GRAY);
		r4.setForeground(Color.GRAY);
		r5.setForeground(Color.GRAY);
		
		r1_1.setBackground(Color.WHITE);
		r1_2.setBackground(Color.WHITE);
		r2.setBackground(Color.WHITE);
		r3.setBackground(Color.WHITE);
		r4.setBackground(Color.WHITE);
		r5.setBackground(Color.WHITE);

		r1_1.setBorderPainted(true);
		r1_2.setBorderPainted(true);
		r2.setBorderPainted(true);
		r3.setBorderPainted(true);
		r4.setBorderPainted(true);
		r5.setBorderPainted(true);
		
		r1_1.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
		r1_2.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
		r2.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
		r3.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
		r4.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
		r5.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
		
		r1_1.setContentAreaFilled(true);
		r1_2.setContentAreaFilled(true);
		r2.setContentAreaFilled(true);
		r3.setContentAreaFilled(true);
		r4.setContentAreaFilled(true);
		r5.setContentAreaFilled(true);

		r1_1.setFocusPainted(false);
		r1_2.setFocusPainted(false);
		r2.setFocusPainted(false);
		r3.setFocusPainted(false);
		r4.setFocusPainted(false);
		r5.setFocusPainted(false);

		r1_1.addActionListener(this);
		r1_2.addActionListener(this);
		r2.addActionListener(this);
		r3.addActionListener(this);
		r4.addActionListener(this);
		r5.addActionListener(this);

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(r1_1);
		radioGroup.add(r1_2);
		radioGroup.add(r2);
		radioGroup.add(r3);
		radioGroup.add(r4);
		radioGroup.add(r5);

		r1_1.addItemListener(this);
		r1_2.addItemListener(this);
		r2.addItemListener(this);
		r3.addItemListener(this);
		r4.addItemListener(this);
		r5.addItemListener(this);

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
		confirmButton.setBackground(myGreen);
		confirmButton.setForeground(Color.GRAY);
		//confirmButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, false));
		cancelButton = new JButton("취소");
		cancelButton.setBackground(myGreen);
		cancelButton.setForeground(Color.GRAY);
		//cancelButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, false));

		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setBackground(Color.white);
		bottomPanel.add(confirmButton);
		bottomPanel.add(cancelButton);

		add(BorderLayout.SOUTH, bottomPanel);

		confirmButton.addActionListener(this);
		confirmButton.setBorderPainted(false);
		cancelButton.addActionListener(this);
		cancelButton.setBorderPainted(false);

		setResizable(false);
		setSize(400, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == confirmButton) {
			onConfirmButtonClicked();
		} else if (obj == cancelButton) {
			Deposits.super.dispose();
		}

		if (e.getSource() == r1_1) {
			JToggleButton selectedButton = r1_1;

			if (selectedButton.isSelected()) {
				// 선택된 경우 색상 변경
				selectedButton.setForeground(Color.BLACK);
				selectedButton.setBackground(myGreen);
				selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
			} else {
				// 선택이 해제된 경우 색상 변경 및 선택 취소
				selectedButton.setForeground(Color.GRAY);
				selectedButton.setBackground(Color.WHITE);
				selectedButton.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
				selectedButton.setSelected(false);
			}
		} else {
			// 다른 버튼의 경우 색상을 원래대로 변경
			r1_1.setForeground(Color.GRAY);
			r1_1.setBackground(Color.WHITE);
			r1_1.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
			r1_1.setSelected(false);
		}

		if (e.getSource() == r1_2) {
			JToggleButton selectedButton = r1_2;

			if (selectedButton.isSelected()) {
				// 선택된 경우 색상 변경
				selectedButton.setForeground(Color.BLACK);
				selectedButton.setBackground(myGreen);
				selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
			} else {
				// 선택이 해제된 경우 색상 변경 및 선택 취소
				selectedButton.setForeground(Color.GRAY);
				selectedButton.setBackground(Color.WHITE);
				selectedButton.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
				selectedButton.setSelected(false);
			}
		} else {
			// 다른 버튼의 경우 색상을 원래대로 변경
			r1_2.setForeground(Color.GRAY);
			r1_2.setBackground(Color.WHITE);
			r1_2.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
			r1_2.setSelected(false);
		}

		if (e.getSource() == r2) {
			JToggleButton selectedButton = r2;

			if (selectedButton.isSelected()) {
				// 선택된 경우 색상 변경
				selectedButton.setForeground(Color.BLACK);
				selectedButton.setBackground(myGreen);
				selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
			} else {
				// 선택이 해제된 경우 색상 변경 및 선택 취소
				selectedButton.setForeground(Color.GRAY);
				selectedButton.setBackground(Color.WHITE);
				selectedButton.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
				selectedButton.setSelected(false);
			}
		} else {
			// 다른 버튼의 경우 색상을 원래대로 변경
			r2.setForeground(Color.GRAY);
			r2.setBackground(Color.WHITE);
			r2.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
			r2.setSelected(false);
		}

		if (e.getSource() == r3) {
			JToggleButton selectedButton = r3;

			if (selectedButton.isSelected()) {
				// 선택된 경우 색상 변경
				selectedButton.setForeground(Color.BLACK);
				selectedButton.setBackground(myGreen);
				selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
			} else {
				// 선택이 해제된 경우 색상 변경 및 선택 취소
				selectedButton.setForeground(Color.GRAY);
				selectedButton.setBackground(Color.WHITE);
				selectedButton.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
				selectedButton.setSelected(false);
			}
		} else {
			// 다른 버튼의 경우 색상을 원래대로 변경
			r3.setForeground(Color.GRAY);
			r3.setBackground(Color.WHITE);
			r3.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
			r3.setSelected(false);
		}

		if (e.getSource() == r4) {
			JToggleButton selectedButton = r4;

			if (selectedButton.isSelected()) {
				// 선택된 경우 색상 변경
				selectedButton.setForeground(Color.BLACK);
				selectedButton.setBackground(myGreen);
				selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
			} else {
				// 선택이 해제된 경우 색상 변경 및 선택 취소
				selectedButton.setForeground(Color.GRAY);
				selectedButton.setBackground(Color.WHITE);
				selectedButton.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
				selectedButton.setSelected(false);
			}
		} else {
			// 다른 버튼의 경우 색상을 원래대로 변경
			r4.setForeground(Color.GRAY);
			r4.setBackground(Color.WHITE);
			r4.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
			r4.setSelected(false);
		}

		if (e.getSource() == r5) {
			JToggleButton selectedButton = r5;

			if (selectedButton.isSelected()) {
				// 선택된 경우 색상 변경
				selectedButton.setForeground(Color.BLACK);
				selectedButton.setBackground(myGreen);
				selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
			} else {
				// 선택이 해제된 경우 색상 변경 및 선택 취소
				selectedButton.setForeground(Color.GRAY);
				selectedButton.setBackground(Color.WHITE);
				selectedButton.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
				selectedButton.setSelected(false);
			}
		} else {
			// 다른 버튼의 경우 색상을 원래대로 변경
			r5.setForeground(Color.GRAY);
			r5.setBackground(Color.WHITE);
			r5.setBorder(BorderFactory.createLineBorder(myGreen, 3, true));
			r5.setSelected(false);
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// RadioButton 아이템 선택 상태가 변경될 때 동작을 정의하는 메서드
		if (e.getStateChange() == ItemEvent.SELECTED) {
			// 선택된 RadioButton에 따라 depositType 설정
			if (e.getSource() == r1_1) {
				depositType = r1_1.getText();
			} else if (e.getSource() == r1_2) {
				depositType = r1_2.getText();
			} else if (e.getSource() == r2) {
				depositType = r2.getText();
			} else if (e.getSource() == r3) {
				depositType = r3.getText();
			} else if (e.getSource() == r4) {
				depositType = r4.getText();
			} else if (e.getSource() == r5) {
				depositType = r5.getText();
			}
		}
	}

	private void onConfirmButtonClicked() {
		// 확인 버튼 클릭 시 동작을 정의하는 메서드
		if (amountTf.getText().isEmpty() || descriptionTf.getText().isEmpty() || depositType == null) {
			JOptionPane.showMessageDialog(null, "모든 필드를 입력해 주세요.");
			return;
		}
		
		PaymentTypeConverter converter = new PaymentTypeConverter();
		String korPaymentType = (String) paymentTypeCb.getSelectedItem();
		String engPaymentType = converter.convertKorToEngPayType(korPaymentType);

		depositData = new String[4];
		depositData[0] = amountTf.getText();
		depositData[1] = depositType;
		depositData[2] = engPaymentType;
		depositData[3] = descriptionTf.getText();

		// 데이터베이스에 입금 데이터 추가
		db.insertDeposit(sm.getID(), sqlDateId, Integer.parseInt(depositData[0]), depositData[1], depositData[2],
				depositData[3]);

		// 입금에 따른 계좌 잔액 업데이트
		db.updateAccountBalancesForDeposits(sm.getID(), depositData);

		Deposits.super.dispose();
	}
}
