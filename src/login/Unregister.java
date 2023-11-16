package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Unregister {
	DBuser du = new DBuser();
	
	
	
	public JButton unSignUp() {
		
		JButton unregister = new JButton("회원탈퇴");
		unregister.setBounds(250, 600, 75, 35);
		unregister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog(null,"삭제할 ID 입력","ID");
				//DB에 아이디가 있는지 검사.
				if(du.findID(id)) {
					//ID가존재한다면 비밀번호 입력
					JPasswordField unpw = new JPasswordField();
					int option = JOptionPane.showOptionDialog(null, unpw, "Password Enter", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, null, null);
					
					//확인버튼 눌렀을때의 처리
					if(option == JOptionPane.OK_OPTION) {
						char[] unPwCh = unpw.getPassword();
						String password = new String(unPwCh);
						
						JPasswordField unpw2 = new JPasswordField();
						int option2 = JOptionPane.showOptionDialog(null, unpw2, "Password Enter", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, null, null);
						//확인버튼 눌렀을때의 처리
						if(option2 == JOptionPane.OK_OPTION) {
							char[] unPwCh2 = unpw2.getPassword();
							String password2 = new String(unPwCh2);
							
							//패스워드가 일치하는지 확인.
							if(password.equals(password2)) {
								
								JOptionPane.showMessageDialog(null, "회원탈퇴가 완료되었습니다.");
								//패스워드가 일치한다면 삭제
								du.userDrop(id, password);
							}else {
								//패스워드가 일치하지않는다면 ? 
								JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
							}
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "존재하는 ID가 없습니다.");
				}
			}
		});
		
		return unregister;
	}

}
