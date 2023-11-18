package a_login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.jdbc.Driver;

public class DBuser {
	Connection cnn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	String dbUrl = "jdbc:mysql://localhost:3306/account";
	String dbUser = "root";
	String dbPassword = "!@Arhwe2";
	
	
	//회원 가입 로직
		public void join_membership(String ID, String PW, String Name) {
			try {
				cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				String sql = "insert into account_book values(?,?,?)";
				stmt = cnn.prepareStatement(sql);
				stmt.setString(1, ID);
				stmt.setString(2, PW);
				stmt.setString(3, Name);
				int res = stmt.executeUpdate();
				if(res==1) {
					System.out.println("데이터삽입성공");
				}
			} catch (Exception e) {
				System.out.println("오류"+e);
			}finally {
				try {
					if(stmt != null) stmt.close();
		            if(cnn != null) cnn.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			
		}
		
		//아이디 중복검사 & 아이디 존재 여부
		public boolean findID(String ID) {
			boolean overlap = false;
			try {
				
				cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				String sql = "select count(*) from account_book where ID = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setString(1, ID);
				rs = stmt.executeQuery();
				if(rs.next() && rs.getInt(1) > 0) {
					overlap = true;
				}else {
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if(stmt != null) stmt.close();
		            if(cnn != null) cnn.close();
		            if(rs != null) rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			return overlap;
		}
		
		//회원탈퇴
		public void userDrop(String ID,String PW) {
			try {
				cnn = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
				String sql = "delete from account_book where ID = ? and PW = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setString(1, ID);
				stmt.setString(2, PW);
				int rs = stmt.executeUpdate();
				if(rs >0) {
					System.out.println("삭제성공");
				}else {
					System.out.println("ID또는 비밀번호가 일치하지않습니다.");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if(stmt != null) stmt.close();
		            if(cnn != null) cnn.close();
		            if(rs != null) rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		//로그인
		public void userLogin(String ID, String PW) {
			try {
				cnn = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
				String sql = "select * from account_book where ID = ? and PW = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setString(1, ID);
				stmt.setString(2, PW);
				rs = stmt.executeQuery();
				if(rs.next()) {
					System.out.println("로그인성공");
				}else {
					System.out.println("일치하는 아이디가 없습니다.");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if(stmt != null) stmt.close();
		            if(cnn != null) cnn.close();
		            if(rs != null) rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		public boolean findPW(String PW) {
			boolean overlap = false;
			try {
				
				cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				String sql = "select count(*) from account_book where PW = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setString(1, PW);
				rs = stmt.executeQuery();
				if(rs.next() && rs.getInt(1) > 0) {
					overlap = true;
				}else {
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					if(stmt != null) stmt.close();
		            if(cnn != null) cnn.close();
		            if(rs != null) rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			return overlap;
		}
		
		public void insertCash(int cash, int account1,String ID) {
			boolean ap = false;
			try {
				cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				String sql = "insert into accounts (cash,account1,ID) value(?,?,?)";
				stmt = cnn.prepareStatement(sql);
				stmt.setInt(1, cash);
				stmt.setInt(2, account1);
				stmt.setString(3, ID);
				int rs = stmt.executeUpdate();
				if(rs == 1) {
					System.out.println("데이터 삽입 성공");
				}else {
					System.out.println("실패");
				}
			} catch (Exception e) {
				System.out.println("오류"+e);
			}
			
		
		}
		
		
		

}
