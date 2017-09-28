import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame {
	
	private Vis mainPanel;

	public Main() {
		JMenuBar mb = setupMenu();
		setJMenuBar(mb);
		mainPanel = new Vis();
		setContentPane(mainPanel);
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Hello World	");
		setVisible(true);
	}
	
	private JMenuBar setupMenu() {
		//instantiate menubar, menus, and menu options
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Option");
		JMenu subMenu = new JMenu("Moving castle");
		JMenuItem item1 = new JMenuItem("Move castle Down");
		JMenuItem item2 = new JMenuItem("Move castle Up");
		JMenuItem item3 = new JMenuItem("Move Plan");
		
		//setup action listeners example of anonymous class
		item1.addActionListener((ActionEvent e) -> {//using lambda syntax
			Vis.x += 20;
			repaint();
		});
		item2.addActionListener(e -> {//even shorter with just variable 	
			Vis.x -= 20;
			repaint();
		});
		
		subMenu.add(item1);
		subMenu.add(item2);
		fileMenu.add(subMenu);
		fileMenu.add(item3);
		menuBar.add(fileMenu);
		
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
