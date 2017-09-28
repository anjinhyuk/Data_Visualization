package starter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;


public class Main extends JFrame {
	
	private Vis mainPanel;
	private static String myQuery;
	//private static int count;
	public static ArrayList<String> DATA = new ArrayList<String>();
	public static ArrayList<Double> GRAPH = new ArrayList<Double>();
	public static int graphOption = 1;
	
	public Main() {		
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		myQuery = new String("SELECT COUNT(*),major FROM STUDENTS GROUP BY major");
		mainPanel = new Vis();
		setContentPane(mainPanel);
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Barchart and Linechart");
		setVisible(true);
		connectToDatabase();
	}

	private void connectToDatabase() {
		try {
			//connect to the folder
			Connection conn = DriverManager.getConnection("jdbc:derby:barchart");
			Statement s = conn.createStatement();
			//mySql commend
			ResultSet rs = s.executeQuery(myQuery);
			//using while loop
			while (rs.next()){
				double count = rs.getDouble(1);
				String catName = rs.getString(2);
				System.out.println(catName + " has " + count);
				DATA.add(catName);
				GRAPH.add(count);
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
		JMenu queryMenu = new JMenu("Query");
		JMenu chartMenu = new JMenu("Chart Type");
		//query submenu
		JMenuItem item1 = new JMenuItem("The number of students in each of the 4 majors");
		JMenuItem item2 = new JMenuItem("The number of students from each of the \"home areas\"");
		JMenuItem item3 = new JMenuItem("The average GPA of students in each of the 4 majors");
		JMenuItem item4 = new JMenuItem("The average number of credits attempted, per year");
		JMenuItem item5 = new JMenuItem("The average GPA of students from each of the \"home areas\"");
		
		JMenuItem bar = new JMenuItem("Bar Chart");
		JMenuItem line = new JMenuItem("Line Chart");
			
		//The number of students in each of the 4 majors
		item1.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("The number of students in each of the 4 majors");
			myQuery = new String("SELECT COUNT(*),major FROM STUDENTS GROUP BY major");
			connectToDatabase();
		});
		//The number of students from each of the \"home areas\"
		item2.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("The number of students from each of the \"home areas\"");
			myQuery = new String("SELECT COUNT(*),area FROM STUDENTS GROUP BY area");
			connectToDatabase();
		});
		// The average GPA of students in each of the 4 majors
		item3.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("The average GPA of students in each of the 4 majors");
			myQuery = new String("SELECT avg(gpa),major FROM STUDENTS GROUP BY major");
			connectToDatabase();
		});
		// The average number of credits attempted, per year
		item4.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("The average number of credits attempted, per year");
			myQuery = new String("SELECT avg(credits_attempted),grad_year FROM STUDENTS GROUP BY grad_year");
			connectToDatabase();
		});
		// At least 1 more query of your choice
		item5.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("The average GPA of students from each of the \"home areas\"");
			myQuery = new String("SELECT avg(gpa),area FROM STUDENTS GROUP BY area");
			connectToDatabase();
		});
		// bar chart
		bar.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("Just clicked barchart");
			graphOption=1;
			connectToDatabase();
		});
		// line chart
		line.addActionListener((ActionEvent e) -> {
			DATA.clear();
			GRAPH.clear();
			System.out.println("Just clicked linechart");
			graphOption=2;
			connectToDatabase();
		});
		//now hook all menues together
		menuBar.add(queryMenu);
		menuBar.add(chartMenu);
		queryMenu.add(item1);
		queryMenu.add(item2);
		queryMenu.add(item3);
		queryMenu.add(item4);
		queryMenu.add(item5);
		chartMenu.add(bar);
		chartMenu.add(line);
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