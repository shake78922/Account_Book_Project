package a_loginFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import accountBook1.PaymentTypeConverter;

public class DB {
	private Connection cnn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private String dbUrl = "jdbc:mysql://localhost:3306/account";
	private String dbUser = "root";
	private String dbPassword = "1234";

	// Add other database-related methods such as user retrieval, insertion,
	// deletion, etc.

	public void insertUser(String ID, String PW, String Name) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "insert into account_book values(?,?,?)";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, ID);
			stmt.setString(2, PW);
			stmt.setString(3, Name);
			int res = stmt.executeUpdate();
			if (res == 1) {
				System.out.println("데이터삽입성공");
			}
		} catch (Exception e) {
			System.out.println("오류" + e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (cnn != null)
					cnn.close();

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
			if (rs.next() && rs.getInt(1) > 0) {
				overlap = true;
			} else {

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

	public void insertDeposit(String userId, String depositDate, int depositAmount, String depositType,
			String paymentType, String description) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "INSERT INTO deposits (user_id, deposit_date, deposit_amount, deposit_type, payment_type, description) VALUES (?, ?, ?, ?, ?, ?)";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, depositDate);
			stmt.setInt(3, depositAmount);
			stmt.setString(4, depositType);
			stmt.setString(5, paymentType);
			stmt.setString(6, description);
			int res = stmt.executeUpdate();
			if (res == 1) {
				JOptionPane.showMessageDialog(null, "입금 내역이 입력되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public void insertExpense(String userId, String expenseDate, int expenseAmount, String expenseType,
			String paymentType, String description) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "INSERT INTO expenses (user_id, expense_date, expense_amount, expense_type, payment_type, description) VALUES (?, ?, ?, ?, ?, ?)";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, expenseDate);
			stmt.setInt(3, expenseAmount);
			stmt.setString(4, expenseType);
			stmt.setString(5, paymentType);
			stmt.setString(6, description);
			int res = stmt.executeUpdate();
			if (res == 1) {
				JOptionPane.showMessageDialog(null, "지출 내역이 입력되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public List<String> getPaymentTypesForUser(String userId) {
		List<String> paymentTypes = new ArrayList<>();

		Map<String, String> colNameMap = new HashMap<>();
		colNameMap.put("cash", "현금");
		colNameMap.put("account1", "계좌1");
		colNameMap.put("account2", "계좌2");
		colNameMap.put("account3", "계좌3");

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "SELECT * FROM accounts WHERE ID = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int colCnt = metaData.getColumnCount();
				for (int i = 1; i <= colCnt; i++) {
					String colName = metaData.getColumnName(i);
					if (!colName.equalsIgnoreCase("ID") && rs.getObject(colName) != null) {
						String korColName = colNameMap.getOrDefault(colName, colName);
						paymentTypes.add(korColName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return paymentTypes;
	}

	public Map<String, Integer> getAccountBalances(String userId) {
		Map<String, Integer> accountBalances = new HashMap<>();

		Map<String, String> colNameMap = new HashMap<>();
		colNameMap.put("cash", "현금");
		colNameMap.put("account1", "계좌1");
		colNameMap.put("account2", "계좌2");
		colNameMap.put("account3", "계좌3");

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "SELECT * FROM accounts WHERE ID = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int colCnt = metaData.getColumnCount();
				for (int i = 1; i <= colCnt; i++) {
					String colName = metaData.getColumnName(i);
					if (!colName.equalsIgnoreCase("ID")) {
						String korColName = colNameMap.getOrDefault(colName, colName);
						Integer balance = rs.getInt(colName);
						if (!rs.wasNull()) {
							accountBalances.put(korColName, balance); // Add to map if the balance is not null
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return accountBalances;
	}

	private String dateIdToSQLDate(String dateId) {
		String year = dateId.substring(0, 4);
		String month = dateId.substring(4, 6);
		String day = dateId.substring(6, 8);
		return String.format("%s-%s-%s", year, month, day);
	}

//    private String sqlDateToDateId(String sqlDate) {
//        // Extract year, month, and day from the SQL date
//        String[] parts = sqlDate.split("-");
//        String year = parts[0];
//        String month = parts[1];
//        String day = parts[2];
//
//        // Combine the year, month, and day into the "dateId" format (yyyyMMdd)
//        return year + month + day;
//    }

	public List<String[]> getDepositDataByDateId(String userId, String dateId) {
		List<String[]> depositDataList = new ArrayList<>();
		PaymentTypeConverter converter = new PaymentTypeConverter();

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "SELECT deposit_amount, deposit_type, payment_type, description FROM deposits WHERE user_id = ? AND deposit_date = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, dateIdToSQLDate(dateId)); // Convert the dateId to SQL date format
			rs = stmt.executeQuery();

			while (rs.next()) {
				String depositAmount = "+" + String.valueOf(rs.getInt("deposit_amount"));
				String depositType = rs.getString("deposit_type");
				String paymentType = converter.convertEngToKorPayType(rs.getString("payment_type"));
				String description = rs.getString("description");

				// Create an array to store each deposit record's data
				String[] depositRecord = { depositAmount, depositType, paymentType, description };
				depositDataList.add(depositRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return depositDataList;
	}

	public List<String[]> getExpenseDataByDateId(String userId, String dateId) {
		List<String[]> expenseDataList = new ArrayList<>();

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "SELECT expense_amount, expense_type, payment_type, description FROM expenses WHERE user_id = ? AND expense_date = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, dateIdToSQLDate(dateId)); // Convert the dateId to SQL date format
			rs = stmt.executeQuery();

			while (rs.next()) {
				String expenseAmount = "-" + String.valueOf(rs.getInt("expense_amount"));
				String expenseType = rs.getString("expense_type");
				String paymentType = rs.getString("payment_type");
				String description = rs.getString("description");

				// Create an array to store each expense record's data
				String[] expenseRecord = { expenseAmount, expenseType, paymentType, description };
				expenseDataList.add(expenseRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return expenseDataList;
	}

	public void updateAccountBalancesForDeposits(String userId, String[] depositData) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String paymentType = depositData[2];
			int depositAmount = Integer.parseInt(depositData[0]);

			String sql = "UPDATE accounts SET ";
			String columnToUpdate = ""; // To store the column name to be updated

			// Use English column names to update the specific column
			if ("cash".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "cash";
			} else if ("account1".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account1";
			} else if ("account2".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account2";
			} else if ("account3".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account3";
			}

			// Construct the SQL query dynamically based on the determined column
			if (!columnToUpdate.isEmpty()) {
				sql += columnToUpdate + " = " + columnToUpdate + " + ? WHERE ID = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setInt(1, depositAmount);
				stmt.setString(2, userId);

				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Account balances updated successfully.");
				} else {
					System.out.println("Failed to update account balances.");
				}
			} else {
				System.out.println("Payment type not recognized.");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	public void updateAccountBalancesForExpenses(String userId, String[] expenseData) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String paymentType = expenseData[2];
			int expenseAmount = Integer.parseInt(expenseData[0]);

			String sql = "UPDATE accounts SET ";
			String columnToUpdate = ""; // To store the column name to be updated

			// Use English column names to update the specific column
			if ("cash".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "cash";
			} else if ("account1".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account1";
			} else if ("account2".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account2";
			} else if ("account3".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account3";
			}

			// Construct the SQL query dynamically based on the determined column
			if (!columnToUpdate.isEmpty()) {
				sql += columnToUpdate + " = " + columnToUpdate + " - ? WHERE ID = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setInt(1, expenseAmount);
				stmt.setString(2, userId);

				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Account balances updated successfully for expenses.");
				} else {
					System.out.println("Failed to update account balances for expenses.");
				}
			} else {
				System.out.println("Payment type not recognized for expenses.");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	}

	private void closeResources() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (cnn != null)
				cnn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
