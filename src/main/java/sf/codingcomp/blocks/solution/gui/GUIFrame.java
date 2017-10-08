package sf.codingcomp.blocks.solution.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUIFrame extends JFrame {
	private static final long serialVersionUID = -3022953366969549356L;
	
	private JTabbedPane tabbedPane;
	private JPanel leftPanel, rightPanel;

	public GUIFrame() {
		super("Block Manipulator");
		this.setBounds(100, 100, 1000, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void setupWithTabs(JPanel leftPanel, JPanel rightPanel) {
		if (leftPanel == null || rightPanel == null) {
			throw new IllegalArgumentException("Panels may not be null");
		}
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		tabbedPane.add("Building Blocks", this.leftPanel);
		tabbedPane.add("Poly Blocks", this.rightPanel);
	}
}
