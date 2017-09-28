package starter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;


public class Vis extends JPanel {
	
	public String text;
	
	public Vis() {
		super();
	}
	
	//print text method
	public void printText(int count, String gender, String age, String time){
		text = new String(""+ count + " athlets have the attributes: " + gender + ", " + age + ", " + time);
		repaint();
	}
		
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		
		//draw blank background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//render visualization
		g.setColor(Color.BLACK);
		g.drawString(text, 10, 20);
	}

}
