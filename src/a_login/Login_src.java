package a_login;


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


public class Login_src {
	DBuser du = new DBuser();
	private JFrame f = new JFrame();
	
	//프로그램 시작점.
	public Login_src() {
		f.setLayout(null);
		
		//백그라운드 이미지
		String img = "src/Images/Login5.png";
		JLabel jl = new JLabel(new ImageIcon(img));
		jl.setBounds(0, 0, 500, 728);
		jl.setLayout(null);
		
		String calendar = "src/Images/calendar.png";
		JLabel cal = new JLabel(new ImageIcon(calendar));
		cal.setBounds(240, 240, 40, 46);
		jl.add(cal);
		
		
		//로그인 라벨 & 텍스트필드
		f.add(jl);
		JTextField id = new JTextField(20);
		JPasswordField pw = new JPasswordField(20);
		JButton login = new JButton("Login");
		JLabel ID = new JLabel("ID");
		JLabel PW = new JLabel("PW");
		ID.setBounds(200, 300, 50, 50);
		PW.setBounds(200, 330, 50, 50);
		
		id.setBounds(220, 315, 100, 23);
		pw.setBounds(220, 342, 100, 23);
		login.setBounds(320, 320, 50, 40);
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String loginId = id.getText();
				String loginPw = new String(pw.getPassword());
				if(du.findID(loginId) && du.findPW(loginPw)) {
					JOptionPane.showMessageDialog(null, "로그인 성공");
					f.dispose();
					MyProfileMenu mu = new MyProfileMenu();
				}else {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 찾을 수 없습니다.");
				}
				
			}
		});
		
		
		
		
		ID.setForeground(Color.WHITE);
		PW.setForeground(Color.WHITE);
		
		
		jl.add(pw);
		jl.add(PW);
		jl.add(id);
		jl.add(ID);
		jl.add(login);
		
		
		
		
		
		
		
		JButton signUp = new JButton("회원가입");
		signUp.setBounds(220, 600, 80, 40);
		signUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Sign_Up_Ui si = new Sign_Up_Ui();
				si.signUp(signUp);
				f.dispose();
				
				
			}
		});
		jl.add(signUp);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		f.setResizable(false);
		f.setBounds(0, 0, 500, 728);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
			
	}
	
	
}
