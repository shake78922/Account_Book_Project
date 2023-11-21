package a_loginFinal;

public class SessionManager {
	private String currentID; // 현재 ID를 저장하는 변수
	private String currentName; // 현재 이름을 저장하는 변수
	// Singleton 인스턴스를 저장하는 변수
	private static SessionManager instance; 

	// 직접적인 인스턴스화를 방지하기 위한 private 생성자
	private SessionManager() {
	}

	// Singleton 인스턴스를 반환하는 메서드
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}
	
	// 현재 사용자를 설정하는 메서드
	public void setCurrentUser(String ID) {
		this.currentID = ID;
		DB db = new DB();
		// ID를 기반으로 데이터베이스에서 이름 가져오기
		this.currentName = db.getNameByID(ID); 
	}

	// 로그아웃하는 메서드
	public void logout() {
		// 현재 세션 정보를 지움
		currentID = null;
		currentName = null;
	}

	// 현재 ID를 반환하는 메서드
	public String getID() {
		return currentID;
	}

	// 현재 이름을 반환하는 메서드
	public String getName() {
		return currentName;
	}


}
