package a_loginFinal;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import accountBook1.*;

public class Sign_Up_Ui {
	private boolean idCheck = false;

	public JFrame signUp() {
		DB db = new DB(); // Creating a new instance of the DB class
		JFrame sigf = new JFrame("회원가입");
		sigf.setLayout(null);
		String img = "src/Images/Login5-2.png";
		String userPng = "src/Images/user-solid.png";
		JLabel userPngjl = new JLabel(new ImageIcon(userPng));
		JButton searchId = new JButton("ID중복확인");
		searchId.setBounds(320, 235, 90, 30);
		userPngjl.setBounds(220, 100, 50, 57);
		JLabel jl = new JLabel(new ImageIcon(img));
		jl.add(searchId);
		jl.add(userPngjl);
		jl.setBounds(0, 0, 500, 728);
		jl.setLayout(null);
		sigf.add(jl);
		JTextField name = new JTextField(20);
		JLabel NAME = new JLabel("이름");
		JTextField id = new JTextField(20);
		NAME.setFont(NAME.getFont().deriveFont(18.0f));
		JLabel ID = new JLabel("ID");
		JPasswordField pw1 = new JPasswordField(20);
		JLabel PW1 = new JLabel("비밀번호");
		JPasswordField pw2 = new JPasswordField(20);
		JLabel PW2 = new JLabel("비밀번호 재입력");
		name.setBounds(190, 200, 130, 30);
		NAME.setBounds(150, 200, 40, 30);
		pw2.setBounds(190, 315, 130, 30);
		PW2.setBounds(65, 300, 130, 60);
		PW2.setFont(PW2.getFont().deriveFont(18.0f));
		PW1.setFont(PW1.getFont().deriveFont(18.0f));
		PW1.setBounds(120, 260, 80, 60);
		pw1.setBounds(190, 275, 130, 30);
		ID.setFont(ID.getFont().deriveFont(18.0f));
		ID.setBounds(160, 220, 50, 60);
		id.setBounds(190, 235, 130, 30);

		JLabel res = new JLabel();
		res.setBounds(335, 255, 200, 30);
		jl.add(res);

		searchId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (id.getText().isEmpty()) {
					res.setText("아이디를 입력해 주세요.");
					return;
				}
				if (db.findID(id.getText())) {
					res.setText("중복된 아이디 입니다.");
				} else {
					res.setText("사용가능한 아이디 입니다.");
					idCheck = true;
				}

			}
		});

		JButton signUp = new JButton("회원가입");
		signUp.setBounds(200, 400, 100, 40);
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name1 = name.getText();
				String id1 = id.getText();
				char[] overlapPw1 = pw1.getPassword();
				char[] overlapPw2 = pw2.getPassword();
				String ovPw1 = new String(overlapPw1);
				String ovPw2 = new String(overlapPw2);

				if (!idCheck) {
					JOptionPane.showMessageDialog(null, "ID중복을 확인해 주세요.");
					return;
				}
				if (id1.isEmpty() || name1.isEmpty() || ovPw1.isEmpty() || ovPw2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "모든 필드를 입력해 주세요.");
					return;
				}
				if (!ovPw1.equals(ovPw2)) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
					return;
				}

				db.insertUser(id1, ovPw1, name1);

				// Automatic login after successful signup
				SessionManager sm = SessionManager.getInstance();
				sm.setCurrentUser(id1);

				// Transition to the DiaryFinal class
				MyProfile mp = new MyProfile(sm); // Create an instance of DiaryFinal

				JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다 !");
				sigf.dispose();
			}
		});

		jl.add(signUp);
		jl.add(name);
		jl.add(NAME);
		jl.add(pw2);
		jl.add(PW2);
		jl.add(pw1);
		jl.add(PW1);
		jl.add(id);
		jl.add(ID);

		NAME.setForeground(Color.WHITE);
		PW2.setForeground(Color.white);
		ID.setForeground(Color.WHITE);
		PW1.setForeground(Color.WHITE);

		sigf.setResizable(false);
		sigf.setVisible(true);
		sigf.setBounds(0, 0, 500, 728);
		sigf.setLocationRelativeTo(null);
		sigf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		return sigf;
	}
}