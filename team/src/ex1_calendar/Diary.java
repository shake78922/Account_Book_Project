package ex1_calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Diary extends JFrame{
	String[] dayAr = {"일", "월", "화", "수", "목", "금", "토"};
	DateBox[] dateBoxAr = new DateBox[dayAr.length*5];
	JPanel dateBoxAr1 = new JPanel();
	JButton jButton;
	JPanel pNorth;
	JButton btBack;
	JLabel lbTitle;
	JButton btNext;
	JPanel pCenter; //날짜 박스
	JPanel pSouth;//입출금내역,My버튼 구역
	JTextField breakdown;//입출금내역 텍스트필드
	JButton my;//마이버튼
	JLabel label;
	Calendar cal; //날짜 객체
	
	int yy; //기준점이 되는 년도
	int mm; //기준점이 되는 월
	int startDay; //월의 시작 요일
	int lastDate; //월의 마지막 날
	
	
	public Diary() {
		//디자인
		pNorth = new JPanel();
		btBack = new JButton("◀");
		lbTitle = new JLabel("년 월", SwingConstants.CENTER);
		btNext = new JButton("▶");
		pCenter = new JPanel();
		pSouth = new JPanel();
		my = new JButton("My");

		
		//라벨에 폰트 설정
		lbTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
		lbTitle.setPreferredSize(new Dimension(300, 35));
		
		pNorth.add(btBack);
		pNorth.add(lbTitle);
		pNorth.add(btNext);
		pSouth.add(my,BorderLayout.EAST);
		add(pNorth, BorderLayout.NORTH);
        add(pCenter);
		add(pSouth,BorderLayout.SOUTH);
		
		//이전 버튼을 눌렀을 때 전 월로 이동해야함
		btBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMonth(-1);
			}
		});
		
		//다음 버튼을 눌렀을 때 다음 달로 이동해야함
		btNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMonth(1);
			}
		});
		jButton = new JButton();
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createDate();
			}
		});
		
		my.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showMy();
			}
		});
		
		getCurrentDate(); 
		getDateInfo(); 
		setDateTitle(); 
		createDay(); 
		createDate();
		printDate(); 
		showMy();
		
		setTitle("캘린더");
		setVisible(true);
		setBounds(400, 0, 780, 1000);
	}
	
	//현재날짜 객체 만들기
	public void getCurrentDate() {
		cal = Calendar.getInstance();
	}
	
	public void getDateInfo() {
		yy = cal.get(Calendar.YEAR);
		mm = cal.get(Calendar.MONTH);
		startDay = getFirstDayOfMonth(yy, mm);
		lastDate = getLastDate(yy, mm);
	}
	

	
	//요일 생성
	public void createDay() {
		for(int i = 0; i < 7; i++){
			DateBox dayBox = new DateBox(dayAr[i], Color.pink, 100, 70);
			pCenter.add(dayBox);

		}
	}
	
	//날짜 생성
	
	public void createDate() {
		
		for(int i = 0; i < dayAr.length*5; i++) {
			DateBox dateBox = new DateBox("", Color.white, 100, 100);
			jButton = new JButton();
			dateBox.add(jButton);
			pCenter.add(dateBox);
			dateBoxAr[i] = dateBox;
			dateBox.setBounds(0, 100, 500, 350);
			} 
		}
	
	
	//해당 월의 시작 요일 구하기
	public int getFirstDayOfMonth(int yy, int mm) {
		Calendar cal = Calendar.getInstance(); 
		cal.set(yy, mm, 1);
		return cal.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	public int getLastDate(int yy, int mm) {
		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm+1, 0);
		return cal.get(Calendar.DATE);
	}
	
	//날짜 박스에 날짜 출력하기
	public void printDate() {
		
		int n = 1;
		for(int i = 0; i < dateBoxAr.length; i++) {
			
			if(i >= startDay && n <= lastDate) {
				dateBoxAr[i].day = Integer.toString(n);
				dateBoxAr[i].repaint();

				n++;
			}else {
				dateBoxAr[i].day = "";
				dateBoxAr[i].repaint();
				
			}
		}
	}
	
	//달력을 넘기거나 전으로 이동할 때 날짜 객체에 대한 정보도 변경
	public void updateMonth(int data) {
		//캘린더 객체에 들어있는 날짜를 기준으로 월 정보를 바꿔준다.
		cal.set(Calendar.MONTH, mm+data);
		getDateInfo();
		printDate();
		setDateTitle();
	}
	
	//몇년도 몇월인지를 보여주는 타이틀 라벨의 값을 변경
	public void setDateTitle() {
		lbTitle.setText(yy+"년 "+(mm+1)+"월");
		lbTitle.updateUI();
	}
	
	public void showMy() {
			DateBox db = new DateBox("My", Color.pink,100 , 100);
			DateBox db2 = new DateBox("", Color.white,600 , 300);
			pSouth.add(db2,BorderLayout.WEST);
			pSouth.add(db,BorderLayout.EAST);
			//my.set
			
		
		
//		JDialog dialog = new JDialog(this,"My",true);
//		dialog.setBounds(200,50,500,500);
//		
//		dialog.add(pSouth);
//		dialog.setVisible(true);
	}
	
	
	
	
}
