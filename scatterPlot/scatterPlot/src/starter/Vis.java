package starter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener, MouseMotionListener{
	
	public String text;
	private static double max1;
	private static double max2;
	private static double min1;
	private static double min2;
	private Rectangle box = null;
	private Point mouseDown;
	private Ellipse2D circle;
	private ArrayList<Ellipse2D> circles = new ArrayList<Ellipse2D>();
	private static int check;
	public static boolean zoomed = false;
	
	public Vis() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		circle = new Ellipse2D.Double(100, 100, 15, 15);
	}

	@Override
	public void paintComponent(Graphics g1){
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
		int x3 = (int)(0.88 * w);
		//int y3 = (int)(0.01 * h);
		g.drawLine(x1, y1, x1, y2);
		g.drawLine(x1, y2, x2, y2);
		g.drawString(""+0.00, 0, y2);
		g.drawString(""+0.00, x1, (int)(0.98*h));
		
		int val1 = Main.Xaxis.size();
		max1 = (double)(Collections.max(Main.Xaxis));
		max2 = (double)(Collections.max(Main.Yaxis));
		min1 = (double)(Collections.min(Main.Xaxis));
		min2 = (double)(Collections.min(Main.Xaxis));
		DecimalFormat setP = new DecimalFormat("#.00");
		for (int j = 1; j <= 4; j++) {
			g.drawString(setP.format(((max2/4)*j)), 0, (int)(y2-(((0.9/4)*j)*h)));
		}
		for (int j = 1; j <= 4; j++) {
			g.drawString(setP.format(((max1/4)*j)), (int)(((0.05)+(0.225 * j))*w), (int)(0.98*h));
		}
		//dots
		circles.clear();
		int radius = 6;
		for (int i = 0; i < val1; i++) {
			g.setColor(Color.green);
			Ellipse2D dots = new Ellipse2D.Double((((0.9/max1) * (Main.Xaxis.get(i)) * w)+x1), (((0.9 / max2)*(max2 - Main.Yaxis.get(i)) * h)+y1-(radius / 2)), radius, radius);
			//draw black dots when it is selected
			if (check == i) {
				g.setColor(Color.black);
				g.fill(dots);
			} 
			else {
				g.setColor(Color.green);
				g.fill(dots);
				g.setColor(Color.YELLOW);
				//gender function
				if (Main.genCheck) {
					if(Main.Gender.get(i).equals("M")) {
						g.setColor(Color.blue);
						g.fill(dots);
						g.setColor(Color.YELLOW);
					}
					else {
						g.setColor(Color.red);
						g.fill(dots);
					}
				}
			}
			circles.add(dots);			
		}
		if (box != null) {
			g.draw(box);
		}
		//Name of Values
		g.setColor(Color.BLACK);
		g.drawString(Main.xCat, x3, y2);
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g.setTransform(at);
		g.drawString(Main.yCat, (int)((x2-w)-(0.1*h)), (int)(y1+(0.03*w)));
		repaint();
	}
	@Override
	public void mouseMoved(	MouseEvent e) {
		//showing detail values
		int val1 = Main.Xaxis.size();
		int a = e.getX();
		int b = e.getY();
		for (int i = 0; i < val1; i++){
			if(circles.get(i).contains(a,b)) {
				setToolTipText(Main.xCat + " is " + Main.Xaxis.get(i) + " and " + Main.yCat + " is " + Main.Yaxis.get(i));
				check = i;
				break;
			} 
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mouseDown = new Point(x,y);
		box = new Rectangle();
		//System.out.println("mouse button pressed! x=" + x + ", y=" + y);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("dragging!");
		int x = e.getX();
		int y = e.getY();
		box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, x, y);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse button released!");
		int x = e.getX();
		int y = e.getY();
		Point mouseUp = new Point(x,y);		
		double w = getWidth();
		double h = getHeight();
		Point p1, p2;
		if (mouseDown.x > mouseUp.x) {
			p1 = mouseUp;
			p2 = mouseDown;
		} else {
			p1 = mouseDown;
			p2 = mouseUp;
		}
		box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, mouseUp.x, mouseUp.y);
		double xRange = max1 - min1;
		double yRange = max2 - min2;
		max1 = min1 + (p2.x / w) * (xRange);
		min1 = min1 + (p1.x / w) * (xRange);
		max2 = min2 + ((h - p1.y) / h) * (yRange);
		min2 = min2 + ((h - p2.y) / h) * (yRange);
		box = null;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

}
