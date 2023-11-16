package login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Sign_Up_Ui {
	
	public JFrame signUp() {
		DBuser db = new DBuser();
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
//		JLabel TEL = new JLabel("전화번호");
//		JTextField tel = new JTextField();
//		TEL.setFont(TEL.getFont().deriveFont(18.0f));
//		TEL.setBounds(120, 340, 100, 50);
//		tel.setBounds(190, 350, 130, 30);
		JLabel ID = new JLabel("ID");
		JPasswordField pw1 = new JPasswordField(20);
		JLabel PW1 = new JLabel("비밀번호");
		JPasswordField pw2 = new JPasswordField(20);
		JLabel PW2 = new JLabel("비밀번호 재입력");
		name.setBounds(190, 200, 130, 30);
		NAME.setBounds(150,200, 40, 30);
		pw2.setBounds(190, 315, 130, 30);
		PW2.setBounds(65, 300, 130, 60);
		PW2.setFont(PW2.getFont().deriveFont(18.0f));
		PW1.setFont(PW1.getFont().deriveFont(18.0f));
		PW1.setBounds(120, 260, 80, 60);
		pw1.setBounds(190, 275, 130, 30);
		ID.setFont(ID.getFont().deriveFont(18.0f));
		ID.setBounds(160, 220, 50, 60);
		id.setBounds(190, 235, 130, 30);
		
		String name1 = name.getText();
		String id1 = id.getText();
		
		searchId.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(id.getText().equals(null)) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요.");
					return;
				}
				
				
				if(db.findID(id.getText())) {
					JOptionPane.showMessageDialog(null, "중복된 아이디 입니다.");
					
				}else {
					JOptionPane.showMessageDialog(null, "사용가능한 아이디 입니다.");
				}
				
			}
		});
		
		char[] overlapPw1 = pw1.getPassword();
		char[] overlapPw2 = pw2.getPassword();
		String ovPw1 = new String(overlapPw1);
		String ovPw2 = new String(overlapPw1);
		
		JButton signUp = new JButton("회원가입");
		signUp.setBounds(200, 400, 100, 40);
		signUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ovPw1 == ovPw2) {
					db.join_membership(id1, ovPw1, name1);
				}
				
			}
		});
		
		jl.add(signUp);
		
		
		
		
		
		
		
		
		
		
		
		
//		jl.add(tel);
//		jl.add(TEL);
		jl.add(name);
		jl.add(NAME);
		jl.add(pw2);
		jl.add(PW2);
		jl.add(pw1);
		jl.add(PW1);
		jl.add(ID);
		jl.add(id);
//		TEL.setForeground(Color.WHITE);
		NAME.setForeground(Color.WHITE);
		PW2.setForeground(Color.white);
		ID.setForeground(Color.WHITE);
		PW1.setForeground(Color.WHITE);
		
		
		
		
		
		
		sigf.setVisible(true);
		sigf.setBounds(0, 0, 500, 728);
		sigf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return sigf;
		
	}
	public static void main(String[] args) {
		Sign_Up_Ui si = new Sign_Up_Ui();
		si.signUp();
	}
	
	
}
