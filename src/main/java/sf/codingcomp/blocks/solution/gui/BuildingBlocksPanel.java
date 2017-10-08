package sf.codingcomp.blocks.solution.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sf.codingcomp.blocks.CircularReferenceException;
import sf.codingcomp.blocks.StorageBuildingBlock;
import sf.codingcomp.blocks.solution.StorageBuildingBlockImpl;

public class BuildingBlocksPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -7289101757793596219L;
	
	private JPanel menu;
	private JButton addBlockButton, stackOverButton, stackUnderButton, remBlockButton;
	private BuildingBlocksView view;
	
	public BuildingBlocksPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		menu = new JPanel();
		addBlockButton = new JButton("Add Building Block");
		addBlockButton.addActionListener(this);
		stackOverButton = new JButton("Stack Over a Block");
		stackOverButton.addActionListener(this);
		stackOverButton.setEnabled(false);
		stackUnderButton = new JButton("Stack Under a Block");
		stackUnderButton.addActionListener(this);
		stackUnderButton.setEnabled(false);
		remBlockButton = new JButton("Remove Block");
		remBlockButton.addActionListener(this);
		remBlockButton.setEnabled(false);
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.add(addBlockButton);
		menu.add(stackOverButton);
		menu.add(stackUnderButton);
		menu.add(remBlockButton);
		menu.setBorder(BorderFactory.createTitledBorder("Menu"));
		this.add(menu, BorderLayout.EAST);
		
		view = new BuildingBlocksView(this);
		this.add(view, BorderLayout.CENTER);
	}
	
	void setStackButtonsEnabled(boolean enabled) {
		this.stackOverButton.setEnabled(enabled && this.view.getBlocks().size() > 1);
		this.stackUnderButton.setEnabled(enabled && this.view.getBlocks().size() > 1);
		this.remBlockButton.setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addBlockButton)) {
			String name = JOptionPane.showInputDialog(this, "Name your block:", "Enter Name", JOptionPane.QUESTION_MESSAGE);
			if (name == null) {
				return;
			}
			
			StorageBuildingBlock<String> block = new StorageBuildingBlockImpl<String>(name);
			this.view.addBlock(block);
		} else if (e.getSource().equals(stackOverButton)) {
			String[] choices = view.getOtherBlockNames();
			int chosen = JOptionPane.showOptionDialog(this, "Select the block to stack on top of:", "Select Block", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			String name = choices[chosen];
			StorageBuildingBlock<String> otherBlock = view.getBlockByName(name);
			StorageBuildingBlock<String> thisBlock = view.getSelectedBlock();
			try {
				thisBlock.stackOver(otherBlock);
			} catch (CircularReferenceException e2) {
				JOptionPane.showMessageDialog(null, "That is an illegal circular action", "Illegal Action", JOptionPane.ERROR_MESSAGE);
			}
			view.repaint();
		} else if (e.getSource().equals(remBlockButton)) {
			this.view.removeSelected();
		}
	}

}
