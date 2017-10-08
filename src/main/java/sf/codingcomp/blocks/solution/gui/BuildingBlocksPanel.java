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
	
	/**
	 * The right-hand menu
	 */
	private JPanel menu;
	
	/**
	 * The menu buttons
	 */
	private JButton addBlockButton, stackOverButton, stackUnderButton, remBlockButton;
	
	/**
	 * The view displaying our blocks
	 */
	private BuildingBlocksView view;
	
	public BuildingBlocksPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		menu = new JPanel();
		
		// Create the four menu buttons
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
		
		// Add the four menu buttons to the menu
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
	
	/**
	 * Set whether the menu buttons are enabled or disabled
	 * @param enabled Whether the buttons should be enabled
	 */
	public void setMenuButtonsEnabled(boolean enabled) {
		// Only enable the stack buttons if more than two blocks exist
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
		} else if (e.getSource().equals(stackOverButton) || e.getSource().equals(stackOverButton)) {
			// Select another block to stack with
			String[] choices = view.getOtherBlockNames();
			int chosen = JOptionPane.showOptionDialog(this, "Select the block to stack on top of/below of:", "Select Block", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			if (chosen == -1) {
				return;
			}
			String name = choices[chosen];
			
			// Find the two chosen blocks
			StorageBuildingBlock<String> otherBlock = view.getBlockByName(name);
			StorageBuildingBlock<String> thisBlock = view.getSelectedBlock();
			
			try {
				if (e.getSource().equals(stackOverButton)) {
					thisBlock.stackOver(otherBlock);
				} else {
					thisBlock.stackUnder(otherBlock);
				}
			} catch (CircularReferenceException e2) {
				// Catch our circular reference exceptions
				JOptionPane.showMessageDialog(null, "That is an illegal circular action", "Illegal Action", JOptionPane.ERROR_MESSAGE);
			}
			view.repaint();
		} else if (e.getSource().equals(remBlockButton)) {
			this.view.removeSelected();
		}
	}

}
