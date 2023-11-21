package accountBook1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
//import a_login.DBuser;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import a_loginFinal.DB;
import a_loginFinal.SessionManager;

// icon images @ fontawesome.com
// svg to png @ svgtopng.com
// font size 20

public class Expenses extends JFrame implements ItemListener, ActionListener{
   private Font fnt_title = new Font("fira code", Font.BOLD, 16);
   private Font fnt_regular = new Font("fira code", Font.PLAIN, 14);
   
   private JPanel topPanel = new JPanel();
   private JPanel centerPanel = new JPanel();
   private JPanel bottomPanel = new JPanel();
   
   private JLabel dateLabel;
   private JTextField amountTf, descriptionTf;
   private JComboBox<String> paymentTypeCb;
   private String[] iconArray;
   private String[] rbLabelArray;
   private JToggleButton[] rbArray;
   private RoundedButton confirmButton, cancelButton;
   
   private String year, month, clickedDay, expenseType;
   private String[] expenseData;
   private String dateId;
   private String sqlDateId;
   
   DB db = new DB();
   SessionManager sm;
   
   
   public Expenses(int year, int month, int clickedDay, SessionManager sm) {
      super("지출");
      this.sm = sm;
      this.year = String.valueOf(year);
      this.month = (month < 10 ? "0" : "") + month;
      this.clickedDay = (clickedDay < 10 ? "0" : "") + clickedDay;
      this.dateId = this.year + this.month + this.clickedDay;
      this.sqlDateId = this.year + "-" + this.month + "-" + this.clickedDay;
      
      // ======= 상단패널 ========
      
      // 날짜 아이콘
      String calImg = "src/Images/calendar20.png";
      JLabel calLabel = new JLabel(new ImageIcon(calImg));
      calLabel.setBounds(0,0,50,50);

      // 날짜 라벨
      dateLabel = new JLabel(String.format("%d년 %d월 %d일", year, month, clickedDay));
      dateLabel.setBounds(47,0,400,50);
      dateLabel.setFont(fnt_regular);
      
      // -------
      
      // 입금 금액 아이콘
      String amountImg = "src/Images/won_sign20.png";
      JLabel amountLabel = new JLabel(new ImageIcon(amountImg));
      amountLabel.setBounds(0,45,50,50);

      //출금 금액 텍스트필드
      
      amountTf = new HintTextField("지출 금액") {
         public void setBorder(Border border) {
            
         }
      };
      amountTf.addKeyListener(new KeyAdapter() {
         @Override
         public void keyTyped(KeyEvent e) {
            char ch = e.getKeyChar();
            if(!Character.isDigit(ch)) {
               e.consume();
            }
         }
      });
      amountTf.setBounds(45,55,150,30);
      amountTf.setFont(fnt_regular);
      
      // -------
      
      // 자산(결제수단) 아이콘
      
      String paymentTypeImg = "src/Images/payment_type20.png";
      JLabel paymentTypeLabel = new JLabel(new ImageIcon(paymentTypeImg));

      // 자산(결제수단) 콤보박스
      List<String> paymentTypes = db.getPaymentTypesForUser(sm.getID());
      paymentTypeCb = new JComboBox<>(paymentTypes.toArray(new String[0]));
      paymentTypeCb.setBackground(Color.white);
      
      // -------
      
      // 상세내역 아이콘
      String descriptionImg = "src/Images/description20.png";
      JLabel descriptionLabel = new JLabel(new ImageIcon(descriptionImg));
      descriptionLabel.setBounds(0,90,50,50);

      // 상세 내역 텍스트필드
      descriptionTf = new HintTextField("상세 내역") {
         public void setBorder(Border border) {
            
         }
      };
      descriptionTf.setBounds(45,100,400,30);
      descriptionTf.setFont(fnt_regular);
      
      // -------
      
      // 상단 패널 레이아웃
      GroupLayout layout = new GroupLayout(topPanel);
      topPanel.setLayout(layout);
      topPanel.setBackground(Color.white);
      layout.setAutoCreateGaps(true);
      layout.setAutoCreateContainerGaps(true);
      layout.setHorizontalGroup(
               layout.createSequentialGroup()
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                           .addComponent(calLabel)
                           .addComponent(amountLabel)
                           .addComponent(paymentTypeLabel)
                           .addComponent(descriptionLabel))
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                           .addComponent(dateLabel)
                           .addComponent(amountTf)
                           .addComponent(paymentTypeCb)
                           .addComponent(descriptionTf))
            );
      layout.setVerticalGroup(
               layout.createSequentialGroup()
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                           .addComponent(calLabel)
                           .addComponent(dateLabel))
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                           .addComponent(amountLabel)
                          .addComponent(amountTf))
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                           .addComponent(paymentTypeLabel)
                          .addComponent(paymentTypeCb))
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                           .addComponent(descriptionLabel)
                          .addComponent(descriptionTf))
            );
      
      add(BorderLayout.NORTH, topPanel);
      
      
      // ======= 중앙 패널 =======
      
      ButtonGroup radioGroup = new ButtonGroup();
      centerPanel.setLayout(new GridLayout(5,5));
      centerPanel.setBackground(Color.white);
      //아이콘배열
      iconArray = new String[25];
      for(int i=0; i<iconArray.length; i++) {
         iconArray[i] = "src/Images/expenses/expenses" + (i+1) +".png";
      }
      //버튼배열
      rbArray = new JToggleButton[25];
      rbLabelArray = new String[]{"식비","간식","교통","문화","오락",
                           "교육","여행","패션","미용","생필품",
                           "통신","주거비","대출이자","공과금","적금",
                           "편의점","건강","카페","담배","술",
                           "취미","용돈","선물","데이트","기타"};
      
      for(int i=0; i<rbArray.length; i++) {
         rbArray[i] = new JRadioButton(rbLabelArray[i], new ImageIcon(iconArray[i]));
         rbArray[i].setBorderPainted(false);
         radioGroup.add(rbArray[i]);
         rbArray[i].addItemListener(this);
         centerPanel.add(rbArray[i]);
         rbArray[i].setContentAreaFilled(false);
            rbArray[i].setFocusPainted(false);
            rbArray[i].addActionListener(this);
      }
      pack();
      add(BorderLayout.CENTER, centerPanel);
      
      // ======= 하단 패널 =======
      
      confirmButton = new RoundedButton("확인");
      cancelButton = new RoundedButton("취소");
      
      bottomPanel.setLayout(new FlowLayout());
      bottomPanel.setBackground(Color.white);
      bottomPanel.add(confirmButton);
      bottomPanel.add(cancelButton);
      
      add(BorderLayout.SOUTH, bottomPanel);
      
      confirmButton.addActionListener(this);
      cancelButton.addActionListener(this);
      
      
      setResizable(false);
      setSize(450,500);
      setLocationRelativeTo(null);
        setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
	// 확인 또는 취소 버튼을 눌렀을 때 동작을 정의하는 메서드
      Object obj = e.getSource();
      if(obj == confirmButton) {
         onConfirmButtonClicked();
      }else if(obj == cancelButton) {
         Expenses.super.dispose();
      }
      
      for (int i = 0; i < rbArray.length; i++) {
            if (e.getSource() == rbArray[i]) {
                JToggleButton selectedButton = rbArray[i];
                 if (selectedButton.isSelected()) {
                     // 선택된 경우 색상 변경
                     selectedButton.setForeground(new Color(207, 239, 211));
                 } else {
                     // 선택이 해제된 경우 색상 변경 및 선택 취소
                     selectedButton.setForeground(Color.darkGray);
                     selectedButton.setSelected(false);
                 }
             } else {
                 // 다른 버튼의 경우 색상을 원래대로 변경
                 rbArray[i].setForeground(Color.darkGray);
                 rbArray[i].setSelected(false);
             }
            }
        
   }

   @Override
   public void itemStateChanged(ItemEvent e) {
	// RadioButton 아이템 선택 상태가 변경될 때 동작을 정의하는 메서드
      if(e.getStateChange() == ItemEvent.SELECTED) {
         for(int i=0; i<rbArray.length; i++) {
            if(e.getSource() == rbArray[i]) {
               expenseType = rbArray[i].getText();
               break;
            }
         }
      }
   }
   
   
   private void onConfirmButtonClicked() {
	// 확인 버튼 클릭 시 동작을 정의하는 메서드
      PaymentTypeConverter converter = new PaymentTypeConverter();
      String korPaymentType = (String) paymentTypeCb.getSelectedItem();
        String engPaymentType = converter.convertKorToEngPayType(korPaymentType);

        expenseData = new String[4];
        expenseData[0] = amountTf.getText();
        expenseData[1] = expenseType;
        expenseData[2] = engPaymentType;
        expenseData[3] = descriptionTf.getText();

        // 데이터베이스에 지출 데이터 추가
        db.insertExpense(
            sm.getID(),
            sqlDateId,
            Integer.parseInt(expenseData[0]),
            expenseData[1],
            expenseData[2],
            expenseData[3]
        );

        // 지출에 따른 계좌 잔액 업데이트
        db.updateAccountBalancesForExpenses(sm.getID(), expenseData);

        Expenses.super.dispose();
    }

}