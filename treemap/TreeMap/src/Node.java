import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Node {
	private File file;
	private List<Node> children;
	private long size;
	private String colorScheme;
	private Color color;
	public static double bytes;
	private Rectangle box;
	
	public Node(File f){
//		File file = new File("c:\\Users\\Jingeun\\Desktop\\career\\EnglishResume.pdf");
//		if(file.exists()){
//			bytes = file.length();
//		}
		children = new ArrayList<Node>();
		file = f;
		size = setSize(file);
	}

	public List<Node> getChildren() {
		return children;
	}
	
//	public String getToolip(int x, int y) {
//		
//		return null;
//		
//	}
	
	public void setColorScheme(String colorScheme) {
		if(this.getChildren().isEmpty()) {
			this.colorScheme = colorScheme;
		}
		else {
			for(Node n : this.getChildren()) {
				n.setColorScheme(colorScheme);
			}
		}
	}
	//getting total size of files
	public long setSize(File f) {
		long total = 0;
		if(f.isFile()) {
			total = total + f.length();
		}
		else {
			for (File f2: f.listFiles()) {
				Node child = new Node(f2);
				children.add(child);
				total = total + child.getSize();
			}
		}
		return total;
	}
	
	public long getSize() {
		return size;
	}

	private void paintSetting() {
		if(colorScheme == "type") {
			String ext = getFileExtension(file);
			ext.toLowerCase();
			//colors
			if (ext.equals("txt") || ext.equals("rtf")) {
				color = new Color(46, 139, 87);
			} else if (ext.equals("docx") || ext.equals("doc")) {
				color = new Color(0, 26, 255);
			} else if (ext.equals("avi")) {
				color = new Color(171, 9 , 255);
			} else if (ext.equals("bat")) {
				color = new Color(255, 85, 0);
			} else if (ext.equals("bmp")) {
				color = new Color(0, 255, 255);
			} else if (ext.equals("java")) {
				color = new Color(147, 112, 219);
			} else if (ext.equals("class")) {
				color = new Color(10, 220, 150);
			} else if (ext.equals("csv") || ext.equals("xls") || ext.equals("xlsx")) {
				color = new Color(5, 159, 9);
			} else if (ext.equals("cvs")) {
				color = new Color(186, 0, 43);
			} else if (ext.equals("dbf")) {
				color = new Color(0, 50, 186);
			} else if (ext.equals("dif")) {
				color = new Color(230, 255, 0);
			} else if (ext.equals("exe")) {
				color = new Color(255, 0, 0);
			} else if (ext.equals("gif")) {
				color = new Color(0, 201, 201);
			} else if (ext.equals("html") || ext.equals("htm")) {
				color = new Color(188, 143, 143);
			} else if (ext.equals("mid") || ext.equals("midi")) {
				color = new Color(176, 196, 222);
			} else if (ext.equals("mov") || ext.equals("qt")) {
				color = new Color(102, 13, 255);
			} else if (ext.equals("mtb") || ext.equals("mtw")) {
				color = new Color(153, 153, 51);
			} else if (ext.equals("pdf")) {
				color = new Color(205, 0, 0);
			} else if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg")) {
				color = new Color(171, 255, 0);
			} else if (ext.equals("ppt") || ext.equals("pptx")) {
				color = new Color(0, 255, 255);
			} else if (ext.equals("wpd") || ext.equals("wp5")) {
				color = new Color(0, 124, 103);
			} else {
				color = new Color(255, 255, 255);
			}
			//ages
		} else if(colorScheme == "age") {
			long fileAge = file.lastModified();
			//Year, SixMonths, ThreeMonths, Month
			Calendar year = Calendar.getInstance();
			year.add(Calendar.YEAR, -1);
			Calendar sixMonths = Calendar.getInstance();
			sixMonths.add(Calendar.MONTH, -6);
			Calendar threeMonths = Calendar.getInstance();
			threeMonths.add(Calendar.MONTH, -3);
			Calendar month = Calendar.getInstance();
			month.add(Calendar.MONTH, -1);
			if(fileAge < year.getTimeInMillis()) {
				color = new Color(255, 0, 0);
			} else if(fileAge > year.getTimeInMillis() && fileAge < sixMonths.getTimeInMillis()) {
				color = new Color(255, 69, 0);
			} else if(fileAge > sixMonths.getTimeInMillis() && fileAge < threeMonths.getTimeInMillis()) {
				color = new Color(255, 127, 80);
			} else if(fileAge > threeMonths.getTimeInMillis() && fileAge < month.getTimeInMillis()) {
				color = new Color(255, 140, 0);
			} else {
				color = new Color(255, 165, 0);
			}
			//whites
		} else if(colorScheme == "white") {
			color = new Color(255, 255, 255);
			//permission
		} else if(colorScheme == "permissions") {
			if(file.canExecute() && !file.canRead() && !file.canWrite()) {
				color = new Color(255, 0, 0);
			} else if(file.canWrite() && !file.canExecute() && !file.canRead()) {
				color = new Color(0, 0, 255);
			} else if(file.canWrite() && file.canExecute() && !file.canRead()) {
				color = new Color(200, 0, 200);
			} else if(file.canRead() && !file.canWrite() && !file.canExecute()) {
				color = new Color(0, 213, 255);
			} else if(file.canRead() && file.canExecute() && !file.canWrite()) {
				color = new Color(253, 138, 253);
			} else if(file.canRead() && file.canWrite() && !file.canExecute()) {
				color = new Color(0, 150, 255);
			} else if(file.canRead() && file.canWrite() && file.canExecute()) {
				color = new Color(0, 255, 0);
			} else {
				color = new Color(0, 0, 0);
			}
		}
	}
	
	//getting extension from a file
	private String getFileExtension (File f) {
		String fileName = f.getName();
		if(fileName.lastIndexOf(".") != (-1) && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".")+1);
		}
		else {
			return "";
		}
	}
	//drawing algorithm
	public void draw(Graphics2D g, Rectangle r, String orientation, double x, double y) {
		this.setRect(r);
		double w = r.getWidth();
		double h = r.getHeight();
		if(file.isFile()) {
			paintSetting();
			g.setColor(color);
			g.fill(r);
			g.setColor(Color.BLACK);
			g.draw(r);
		} else {
			g.setColor(new Color(255,255,255,128));
			g.fill(r);
			if(orientation == "v") {
				double pixPerByte = w / size;
				for (Node c : children) {
					double cWidth = c.getSize() * pixPerByte;
					r.setBounds((int)x, (int)y, (int)cWidth, (int)h );
					c.draw(g, r,  "h", x, y);
					x = x + cWidth;
				}
			}
			else {
				double pixPerByte = h / size;
				for(Node c : children) {
					double cHeight = c.getSize() * pixPerByte;
					r.setBounds((int)x, (int)y, (int)w, (int)cHeight);
					c.draw(g,  r,  "v", x, y);
					y = y + cHeight;
				}
			}
		}
	}
	
	public void setRect(Rectangle r) {
		this.box = r;
	}
}
