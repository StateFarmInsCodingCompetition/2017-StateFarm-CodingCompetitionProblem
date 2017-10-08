package sf.codingcomp.blocks.solution.gui;

public class MainGUI {
	
	public static void main(String[] args) {
		GUIFrame frame = new GUIFrame();
		frame.setupWithTabs(new BuildingBlocksPanel(), new PolyBlockPanel());
		frame.setVisible(true);
	}
	
}
