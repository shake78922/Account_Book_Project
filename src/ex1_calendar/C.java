package ex1_calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class C extends JFrame{
	JPanel panel = new JPanel();
	public C() {
		
		JButton addButton = new JButton("Add Button");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewButton();
            }
        });

        panel.add(addButton);
        add(panel);
        setVisible(true);
    }

    private void addNewButton() {
        JButton newButton = new JButton("New Button");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 새로 생성된 버튼을 클릭했을 때 수행할 동작
                System.out.println("New Button Clicked!");
            }
        });

        panel.add(newButton);
        panel.revalidate();
        panel.repaint();
    }

	}

