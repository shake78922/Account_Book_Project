package login;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Sign_Up_Ui {
	
	public void signUp() {
		
		JFrame sigf = new JFrame("회원가입");
		sigf.setLayout(null);
		String img = "src/Images/Login5-2.png";
		JLabel jl = new JLabel(new ImageIcon(img));
		jl.setBounds(0, 0, 500, 728);
		jl.setLayout(null);
		sigf.add(jl);
		JTextField id = new JTextField(20);
		JLabel ID = new JLabel("ID");
		JPasswordField pw1 = new JPasswordField(20);
		JLabel PW1 = new JLabel("비밀번호");
		PW1.setFont(PW1.getFont().deriveFont(18.0f));
		PW1.setBounds(180, 130, 50, 60);
		ID.setFont(ID.getFont().deriveFont(18.0f));
		ID.setBounds(180, 100, 50, 60);
		id.setBounds(200, 115, 100, 30);
		jl.add(PW1);
		jl.add(ID);
		jl.add(id);
		ID.setForeground(Color.WHITE);
		PW1.setForeground(Color.WHITE);
		
		
		
		
		
		
		
		
		
		
		sigf.setVisible(true);
		sigf.setBounds(0, 0, 500, 728);
		sigf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		Sign_Up_Ui si = new Sign_Up_Ui();
		si.signUp();
	}
	
	
}
