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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Map<String,List<String[]>> memoMap = new HashMap<>();
    
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
    
    
    public DiaryFinal() {
        super("캘린더"); 
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
	                    	
	                    	Deposits dep = new Deposits(year, month, clickedDay);
	                    	
	                    	retrieveDepositsData(dep);

	                    	
	
	                    } else if (rb2.isSelected()) {
	                        //JOptionPane.showMessageDialog(DiaryFinal.this, "출금창");
	                        //위와 동일하지만 출금창으로
	                    	
	                    	Expenses exp = new Expenses(year, month, clickedDay);
	                    	
	                    	retrieveExpensesData(exp);

	                    	
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
		JMenu jmu1 = new JMenu("파일");
		JMenu jmu2 = new JMenu("편집");
		JMenu jmu3 = new JMenu("보기");
		
		JMenuItem jmi1 = new JMenuItem("새로 만들기");
		JMenuItem jmi2 = new JMenuItem("열기");
		JMenuItem jmi3 = new JMenuItem("저장");
		
		jmu1.add(jmi1);
		jmu1.add(jmi2);
		jmu1.add(jmi3);
		
		JMenuItem jmi4 = new JMenuItem("잘라내기");
		JMenuItem jmi5 = new JMenuItem("복사");
		JMenuItem jmi6 = new JMenuItem("붙여넣기");
		
		jmu2.add(jmi4);
		jmu2.add(jmi5);
		jmu2.add(jmi6);
		
		jmb.add(jmu1);
		jmb.add(jmu2);
		jmb.add(jmu3);
		
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
    	int year = getYearByDateId(dateId);
    	int month = getMonthByDateId(dateId);
    	int day = getDayByDateId(dateId);
    	String dateString = String.format("%d년 %d월 %d일", year, month, day);
    	String dataString = "";
    	List<String[]> dataList = memoMap.get(dateId);
    	
    	if(dataList != null) {
    	    for (String[] transactions : dataList) {
    	        // Iterate through each String[] array
    	        for (int i=0; i<transactions.length; i++) {
    	        	if(i!=2) {
    	        		dataString = dataString + transactions[i] + "\t\t";
    	        	}else {
    	        		dataString = dataString + transactions[i];
    	        	}
    	        }
    	    }
    	}
    	

    	
        memoPane.removeAll(); 
        JLabel titleLabel = new JLabel(dateString);
        JLabel dataLabel = new JLabel(dataString);
        dataLabel.setHorizontalAlignment(SwingConstants.LEFT);
        memoPane.add(titleLabel, BorderLayout.NORTH);
        memoPane.add(dataLabel, BorderLayout.CENTER);
        memoPane.revalidate();
        titleLabel.setFont(fnt_regular);
        dataLabel.setFont(fnt_regular);
    }

    //my패널에 원래있던 글자 삭제하고 다시 띄우기
    public void MyPaneltxt() {
    	memoPane.removeAll(); 
    	memoPane.revalidate();
    }
    
    public String getDateIdByDate(int year, int month, int day) {
		String yearData = String.valueOf(year);
		String monthData = (month < 10 ? "0" : "") + month;
		String dayData = (day < 10 ? "0" : "") + day;
		String dateId = yearData + monthData + dayData;
		return dateId;
    }
    
    public int getYearByDateId(String dateId) {
    	int year = Integer.parseInt(dateId.substring(0,4));
    	return year;
    }
    
    public int getMonthByDateId(String dateId) {
    	int month = Integer.parseInt(dateId.substring(4,6));
    	return month;
    }
    
    public int getDayByDateId(String dateId) {
    	int day = Integer.parseInt(dateId.substring(6,8));
    	return day;
    }
    
    public void retrieveDepositsData(Deposits dep) {
        String[] depositData = dep.getDepositData();
        String dateId = dep.getDateId();
        
        if (depositData != null) {
    		System.out.println("inside DiaryFinal Class");
        	System.out.println(dateId);
        	for(String data : depositData) {
				System.out.println(data);
			}
            // Update data in DiaryFinal or relevant data structures
            // For example, update memoMap or perform necessary actions
            if (!memoMap.containsKey(dateId)) {
                List<String[]> dataList = new ArrayList<>();
                dataList.add(depositData);
                memoMap.put(dateId, dataList);
            } else {
                List<String[]> dataList = memoMap.get(dateId);
                dataList.add(depositData);
                memoMap.put(dateId, dataList);
            }
        }
        // Update UI or perform other necessary actions based on retrieved data
        MyTransactions(dateId);
    }

    public void retrieveExpensesData(Expenses exp) {
        String[] expenseData = exp.getExpenseData();
        String dateId = exp.getDateId();
    	
    	if(expenseData != null) {
    		System.out.println("inside DiaryFinal Class");
        	System.out.println(dateId);
        	for(String data : expenseData) {
				System.out.println(data);
			}
        	if(!memoMap.containsKey(dateId)) {
        		List<String[]> dataList = new ArrayList<>();
        		dataList.add(expenseData);
        		memoMap.put(dateId, dataList);
        	}else {
        		List<String[]> dataList = memoMap.get(dateId);
        		dataList.add(expenseData);
        		memoMap.put(dateId, dataList);
        	}
    	}
        MyTransactions(dateId);
    }
}

