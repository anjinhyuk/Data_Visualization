package starter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;


public class Main extends JFrame {
	
	private Vis mainPanel;
	//private static int count;
	private String age;
	private String gender;
	private String time;
	
	public Main() {
		//default value of the example
		gender = new String("GENDER = 'M'");
		age = new String("AGE > 40");
		time = new String("HOURS > 5");
		
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		
		mainPanel = new Vis();
		setContentPane(mainPanel);

		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Put the title of your program here");
		setVisible(true);
		connectToDatabase();
	}
	
	private void connectToDatabase() {
		try {
			//connect to the forder
			Connection conn = DriverManager.getConnection("jdbc:derby:cs490R");
			Statement s = conn.createStatement();
			//sql commend
			ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM marathon WHERE " + gender + " AND " + age + " AND " + time);
			rs.next();
			int count = rs.getInt(1);//give me the integer located in column 1 of the result set
			mainPanel.printText(count, gender, age, time);
			System.out.println("There are " + count + " rows in the table.");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JMenuBar setupMenu() {
		//instantiate menubar, menus, and menu options
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu queryMenu = new JMenu("Query");
		//query submenu
		JMenu item1 = new JMenu("Gender");
		JMenuItem g1 = new JMenuItem("Male");
		JMenuItem g2 = new JMenuItem("Female");
		JMenu item2 = new JMenu("Time");
		JMenuItem t1 = new JMenuItem("less than 4 hours");
		JMenuItem t2 = new JMenuItem("between 4 and 5 hours");
		JMenuItem t3 = new JMenuItem("more than 5 hours");
		JMenu item3 = new JMenu("Age");
		JMenuItem a1 = new JMenuItem("less than 25");
		JMenuItem a2 = new JMenuItem("between 25 and 39");
		JMenuItem a3 = new JMenuItem("greater than or equal to 40");
		
		//Male
		g1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked male");
				gender = new String ("GENDER = 'M'");
				connectToDatabase();
			}			
		});
		//Female
		g2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked female");
				gender = new String ("GENDER = 'F'");
				connectToDatabase();
//				mainPanel.printText(count, gender, age, time);
			}			
		});
		// < 4
		t1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked < 4");
				time = new String("HOURS < 4");
				connectToDatabase();
			}			
		});
		//4 < x < 5
		t2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked 4 < x < 5");
				time = new String("HOURS > 4 AND HOURS < 5");
				connectToDatabase();
			}			
		});
		// > 5
		t3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked menu > 5");
				time = new String("HOURS > 5");
				connectToDatabase();
			}			
		});
		// < 25
		a1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked < 25");
				age = new String("AGE < 25");
				connectToDatabase();
			}			
		});
		// 25 < x < 39
		a2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked 25 < x < 39");
				age = new String ("AGE > 25 AND AGE < 39");
				connectToDatabase();
			}			
		});
		// < 40
		a3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Just clicked >= 40");
				age = new String ("AGE >= 40");
				connectToDatabase();
			}			
		});
		//now hook all menues together
		menuBar.add(fileMenu);
		menuBar.add(queryMenu);
		queryMenu.add(item1);
		queryMenu.add(item2);
		queryMenu.add(item3);
		item1.add(g1);
		item1.add(g2);
		item2.add(t1);
		item2.add(t2);
		item2.add(t3);
		item3.add(a1);
		item3.add(a2);
		item3.add(a3);
		
		
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
