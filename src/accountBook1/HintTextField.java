package accountBook1;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextField;

//힌트 텍스트를 표시하는 텍스트 필드 컴포넌트
public class HintTextField extends JTextField {
 private final String _hint; // 힌트 문자열
 
 // 생성자: 힌트 문자열을 받아옴
 public HintTextField(String hint) {
     _hint = hint;
 }
 
 // 그래픽을 그리는 메소드 재정의
 @Override
 public void paint(Graphics g) {
     super.paint(g);
     
     // 텍스트 필드의 텍스트 길이가 0이면 힌트 표시
     if (getText().length() == 0) {
         int h = getHeight();
         ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         Insets ins = getInsets();
         FontMetrics fm = g.getFontMetrics();
         
         // 배경색과 전경색 계산
         int c0 = getBackground().getRGB();
         int c1 = getForeground().getRGB();
         int m = 0xfefefefe;
         int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
         g.setColor(new Color(c2, true));
         
         // 힌트 문자열을 그려줌
         g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
     }
 }
}
