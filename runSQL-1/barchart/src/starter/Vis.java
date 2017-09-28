package starter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.Spring;


public class Vis extends JPanel {
	
	Rectangle bar;
	BasicStroke thinkLine;
	Main main;
	
	public String names;
	public String scales;
	public double numbers;
	public static double max;
	
	public Vis() {
		super();
		thinkLine = new BasicStroke(5);
	}

	//draw coordinate
	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		int w = getWidth();
		int h = getHeight();
		
		//draw blank background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		
		//coordinate
		int x1 = (int)(0.05 * w);
		int y1 = (int)(0.05 * h);
		int y2 = (int)(0.95 * h);
		int x2 = (int)(0.95 * w);
		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x1, y2, x2, y2);
		
		//categories
		int val1 = Main.DATA.size();
		for (int i = 0 ; i < val1; i++){
			names = Main.DATA.get(i);
			g.drawString(names, (int)((0.05*w)+(i*(0.9*w/val1))), (int)(0.98*h));
			repaint();
		}
		//scales
		int val2 = Main.GRAPH.size();
		max = (double)(Collections.max(Main.GRAPH));
		DecimalFormat setP = new DecimalFormat("#.00");
		for (int j = 1; j <= 4; j++) {
			g.drawString(setP.format(((max/4)*j)), 0, (int)(y2-(((0.9/4)*j)*h)));
			repaint();
		}
	
		//graphs
		g.setColor(Color.GREEN);
		g.setStroke(thinkLine);
		if(Main.graphOption == 1) {
			for (int i = 0; i < val2 ; i++) {
				numbers = Main.GRAPH.get(i);
				g.fillRect((int)(x1+(((0.9*w)/val1)*i)), (int)((0.95*h)-((numbers/max)*(0.9*h))), (int)((0.8*w)/val1), (int)((numbers/max)*(0.9*h)));
				repaint();
			}
		} else {
			for (int i = 0; i < val2; i++) {
				numbers = Main.GRAPH.get(i);
				int x = (int)(((0.05*w)+(i*(0.9*w/val1)))+((0.9*w/val1)*0.4));
				int y = (int)((0.95*h)-((numbers/max)*(0.9*h)));
				g.fillOval(x, y, 20, 20);
				if(i > 0) {
					int j = i-1;
					double number2 = Main.GRAPH.get(i-1);
					g.drawLine((int)(((0.05*w)+(j*(0.9*w/val1)))+((0.9*w/val1)*0.4)), (int)((0.95*h)-((number2/max)*(0.9*h))), x, y);
				}
			}
		}
	}
}
