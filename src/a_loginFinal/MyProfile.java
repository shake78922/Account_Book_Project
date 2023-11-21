package a_loginFinal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import accountBook1.DiaryFinal;

public class MyProfile {
	SessionManager sm;
	JFrame profileMenu;
	Map<String, Integer> accountBalances;

	public MyProfile(SessionManager sm) {
		this.sm = sm; // 로그인한 사용자 session 객체 저장
		profileMenu = new JFrame("Profile");
		profileMenu.setLayout(null);
		String background = "src/Images/MENU-3.png"; // 백그라운드 이미지 url
		JLabel menuBackground = new JLabel(new ImageIcon(background)); // 백그라운드 이미지
//--------------------------------------------
		JButton unLogin = new JButton("로그아웃");
		unLogin.setFont(unLogin.getFont().deriveFont(14.0f));
		unLogin.setBounds(260, 500, 100, 40);
		unLogin.addActionListener(new ActionListener() {

			// 로그아웃 후 메인페이지로 이동.
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showOptionDialog(null, "로그아웃", "로그아웃 하시겠습니까 ? ", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (option == JOptionPane.YES_OPTION) {
					sm.logout();
					profileMenu.dispose();
					Login loginMenu = new Login();
				} else if (option == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});
		// -------------------------------------
		// 메뉴에서 캘린더 버튼을 누를시 캘린더 프레임으로 이동
		JButton calendar = new JButton("캘린더");
		calendar.setFont(calendar.getFont().deriveFont(14.0f));
		calendar.setBounds(260, 400, 100, 40);
		calendar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				profileMenu.dispose();
				DiaryFinal diary = new DiaryFinal(sm);

			}
		});
//		----------------------------------------

		// 메뉴버튼에서 계좌번호 입력 & 현금 입력 버튼
		JButton money = new JButton("계좌번호 입력 & 현재 현금");
		money.setFont(money.getFont().deriveFont(14.0f));
		money.setBounds(220, 150, 180, 50);
		money.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame accountMenu = new JFrame();
				accountMenu.setLayout(null);
				String accountMenuBackGroundImages = "src/Images/accountMenu.jpg";
				JLabel accountMenuBackground = new JLabel(new ImageIcon(accountMenuBackGroundImages));
				accountMenuBackground.setBounds(0, 0, 400, 500);

				JLabel cash = new JLabel("현금");
				JTextField cashMoney = new JTextField(20);
				// cashMoney에 문자열이 들어왔는지 확인.
				cashMoney.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						char ch = e.getKeyChar();
						if (!Character.isDigit(ch)) {
							e.consume();
						}
					}
				});
				// ----------------------------
				// 계좌 입력란.
				cashMoney.setBounds(165, 95, 100, 30);
				JLabel account = new JLabel("Account");
				account.setBounds(100, 150, 60, 20);
				JTextField accountField = new JTextField(20);
				accountField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						char ch = e.getKeyChar();
						if (!Character.isDigit(ch)) {
							e.consume();
						}
					}
				});
				accountField.setBounds(165, 145, 100, 30);
				JButton save = new JButton("저장");
				save.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (cashMoney.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
							return;
						}
						if (accountField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "값을 입력해 주세요.");
							return;
						}

						int cashInt = Integer.parseInt(cashMoney.getText());
						int accountInt = Integer.parseInt(accountField.getText());
						DB db = new DB();
						db.updateMoney(cashInt, accountInt, sm.getID());
						JOptionPane.showMessageDialog(null, "저장되었습니다.");
						accountMenu.dispose();
						profileMenu.dispose();
						MyProfile newMenu = new MyProfile(sm);

					}
				});

				save.setBounds(170, 300, 60, 40);
				accountMenuBackground.add(save);
				cash.setBounds(130, 100, 30, 20);
				cash.setForeground(Color.white);
				cash.setFont(cash.getFont().deriveFont(15.0f));
				account.setFont(account.getFont().deriveFont(15.0f));
				account.setForeground(Color.WHITE);
				accountMenu.add(cashMoney);
				accountMenu.add(cash);
				accountMenu.add(account);
				accountMenu.add(accountField);

				accountMenu.add(accountMenuBackground);
				accountMenu.setBounds(0, 0, 400, 500);
				accountMenu.setLocationRelativeTo(null);
				accountMenu.setVisible(true);
			}
		});

		menuBackground.add(money);

		menuBackground.add(calendar);
		menuBackground.add(unLogin);

		fetchAndDisplayAccountBalances();

		menuBackground.setBounds(0, 0, 600, 700);
		profileMenu.add(menuBackground);
		profileMenu.setResizable(false);
		profileMenu.setBounds(0, 0, 600, 700);
		profileMenu.setLocationRelativeTo(null);
		profileMenu.setVisible(true);
		profileMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// 계좌 잔액을 가져와서 화면에 표시하는(private) 메서드
	private void fetchAndDisplayAccountBalances() {
		DB db = new DB(); // DB 객체 생성
		accountBalances = db.getAccountBalances(sm.getID()); // 사용자의 계좌 잔액 가져오기

		int yOffset = 200; // JLabel의 초기 Y 오프셋
		int totalAmount = 0; // 총 잔액을 계산할 변수

		// 계좌 잔액 표시 및 총 잔액 계산
		for (Map.Entry<String, Integer> entry : accountBalances.entrySet()) {
			String accountName = entry.getKey(); // 계좌 이름
			int balance = entry.getValue(); // 잔액

			JLabel accountLabel = new JLabel(accountName + ": " + balance + "원"); // JLabel 생성
			accountLabel.setBounds(100, yOffset, 150, 30); // JLabel 위치 설정
			profileMenu.add(accountLabel); // JFrame에 JLabel 추가

			totalAmount += balance; // 총 잔액 계산
			yOffset += 50; // 다음 JLabel을 위한 Y 오프셋 증가
		}

		JLabel totalAmountLabel = new JLabel("총 자산: " + totalAmount + "원"); // 총 자산 JLabel 생성
		totalAmountLabel.setBounds(100, yOffset, 150, 30); // 총 자산 JLabel 위치 설정
		profileMenu.add(totalAmountLabel); // JFrame에 총 자산 JLabel 추가

		// 생성된 JLabel 수에 기반하여 JFrame 크기 설정
		profileMenu.setSize(400, 200 + (accountBalances.size() * 50));
		profileMenu.setVisible(true); // JFrame 표시
	}

}
