package a_loginFinal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
	DB db = new DB();
	SessionManager sm;
	private JFrame f = new JFrame();

	// 프로그램 시작점.
	public Login() {
		f.setLayout(null);

		// 백그라운드 이미지
		String img = "src/Images/Logingreen.png";
		JLabel jl = new JLabel(new ImageIcon(img));
		jl.setBounds(0, 0, 600, 600);
		jl.setLayout(null);

		String calendar = "src/Images/calendar.png";
		JLabel cal = new JLabel(new ImageIcon(calendar));
		cal.setBounds(279, 165, 40, 46);
		jl.add(cal);

		// 로그인 라벨 & 텍스트필드
		f.add(jl);
		JTextField id = new JTextField(20);
		JPasswordField pw = new JPasswordField(20);
		JButton login = new JButton("Login");
//		JLabel ID = new JLabel("ID");
//		JLabel PW = new JLabel("PW");
//		ID.setBounds(185, 300, 50, 50);
//		PW.setBounds(185, 330, 50, 50);

		id.setBounds(215, 255, 215, 48);
		pw.setBounds(215, 308, 215, 48);
		login.setBounds(205, 400, 100, 40);
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String loginId = id.getText();
				String loginPw = new String(pw.getPassword());
				if (db.authenticateUser(loginId, loginPw)) {
					JOptionPane.showMessageDialog(null, "로그인 성공");
					sm = SessionManager.getInstance();
					sm.setCurrentUser(loginId);
					f.dispose();
					MyProfile mp = new MyProfile(sm);
				} else {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 찾을 수 없습니다.");
				}

			}
		});

//		
//		ID.setForeground(Color.WHITE);
//		PW.setForeground(Color.WHITE);

		jl.add(pw);
//		jl.add(PW);
		jl.add(id);
//		jl.add(ID);
		jl.add(login);

		JButton signUp = new JButton("회원가입");
		signUp.setBounds(310, 400, 100, 40);
		signUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Sign_Up_Ui si = new Sign_Up_Ui();
				si.signUp();
				f.dispose();

			}
		});
		jl.add(signUp);

		f.setResizable(false);
		f.setBounds(0, 0, 600, 600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

}