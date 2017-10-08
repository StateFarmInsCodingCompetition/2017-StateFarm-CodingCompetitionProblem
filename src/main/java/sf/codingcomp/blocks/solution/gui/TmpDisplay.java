package sf.codingcomp.blocks.solution.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TmpDisplay {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tmp Display");
		frame.setSize(1000, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new PolyBlockPanel();
		panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.add(panel);
		frame.setVisible(true);
		System.out.println("PANEL: " + panel.getWidth());
		
		
	}
	
}
