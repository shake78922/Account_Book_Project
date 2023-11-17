package accountBook1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

// icon images @ fontawesome.com
// svg to png @ svgtopng.com

public class Deposits extends JFrame implements ItemListener, ActionListener{
	Font fnt = new Font("SansSerif", Font.BOLD, 16);
	public Deposits(int year, int month, int clickedDay) {
		super("입금");
		
		setLayout(null);
		
		String calImg = "src/Images/calendar20.png";
		JLabel calLabel = new JLabel(new ImageIcon(calImg));
		calLabel.setBounds(0,0,50,50);
		add(calLabel);
		
		JLabel dateLabel = new JLabel(String.format("%d년 %d월 %d일", year, month, clickedDay));
		dateLabel.setBounds(45,0,400,50);
		dateLabel.setFont(fnt);
		add(dateLabel);
		
		String amountImg = "src/Images/won_sign20.png";
		JLabel amountLabel = new JLabel(new ImageIcon(amountImg));
		amountLabel.setBounds(0,50,50,50);
		add(amountLabel);
		
		JTextField amountTf = new HintTextField("입금할 금액");
		amountTf.setBounds(45,50,200,40);
		add(amountTf);
		
		setBounds(1100,100,500,500);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}

class HintTextField extends JTextField implements FocusListener {

	  private final String hint;
	  private boolean showingHint;

	  public HintTextField(final String hint) {
	    super(hint);
	    this.hint = hint;
	    this.showingHint = true;
	    super.addFocusListener(this);
	  }

	  @Override
	  public void focusGained(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText("");
	      showingHint = false;
	    }
	  }
	  @Override
	  public void focusLost(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText(hint);
	      showingHint = true;
	    }
	  }

	  @Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }
	}
