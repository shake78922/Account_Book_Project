package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Sign_Up {
	DBuser du = new DBuser();
	
	
	
	public JButton SignUp() {
		
		JButton sig = new JButton("회원가입");
//		JPanel sigp = new JPanel();
		sig.setBounds(175, 600, 75,35);
//		sigp.setOpaque(false);
		sig.setOpaque(false);
//		sigp.add(sig);

        
        //회원가입로직
		sig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame sigf = new JFrame();
				
				//회원가입&아이디중복검사
				String Inputid = JOptionPane.showInputDialog(null,"ID : ");
				
				if(du.findID(Inputid)) {
					JOptionPane.showMessageDialog(null, "중복된 아이디입니다.");
				}else {
					//비밀번호 입력 처리구간
					JPasswordField pwd = new JPasswordField();
					int option = JOptionPane.showOptionDialog(null, pwd, "Password Enter", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					
					//확인버튼을 눌렀을 때 처리.
					if(option == JOptionPane.OK_OPTION) {
						char[] pwCh = pwd.getPassword();
						String password = new String(pwCh);
						
						//비밀번호 확인 입력
						JPasswordField pwd2 = new JPasswordField();
						int option2 = JOptionPane.showOptionDialog(null, pwd2, "Password Check", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, null, null);
						//확인버튼을 눌렸을때 처리
						if(option2 == JOptionPane.OK_OPTION) {
							char[] pwCh2 = pwd2.getPassword();
							String password2 = new String(pwCh2);
							
							//비밀번호가 일치하는지 확인
							if(password.equals(password2)) {
								//일치한다면 이름 입력
								String Name = JOptionPane.showInputDialog(null,"NAME : ");
								
								JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
								//이때 데이터삽입
								du.join_membership(Inputid, password, Name);
								
								
							}else {
								
								
								//비밀번호가 맞지않으면 else구문
								JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
								
							}
							
						}
					}
					
				}
				
				//
			}
			
		}); //ActionListener
		
		
		
		
		
		
		return sig;
	}
}
