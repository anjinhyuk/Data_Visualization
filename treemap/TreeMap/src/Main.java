import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;

public class Main extends JFrame{
	
	private Vis mainPanel;
	public Node root;
	//public static double FILESIZE;
	private String colorScheme;
	
	public Main() {
		mainPanel = new Vis();
		setContentPane(mainPanel);
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		int mbH = mb.getHeight();
		mainPanel.setTopHeight(mbH);
		File directory = new File(System.getProperty("user.dir"));
		root = new Node(directory);
		colorScheme = "type";
		root.setColorScheme(colorScheme);
		mainPanel.setRoot(root);		
		
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tree Map for " + directory.getName());
		setVisible(true);
	}

	private JMenuBar setupMenu() {
		// TODO Auto-generated method stub
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem f1 = new JMenuItem("Choose Folder");
		JMenu colorMenu = new JMenu("Color by");
		JMenuItem f2 = new JMenuItem("File types");
		JMenuItem f3 = new JMenuItem("Data last modified");
		JMenuItem f4 = new JMenuItem("Permissions");
		JMenuItem f5 = new JMenuItem("White");
		
		//choosing directory
		f1.addActionListener((ActionEvent e) -> {
			JFileChooser c = new JFileChooser();
			c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnedVal = c.showOpenDialog(null);
			if(returnedVal == JFileChooser.APPROVE_OPTION) {
				root = new Node (c.getSelectedFile());
				root.setColorScheme(colorScheme);
				mainPanel.setRoot(root);
				repaint();
			}
		});
		f2.addActionListener((ActionEvent e) -> {
			System.out.println("Just clicked type");
			colorScheme = "type";
			root.setColorScheme(colorScheme);
			repaint();
		});
		f3.addActionListener((ActionEvent e) -> {
			System.out.println("Just clicked age");
			colorScheme = "age";
			root.setColorScheme(colorScheme);
			repaint();
		});
		f4.addActionListener((ActionEvent e) -> {
			System.out.println("Just clicked permission");
			colorScheme = "permissions";
			root.setColorScheme(colorScheme);
			repaint();
		});
		f5.addActionListener((ActionEvent e) -> {
			System.out.println("Just clicked white");
			colorScheme = "white";
			root.setColorScheme(colorScheme);
			repaint();
		});
		
		menuBar.add(fileMenu);
		fileMenu.add(f1);
		fileMenu.add(colorMenu);
		colorMenu.add(f2);
		colorMenu.add(f3);
		colorMenu.add(f4);
		colorMenu.add(f5);
		
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
