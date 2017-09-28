import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Vis extends JPanel implements MouseMotionListener {
	
	private int top;
	private Node root;
	
	public Vis(){
		super();
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		int w1 = getWidth();
		int h1 = getHeight();
		g.setColor(Color.WHITE);
		g.fillRect(0, top, w1, h1-top);
		Rectangle box = new Rectangle(0, top, w1, h1 - top);
		root.draw(g, box, "v", 0, top);		
	}
	
	public void setTopHeight(int h) {
		top = h;
	}

	public void setRoot(Node r) {
		root = r;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		
	}
	
}
