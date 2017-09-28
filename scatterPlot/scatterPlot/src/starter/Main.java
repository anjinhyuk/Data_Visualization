package starter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;


public class Main extends JFrame {
	
	private Vis mainPanel;
	private static String myQuery;
	public static ArrayList<Double> Xaxis = new ArrayList<Double>();
	public static ArrayList<Double> Yaxis = new ArrayList<Double>();
	public static String xCat = "Credits_attempted";
	public static String yCat = "Credits_passed";
	public static ArrayList<String> Gender = new ArrayList<String>();
	public static boolean genCheck = false;
	
	public Main() {
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		mainPanel = new Vis();
		setContentPane(mainPanel);
		myQuery = new String("SELECT credits_attempted, credits_passed, gender FROM cis2012");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Scatter Plot");
		setVisible(true);
		connectToDatabase();
	}
	
	private void connectToDatabase() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:derby:scotty");
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(myQuery);
			while (rs.next()){
				double xVal = rs.getDouble(1);
				double yVal = rs.getDouble(2);
				String gen = rs.getString(3);
				//System.out.println("x: " + xVal + " y: " + yVal);
				Xaxis.add(xVal);
				Yaxis.add(yVal);
				Gender.add(gen);
				//System.out.println("gender is " + gen);
			};
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JMenuBar setupMenu() {
		//instantiate menubar, menus, and menu options
		JMenuBar menuBar = new JMenuBar();
		JMenu plotMenu = new JMenu("Plot");
		JMenu queryMenu = new JMenu("Query");
		//query submenu
		JMenuItem q1 = new JMenuItem("Credits attempted vs. Credits passed");
		JMenuItem q2 = new JMenuItem("Credits attempted vs. GPA");
		JMenuItem q3 = new JMenuItem("Credits passed vs. GPA");
		JMenuItem q4 = new JMenuItem("Age vs GPA");
		JMenuItem q5 = new JMenuItem("Credits attempted vs. Age");
		JMenuItem p1 = new JMenuItem("Reset Scale");
		JMenuItem p2 = new JMenuItem("Color Gender");
		//JMenuItem p3 = new JMenuItem("Color Gender");
		
		//Credits attempted vs. Credits passed
		q1.addActionListener((ActionEvent e) -> {
			Xaxis.clear();
			Yaxis.clear();
			myQuery = new String("SELECT credits_attempted, credits_passed, gender FROM cis2012");
			System.out.println("Just clicked q1");
			connectToDatabase();
			xCat = "Credits_attempted";
			yCat = "Credits_passed";
		});
		//Credits attempted vs. GPA
		q2.addActionListener((ActionEvent e) -> {
			Xaxis.clear();
			Yaxis.clear();
			myQuery = new String("SELECT credits_attempted, gpa, gender FROM cis2012");
			System.out.println("Just clicked q2");
			connectToDatabase();
			xCat = "Credits_attempted";
			yCat = "GPA";
		});
		//Credits passed vs. GPA
		q3.addActionListener((ActionEvent e) -> {
			Xaxis.clear();
			Yaxis.clear();
			myQuery = new String("SELECT credits_passed, gpa, gender FROM cis2012");
			System.out.println("Just clicked q3");
			connectToDatabase();
			xCat = "Credits_passed";
			yCat = "GPA";
		});
		//Age vs GPA
		q4.addActionListener((ActionEvent e) -> {
			Xaxis.clear();
			Yaxis.clear();
			myQuery = new String("SELECT age, gpa, gender FROM cis2012");
			System.out.println("Just clicked q4");
			connectToDatabase();
			xCat = "Age";
			yCat = "GPA";
		});
		// Credits attempted vs. Age
		q5.addActionListener((ActionEvent e) -> {
			Xaxis.clear();
			Yaxis.clear();
			myQuery = new String("SELECT credits_attempted, age, gender FROM cis2012");
			System.out.println("Just clicked q5");
			connectToDatabase();
			xCat = "Credits_attempted";
			yCat = "Age";
		});
		// Reset Scale
		p1.addActionListener((ActionEvent e) -> {
			System.out.println("Just clicked p1");
			Vis.zoomed = false;
			connectToDatabase();
		});
		// Color Gender
		p2.addActionListener((ActionEvent e) -> {
			genCheck = true;
			System.out.println("Just clicked p2");
			connectToDatabase();
		});
//		// Color Gender
//		p3.addActionListener((ActionEvent e) -> {
//			System.out.println("Just clicked p3");
//			connectToDatabase();
//		});
		
		//now hook all menues together
		menuBar.add(queryMenu);
		menuBar.add(plotMenu);
		queryMenu.add(q1);
		queryMenu.add(q2);
		queryMenu.add(q3);
		queryMenu.add(q4);
		queryMenu.add(q5);
		plotMenu.add(p1);
		plotMenu.add(p2);
		//plotMenu.add(p3);
		
		return menuBar;
	}

	public static void main(String[] args) {

		//this makes the GUI adopt the look-n-feel of the windowing system (Windows/X11/Mac)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
}
