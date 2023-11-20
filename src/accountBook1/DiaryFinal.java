package accountBook1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import a_loginFinal.DB;
import a_loginFinal.Login;
import a_loginFinal.MyProfile;
import a_loginFinal.SessionManager;

//  ★ 색감을 로그인창이랑 맞추고싶긴한데...의견 주세요

public class DiaryFinal extends JFrame implements ItemListener, ActionListener{
    Font fnt = new Font("SansSerif", Font.BOLD, 20); //★글씨체 통일감이 좋겠죠?
    Font fnt_regular = new Font("SansSerif", Font.PLAIN, 14);
    // 상단 년,월 달력옮기기 부분
    JPanel selectPane = new JPanel(); 
    JButton prevBtn = new JButton("◀");
    JButton nextBtn = new JButton("▶");
    JComboBox<Integer> yearCombo = new JComboBox<Integer>(); 
    JComboBox<Integer> monthCombo = new JComboBox<Integer>(); 
    JLabel yearLBl = new JLabel("년");
    JLabel monthLBl = new JLabel("월");

    // 날짜,요일부분 관련 
    JPanel centerPane = new JPanel(new BorderLayout()); 
    JPanel titlePane = new JPanel(new GridLayout(1, 7));
    String[] title = {"일", "월", "화", "수", "목", "금", "토"};
    JPanel dayPane = new JPanel(new GridLayout(0, 7)); 
    
    //상세내역 메모부분 관련
    JPanel memoPane = new JPanel(new BorderLayout());
    JLabel detailMemoLBl = new JLabel();
    
    //메뉴바
    JMenuBar jmb = new JMenuBar();
    
    //달력 관련
    Calendar calendar;
    int year;
    int month;

    //날짜 클릭 시 팝업 관련
    JPanel dialPane = new JPanel();
    JRadioButton rb1 = new JRadioButton("입금");
    JRadioButton rb2 = new JRadioButton("출금");
    ButtonGroup rbGroup = new ButtonGroup();//그래야 둘중 하나만 선택?일듯? 라벨add는 setDay메서드에서 
    
    DB db = new DB();
    SessionManager sm;
    
    public DiaryFinal(SessionManager sm) {
        super("캘린더");
        this.sm = sm;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR); 
        month = calendar.get(Calendar.MONTH)+1; //month 0부터 시작

        //상단 위치배정이랑 디자인부분
        selectPane.setBackground(new Color(255, 193, 204));  // ★폰트, 컬러는 디자인부분이니 나중에 같이 상의해주세욥
        selectPane.add(prevBtn); prevBtn.setFont(fnt);
        selectPane.add(yearCombo); yearCombo.setFont(fnt);
        selectPane.add(yearLBl); yearLBl.setFont(fnt);
        selectPane.add(monthCombo); monthCombo.setFont(fnt);
        selectPane.add(monthLBl); monthLBl.setFont(fnt);
        selectPane.add(nextBtn); nextBtn.setFont(fnt);  

        add(BorderLayout.NORTH, selectPane); 

        //현재 년, 월 세팅
        setYear();
        setMonth();

        //title요일 호출
        setCalendarTitle();     // 월~일보여지는 메서드
        centerPane.add(BorderLayout.NORTH, titlePane);
        add(centerPane);

        //날짜만들기
        centerPane.add(dayPane); 
        setDay();   

        //메모구역만들기
        memoPane.setBorder(new TitledBorder(new LineBorder(Color.black,3),"<상세내역>"));
        memoPane.add(detailMemoLBl);
        add(memoPane, BorderLayout.SOUTH);
        memoPane.setPreferredSize(new Dimension(600, 150));
        //detailMemo();
        
        // 상단메뉴바 세팅
        setMenuBar();
        
        
        
        //---------------------------기능이벤트를 추가-------------------------------
        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        //년월 이벤트 다시등록
        yearCombo.addItemListener(this);
        monthCombo.addItemListener(this);

        //JFrame의 설정들
        setSize(600,700); // ★로그인 창 바운즈 알려주세요 맞출게요
        setLocationRelativeTo(null); // 윈도우화면에 가운데 정렬 팝업
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //  ★클로즈 되는건 조장이 정해주세요 어느시점에서 클로즈 될런지
    
    }
    
    //날짜셋팅
    public void setDay() {
        //요일
        calendar.set(year, month-1, 1); 
        int week = calendar.get(Calendar.DAY_OF_WEEK); 
        //마지막날
        int lastDay = calendar.getActualMaximum(Calendar.DATE); 
        
        //시작일을 맞추기 위한 빈공간   
        for(int s=1; s<week; s++) { 
            JLabel lbl = new JLabel(""); 
            dayPane.add(lbl);
        }
        //날짜추가
        for(int day=1; day<=lastDay; day++) {
        	
        	String calDateId = getDateIdByDate(year, month, day);
        	
        	JLabel lbl = new JLabel(String.valueOf(day)); 
        	lbl.setVerticalAlignment(SwingConstants.TOP);
        	lbl.setHorizontalAlignment(SwingConstants.LEFT);
         
            int finalDay = day;
            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    calendar.set(Calendar.DATE, finalDay); 
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; 
                    int clickedDay = calendar.get(Calendar.DAY_OF_MONTH);
                    
                    rbGroup.add(rb1); rbGroup.add(rb2);
                    dialPane.add(rb1); dialPane.add(rb2);
                    
                    // 입출금 선택창이랑 선택 후 창 부분
                   int result = JOptionPane.showOptionDialog(
                		   DiaryFinal.this, // 프레임
                		   dialPane,
                		   "입출금",  //제목
                		   JOptionPane.OK_CANCEL_OPTION, //버튼종류
                		   JOptionPane.PLAIN_MESSAGE, 
                		   null, //기본아이콘
                		   null, //버튼커스텀아이콘
                		   null);//기본값(null인 경우 첫 번째 버튼이 기본값)
             
                   //여기는 조장이..!!!!
	                if (result == JOptionPane.OK_OPTION) {
	                    if (rb1.isSelected()) {
	                        //JOptionPane.showMessageDialog(DiaryFinal.this, clickedDay);
	                        //입금창 메서드나 클래스 호출 후
	                        //여기서 라벨추가로 입출금액 보여지도록 입출창에서 저장되는 변수 불러와서 스트링으로 변환해서 저장
	                        //만약 라벨1이 입금액을 저장클릭(셀렉티드) 했다면 라벨1에 "+"+입금액
	                    	
	                    	Deposits dep = new Deposits(year, month, clickedDay, sm);


	                    	
	
	                    } else if (rb2.isSelected()) {
	                        //JOptionPane.showMessageDialog(DiaryFinal.this, "출금창");
	                        //위와 동일하지만 출금창으로
	                    	
	                    	Expenses exp = new Expenses(year, month, clickedDay, sm);


	                    	
	                    }
	                }
                
                } 
                @Override
                public void mouseEntered(MouseEvent e) {
                	super.mouseEntered(e);
                    lbl.setBackground(new Color(255, 193, 204));
                    MyTransactions(calDateId);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	super.mouseExited(e);
                	lbl.setBackground(null);
                    MyPaneltxt();
                }
            });
            //출력하는 날짜에 대한 요일
            calendar.set(Calendar.DATE, day); 
            int w = calendar.get(Calendar.DAY_OF_WEEK); 
            if (w == 1) lbl.setForeground(Color.red); 
            if (w == 7) lbl.setForeground(Color.blue); 
            lbl.setOpaque(true);
            dayPane.add(lbl);
        }
    }
    //월화수목금토일 설정
    public void setCalendarTitle() { 
        for(int i =0; i <title.length; i++) { 
            JLabel lbl = new JLabel(title[i], JLabel.CENTER); 
            lbl.setFont(fnt); 
            if(i ==0) lbl.setForeground(Color.red); 
            if(i ==6) lbl.setForeground(Color.blue);
            titlePane.add(lbl); 
        }
    }
    //년도세팅
    public void setYear() {
        for(int i= year-50; i<year+20; i++) { 
            yearCombo.addItem(i); 
        }
        yearCombo.setSelectedItem(year); 
    }
    //월세팅
    public void setMonth() {
        for(int i=1; i<=12; i++) {
            monthCombo.addItem(i);
        }
        monthCombo.setSelectedItem(month); 
    }
    
    public void setMenuBar() {
		JMenu jmu1 = new JMenu("옵션");
		
		JMenuItem jmi1 = new JMenuItem("내 프로필");
	    jmi1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	DiaryFinal.super.dispose();
	            MyProfile profileFrame = new MyProfile(sm);
	        }
	    });
		
		JMenuItem jmi2 = new JMenuItem("로그아웃");
	    jmi2.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int option = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
	            if (option == JOptionPane.YES_OPTION) {
	                sm.logout(); // SessionManager을 사용하여 현재 세션 로그아웃
	                DiaryFinal.super.dispose();
	                Login loginMenu = new Login();
	            }
	        }
	    });
		
		jmu1.add(jmi1);
		jmu1.add(jmi2);
		
		jmb.add(jmu1);
		
		setJMenuBar(jmb);
    }
    
    
    
    
    
    
    
    //콤보박스클릭이벤트
    public void itemStateChanged(ItemEvent e) { //콤보박스를 변경하였을때에 선택되는 이벤트
        year = (int)yearCombo.getSelectedItem(); 
        month = (int)monthCombo.getSelectedItem();

        dayPane.setVisible(false); 
        dayPane.removeAll(); //원래있는 날짜 지우기
        setDay(); 
        dayPane.setVisible(true); 


    }
    //버튼이벤트
    public void actionPerformed(ActionEvent ae) {  //액션이벤트(버튼이벤트)
        Object obj = ae.getSource(); 
        if(obj == prevBtn) {//이전버튼을 눌렀을때
            //이전월을 눌렀을때
            prevMonth(); //이전버튼메소드호출
            setDayReset(); 
            }else if(obj == nextBtn) { //이후 버튼을 눌렀을때
            //다음월을 눌렀을떄
            nextMonth(); 
            setDayReset(); 
        }else if(obj == dayPane) {
            JOptionPane.showInputDialog("현재 년월일");
        }
    }
    private void setDayReset() {
        //년월 이벤트 등록해제
        yearCombo.removeItemListener(this); //등록이벤트를 해제시켜주고
        monthCombo.removeItemListener(this);

        yearCombo.setSelectedItem(year); 
        monthCombo.setSelectedItem(month);

        dayPane.setVisible(false); 
        dayPane.removeAll(); 
        setDay(); 
        dayPane.setVisible(true); 

        yearCombo.addItemListener(this); 
        monthCombo.addItemListener(this); 

    }
    public void prevMonth() { //월
        if(month==1) { //21.01월 일때에 12월로 떨어지면서 year를 전년도로 바꿈
            year--;
            month=12;
        }else { //그외의 경우
            month--;
        }
    }
    public void nextMonth() {
        if(month==12){ //12월일때에는 년도를 추가시키고 월을 1로 바꿈
            year++;
            month=1;
        }else{ //그외의 경우
            month++;
        }
    }
    
    //my패널에 입출금 글자 띄우기
    public void MyTransactions(String dateId) {
        int year = getYearByDateId(dateId); // dateId에서 연도 추출
        int month = getMonthByDateId(dateId); // dateId에서 월 추출
        int day = getDayByDateId(dateId); // dateId에서 일 추출
        String dateString = String.format("%d년 %d월 %d일", year, month, day); // 날짜 문자열 포맷팅

        // 지정된 dateId에 대한 입금 및 지출 데이터 가져오기
        List<String[]> depositData = db.getDepositDataByDateId(sm.getID(), dateId);
        List<String[]> expenseData = db.getExpenseDataByDateId(sm.getID(), dateId);

        // 입금 및 지출 데이터를 단일 리스트로 결합
        List<String[]> dataList = new ArrayList<>();
        dataList.addAll(depositData);
        dataList.addAll(expenseData);

        // 정렬된 dataList을 반복하여 dataHTML을 구성
        String dataHTML = "<html><body>";
        for (String[] record : dataList) {
            // 각 레코드를 HTML 형식으로 포맷팅
            String depositRecord = String.format("%s&nbsp;&nbsp;&nbsp;&nbsp;%s&nbsp;&nbsp;&nbsp;&nbsp;%s&nbsp;&nbsp;&nbsp;&nbsp;%s<br>", record[0], record[1], record[2], record[3]);
            dataHTML += depositRecord;
        }
        dataHTML += "</body></html>";

        memoPane.removeAll(); // 기존 컴포넌트 제거

        JLabel titleLabel = new JLabel(dateString); // 포맷된 날짜로 라벨 생성
        JLabel dataLabel = new JLabel(dataHTML); // HTML 형식의 데이터로 라벨 생성

        // 라벨 정렬 설정
        dataLabel.setVerticalAlignment(SwingConstants.TOP);
        dataLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // 라벨을 memoPane에 추가
        memoPane.add(titleLabel, BorderLayout.NORTH);
        memoPane.add(dataLabel, BorderLayout.CENTER);

        memoPane.revalidate(); // memoPane 업데이트를 위해 재검증

        titleLabel.setFont(fnt_regular); // 제목 라벨에 폰트 설정
        dataLabel.setFont(fnt_regular); // 데이터 라벨에 폰트 설정
    }
    
    //my패널에 원래있던 글자 삭제하고 다시 띄우기
    public void MyPaneltxt() {
    	memoPane.removeAll(); 
    	memoPane.revalidate();
    }
    
 // 날짜를 입력받아 dateId를 반환하는 메소드
    public String getDateIdByDate(int year, int month, int day) {
        // 연도, 월, 일을 문자열로 변환하여 형식에 맞게 조합
        String yearData = String.valueOf(year);
        // 월과 일이 10 미만이면 앞에 "0"을 붙여서 01 ~ 09로 나타냄
        String monthData = (month < 10 ? "0" : "") + month;	
        String dayData = (day < 10 ? "0" : "") + day;

        // 조합된 데이터를 dateId로 반환
        String dateId = yearData + monthData + dayData;
        return dateId;
    }

    // dateId에서 연도를 가져오는 메소드
    public int getYearByDateId(String dateId) {
        // dateId의 앞부분에서 연도를 추출하여 반환
        int year = Integer.parseInt(dateId.substring(0, 4));
        return year;
    }

    // dateId에서 월을 가져오는 메소드
    public int getMonthByDateId(String dateId) {
        // dateId의 중간 부분에서 월을 추출하여 반환
        int month = Integer.parseInt(dateId.substring(4, 6));
        return month;
    }

    // dateId에서 일을 가져오는 메소드
    public int getDayByDateId(String dateId) {
        // dateId의 마지막 부분에서 일을 추출하여 반환
        int day = Integer.parseInt(dateId.substring(6, 8));
        return day;
    }

}

