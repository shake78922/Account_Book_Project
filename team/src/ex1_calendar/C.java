package ex1_calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class C extends JFrame{
	public static void main(String[] args) {
		 String[] arr = {"0","1","2","3","4","5","6","7","8","9","10","11"};
		 JButton[] btn = new JButton[12];
		 JLabel txt;
		
		// TODO Auto-generated method stub
		System.out.println("[GUI - for문 사용해 동적으로 레이아웃 JButton 버튼 생성 및 ActionListener 클릭 이벤트 정의]");
		
		/*[설 명]
		 * 1. 자바에서는 JFrame을 사용해서 GUI 프로그램을 만들 수 있습니다
		 * 2. new GridLayout(행, 열, 가로여백, 세로여백) 표시 - 테이블 모양 형식 
		 * 3. ActionListener : 클릭 이벤트 정의 실시
		 * 4. for문 : 동적으로 배열에 포함된 값을 사용해 버튼을 생성합니다		 
		 * */
	
		
		//TODO 부모 프레임 생성
		JFrame frm = new JFrame("동적으로 레이아웃 생성");
 
		//TODO 부모 프레임 크기 설정 (가로, 세로)
		frm.setSize(500, 500);
 
		//TODO 부모 프레임을 화면 가운데에 배치
		frm.setLocationRelativeTo(null);
 
		//TODO 부모 프레임을 닫았을 때 메모리에서 제거되도록 설정
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		//TODO 부모 레이아웃 설정
		frm.getContentPane().setLayout(null);
		
		//TODO 자식 레이아웃 [텍스트] 생성
		txt = new JLabel("");
		txt.setBounds(0, 0, 500, 100); //setBounds(가로위치, 세로위치, 가로길이, 세로길이);
		txt.setFont(new Font("맑은 고딕", 0, 20)); //TODO 폰트 정의
		txt.setHorizontalAlignment(JLabel.CENTER); //TODO 텍스트 센터 표시 설정
		
		//TODO 자식 레이아웃 [버튼] 생성
		JPanel grid_panel = new JPanel();
		grid_panel.setLayout(new GridLayout(4, 4, 0, 0)); //테이블 형식 - 행, 열, 가로 여백, 세로 여백
						
		for(int idx=0; idx<arr.length; idx++) {
			btn[idx] = new JButton(arr[idx]);
			btn[idx].setFont(new Font("맑은 고딕", 0, 20)); //TODO 폰트 정의
			btn[idx].setBackground(Color.GRAY); //TODO 백그라운드 색상 정의
			btn[idx].setForeground(Color.WHITE); //TODO 텍스트 색상 정의
			
			grid_panel.add(btn[idx]); //TODO JPanel에 버튼 추가
			
		}
		grid_panel.setBounds(0, 100, 500, 350); //setBounds(가로위치, 세로위치, 가로길이, 세로길이);
		 
		//TODO 부모 프레임에다가 자식 컴포넌트 추가
		frm.getContentPane().add(txt);
		frm.getContentPane().add(grid_panel);
		
		//TODO 부모 프레임이 보이도록 설정
		frm.setVisible(true);
		
	}
	}

