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

	// 회원 추가 기능
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
			closeResources();
		}
	}
	
	// 회원 존재 여부 확인 (인증)
	public boolean authenticateUser(String ID, String PW) {
		boolean authenticated = false;
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String sql = "select * from account_book where ID = ? and PW = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, ID);
			stmt.setString(2, PW);
			rs = stmt.executeQuery();
			authenticated = rs.next(); // 반환할 튜플이 존재한다면 true, 없다면 false
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return authenticated;
	}

	// 아이디 존재 여부
	// 중복확인 할 아이디를 매개변수로 받은 다음
	// 데이터베이스에 일치하는 아이디가 있다면 overlap = true 반환
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

	// 아이디를 매개변수로 받아 아이디와 연동된 회원 이름을 반환해주는 매서드
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

	// 사용자의 계좌들의 금액을 새로운 값으로 수정해주는 메서드
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

	// 입금 내역을 데이터베이스의 Deposits 테이블에 반영해주는 메서드
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

	// 지출 내역을 데이터베이스의 Expenses 테이블에 반영해주는 메서드
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

	// 사용자의 현재 사용 중인 계좌들의 영문 명을 한글 명으로 변환하는 메서드
	public List<String> getPaymentTypesForUser(String userId) {
		// 반환할 데이터를 담을 List<String> 객체를 생성합니다.
		List<String> paymentTypes = new ArrayList<>();

		// (영문 명(key), 한글 명(value)) 형태의 Map 객체를 생성하고, 영문과 한글 계좌명을 매핑합니다.
		Map<String, String> colNameMap = new HashMap<>();
		colNameMap.put("cash", "현금");
		colNameMap.put("account1", "계좌1");
		colNameMap.put("account2", "계좌2");
		colNameMap.put("account3", "계좌3");

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			// 사용자 ID와 해당 사용자의 계좌 정보를 담고 있는 accounts 테이블에서 해당 사용자의 계좌 정보를 검색합니다.
			// 사용 중인 계좌는 int로 금액이 기록되어 있고, 사용하지 않는 계좌는 null로 표시됩니다.
			String sql = "SELECT * FROM accounts WHERE ID = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				// ResultSetMetaData는 테이블의 컬럼에 대한 정보를 제공합니다.
				ResultSetMetaData metaData = rs.getMetaData();
				// 컬럼의 개수를 colCnt 변수에 저장합니다.
				int colCnt = metaData.getColumnCount();
				// 컬럼의 개수만큼 반복하면서
				for (int i = 1; i <= colCnt; i++) {
					// index에 해당하는 컬럼의 이름을 colName에 저장합니다.
					String colName = metaData.getColumnName(i);
					// 만약 컬럼이 "ID"가 아니고, 해당 컬럼이 null이 아닌 경우
					if (!colName.equalsIgnoreCase("ID") && rs.getObject(colName) != null) {
						// colNameMap에서 해당하는 한글 명을 가져와서 리스트에 추가합니다.
						String korColName = colNameMap.getOrDefault(colName, colName);
						paymentTypes.add(korColName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(); // 리소스 닫기
		}

		return paymentTypes; // 변환된 한글 명의 계좌 목록을 반환합니다.
	}

	// 사용자의 계좌 잔액을 가져오는 메서드
	public Map<String, Integer> getAccountBalances(String userId) {
		// 계좌 잔액을 저장할 Map<String, Integer> 객체를 생성합니다.
		Map<String, Integer> accountBalances = new HashMap<>();

		// (영문 명(key), 한글 명(value)) 형태의 Map 객체를 생성하고, 영문과 한글 계좌명을 매핑합니다.
		Map<String, String> colNameMap = new HashMap<>();
		colNameMap.put("cash", "현금");
		colNameMap.put("account1", "계좌1");
		colNameMap.put("account2", "계좌2");
		colNameMap.put("account3", "계좌3");

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			// 사용자 ID와 해당 사용자의 계좌 정보를 담고 있는 accounts 테이블에서 해당 사용자의 계좌 정보를 검색합니다.
			String sql = "SELECT * FROM accounts WHERE ID = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				// ResultSetMetaData는 테이블의 컬럼에 대한 정보를 제공합니다.
				ResultSetMetaData metaData = rs.getMetaData();
				// 컬럼의 개수를 colCnt 변수에 저장합니다.
				int colCnt = metaData.getColumnCount();
				// 컬럼의 개수만큼 반복하면서
				for (int i = 1; i <= colCnt; i++) {
					// index에 해당하는 컬럼의 이름을 colName에 저장합니다.
					String colName = metaData.getColumnName(i);
					// 만약 컬럼이 "ID"가 아니라면
					if (!colName.equalsIgnoreCase("ID")) {
						// colNameMap에서 해당하는 한글 명을 가져옵니다.
						String korColName = colNameMap.getOrDefault(colName, colName);
						// 컬럼의 잔액을 Integer 형태로 가져옵니다.
						Integer balance = rs.getInt(colName);
						// 잔액이 null이 아닌 경우 맵에 추가합니다.
						if (!rs.wasNull()) {
							accountBalances.put(korColName, balance);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(); // 리소스 닫기
		}

		return accountBalances; // 변환된 한글 명의 계좌 잔액을 가진 맵을 반환합니다.
	}

	// dateId(YYYYMMDD)를 SQL 날짜형식(YYYY-MM-DD)으로 변환하는 메서드
	private String dateIdToSQLDate(String dateId) {
		// dateId 에서 연도, 월, 일을 추출합니다.
		String year = dateId.substring(0, 4);
		String month = dateId.substring(4, 6);
		String day = dateId.substring(6, 8);
		// SQL 날짜형식으로 변환하여 반환합니다.
		return String.format("%s-%s-%s", year, month, day);
	}

	// userId와 dateId에 해당하는 입금 데이터를 가져오는 메서드
	public List<String[]> getDepositDataByDateId(String userId, String dateId) {
		// 입금 데이터를 담을 List<String[]> 객체를 생성합니다.
		List<String[]> depositDataList = new ArrayList<>();
		PaymentTypeConverter converter = new PaymentTypeConverter();

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			// deposits 테이블에서 userId와 dateId에 해당하는 입금 정보를 가져옵니다.
			String sql = "SELECT deposit_amount, deposit_type, payment_type, description FROM deposits WHERE user_id = ? AND deposit_date = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, dateIdToSQLDate(dateId)); // dateId를 SQL date 형식으로 변환
			rs = stmt.executeQuery();

			while (rs.next()) {
				// 각 입금 데이터를 가져와서 필요한 정보들을 변수에 저장합니다.
				String depositAmount = "+" + String.valueOf(rs.getInt("deposit_amount"));
				String depositType = rs.getString("deposit_type");
				// 각 입금 데이터의 영문 결제 유형을 한글로 변환합니다.
				String paymentType = converter.convertEngToKorPayType(rs.getString("payment_type"));
				String description = rs.getString("description");

				// 각 입금 기록의 데이터를 담을 배열을 생성합니다.
				String[] depositRecord = { depositAmount, depositType, paymentType, description };
				depositDataList.add(depositRecord); // 배열을 리스트에 추가합니다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(); // 리소스 닫기
		}

		return depositDataList; // 입금 데이터를 담고 있는 리스트를 반환합니다.
	}

	// userId와 dateId에 해당하는 지출 데이터를 가져오는 메서드
	public List<String[]> getExpenseDataByDateId(String userId, String dateId) {
		// 지출 데이터를 담을 List<String[]> 객체를 생성합니다.
		List<String[]> expenseDataList = new ArrayList<>();
		PaymentTypeConverter converter = new PaymentTypeConverter();

		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			// expenses 테이블에서 userId와 dateId에 해당하는 지출 정보를 가져옵니다.
			String sql = "SELECT expense_amount, expense_type, payment_type, description FROM expenses WHERE user_id = ? AND expense_date = ?";
			stmt = cnn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, dateIdToSQLDate(dateId)); // dateId를 SQL date 형식으로 변환
			rs = stmt.executeQuery();

			while (rs.next()) {
				// 각 지출 데이터를 가져와서 필요한 정보들을 변수에 저장합니다.
				String expenseAmount = "-" + String.valueOf(rs.getInt("expense_amount"));
				String expenseType = rs.getString("expense_type");
				String paymentType = converter.convertEngToKorPayType(rs.getString("payment_type"));
				String description = rs.getString("description");

				// 각 지출 기록의 데이터를 담을 배열을 생성합니다.
				String[] expenseRecord = { expenseAmount, expenseType, paymentType, description };
				expenseDataList.add(expenseRecord); // 배열을 리스트에 추가합니다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(); // 리소스 닫기
		}

		return expenseDataList; // 지출 데이터를 담고 있는 리스트를 반환합니다.
	}

	// 입금 데이터를 이용하여 사용자의 계좌 잔액을 업데이트하는 메서드
	public void updateAccountBalancesForDeposits(String userId, String[] depositData) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String paymentType = depositData[2];
			int depositAmount = Integer.parseInt(depositData[0]);

			String sql = "UPDATE accounts SET ";
			String columnToUpdate = ""; // 업데이트할 열 이름을 저장하는 변수

			// 영문 열 이름을 사용하여 특정 열을 업데이트합니다.
			if ("cash".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "cash";
			} else if ("account1".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account1";
			} else if ("account2".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account2";
			} else if ("account3".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account3";
			}

			// 결정된 열을 기반으로 SQL 쿼리를 동적으로 생성합니다.
			if (!columnToUpdate.isEmpty()) {
				sql += columnToUpdate + " = " + columnToUpdate + " + ? WHERE ID = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setInt(1, depositAmount);
				stmt.setString(2, userId);

				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("계좌 잔액이 성공적으로 업데이트되었습니다.");
				} else {
					System.out.println("계좌 잔액을 업데이트하지 못했습니다.");
				}
			} else {
				System.out.println("인식되지 않는 결제 유형입니다.");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		} finally {
			closeResources(); // 리소스 닫기
		}
	}

	// 지출 데이터를 이용하여 사용자의 계좌 잔액을 업데이트하는 메서드
	public void updateAccountBalancesForExpenses(String userId, String[] expenseData) {
		try {
			cnn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			String paymentType = expenseData[2];
			int expenseAmount = Integer.parseInt(expenseData[0]);

			String sql = "UPDATE accounts SET ";
			String columnToUpdate = ""; // 업데이트할 열 이름을 저장하는 변수

			// 영문 열 이름을 사용하여 특정 열을 업데이트합니다.
			if ("cash".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "cash";
			} else if ("account1".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account1";
			} else if ("account2".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account2";
			} else if ("account3".equalsIgnoreCase(paymentType)) {
				columnToUpdate = "account3";
			}

			// 결정된 열을 기반으로 SQL 쿼리를 동적으로 생성합니다.
			if (!columnToUpdate.isEmpty()) {
				sql += columnToUpdate + " = " + columnToUpdate + " - ? WHERE ID = ?";
				stmt = cnn.prepareStatement(sql);
				stmt.setInt(1, expenseAmount);
				stmt.setString(2, userId);

				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("지출에 대한 계좌 잔액이 성공적으로 업데이트되었습니다.");
				} else {
					System.out.println("지출에 대한 계좌 잔액을 업데이트하지 못했습니다.");
				}
			} else {
				System.out.println("지출에 대한 인식되지 않는 결제 유형입니다.");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
		} finally {
			closeResources(); // 리소스 닫기
		}
	}

	// 리소스를 닫는(private) 메서드
	private void closeResources() {
		try {
			// ResultSet(rs), Statement(stmt), Connection(cnn)이 null이 아닌 경우 닫습니다.
			if (rs != null)
				rs.close(); // ResultSet 닫기
			if (stmt != null)
				stmt.close(); // Statement 닫기
			if (cnn != null)
				cnn.close(); // Connection 닫기
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
