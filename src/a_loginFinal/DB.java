package a_loginFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    private Connection cnn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private String dbUrl = "jdbc:mysql://localhost:3306/account";
    private String dbUser = "root";
    private String dbPassword = "1234";

    // Add other database-related methods such as user retrieval, insertion, deletion, etc.

    public void insertUser(String ID, String PW, String Name) {
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
    
    public boolean authenticateUser(String ID, String PW) {
        boolean authenticated = false;
        try {
            cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "select * from account_book where ID = ? and PW = ?";
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, ID);
            stmt.setString(2, PW);
            rs = stmt.executeQuery();
            authenticated = rs.next(); // Check if the result set has a row
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return authenticated;
    }
    
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
		} catch (SQLException e) {
            e.printStackTrace();
		}finally {
			closeResources();
		}
		return overlap;
	}

    public String getNameByID(String ID) {
        String name = null;
        try {
            cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "SELECT Name FROM account_book WHERE ID = ?";
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return name;
    }
    
    public void updateMoney(int cash, int account1, String ID) {
        try {
            cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "UPDATE accounts SET cash = ?, account1 = ? WHERE ID = ?";
            stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, cash);
            stmt.setInt(2, account1);
            stmt.setString(3, ID);
            int rs = stmt.executeUpdate();
            if (rs == 1) {
                System.out.println("데이터 업데이트 성공");
            } else {
                System.out.println("실패");
            }
        } catch (Exception e) {
            System.out.println("오류: " + e);
        } finally {
            closeResources();
        }
    }
    
    public int getCashbyID(String ID) {
    	int cash = 0;
        try {
            cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "SELECT cash FROM accounts WHERE ID = ?";
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                cash = rs.getInt("cash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return cash;
    }
    
    public int getAccount1byID(String ID) {
    	int account1 = 0;
        try {
            cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "SELECT account1 FROM accounts WHERE ID = ?";
            stmt = cnn.prepareStatement(sql);
            stmt.setString(1, ID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                account1 = rs.getInt("account1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return account1;
    }

    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (cnn != null) cnn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
