package sf.codingcomp.blocks.solution.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.StorageBuildingBlock;
import sf.codingcomp.blocks.solution.BuildingBlockImpl;

public class BuildingBlocksView extends JPanel implements MouseListener {
	private static final long serialVersionUID = -601579726643886184L;
	
	private HashMap<StorageBuildingBlock<String>, Rectangle> blocks;
	private BuildingBlocksPanel panel;
	private StorageBuildingBlock<String> selected;
	
	public BuildingBlocksView(BuildingBlocksPanel panel) {
		blocks = new HashMap<StorageBuildingBlock<String>, Rectangle>();
		this.panel = panel;
		this.addMouseListener(this);
	}
	
	public void addBlock(StorageBuildingBlock<String> b) {
		blocks.put(b, new Rectangle(0, 0, 0, 0));
		this.repaint();
	}
	
	public HashMap<StorageBuildingBlock<String>, Rectangle> getBlocks() {
		return blocks;
	}
	
	public String[] getOtherBlockNames() {
		if (selected == null) {
			return new String[0];
		}
		String[] names = new String[blocks.size() - 1];
		int i = 0;
		for (StorageBuildingBlock<String> block : blocks.keySet()) {
			if (block.equals(selected)) {
				continue;
			}
			names[i] = block.getValue();
			i++;
		}
		return names;
	}
	
	public StorageBuildingBlock<String> getBlockByName(String name) {
		for (StorageBuildingBlock<String> block : blocks.keySet()) {
			if (block.getValue().equals(name)) {
				return block;
			}
		}
		return null;
	}
	
	public StorageBuildingBlock<String> getSelectedBlock() {
		return selected;
	}
	
	public void removeSelected() {
		BuildingBlockImpl impl = (BuildingBlockImpl)selected;
		if (impl.findBlockOver() != null) {
			impl.findBlockOver().stackOver(null);
		}
		if (impl.findBlockUnder() != null) {
			impl.findBlockUnder().stackUnder(impl.findBlockOver());
		}
		this.blocks.remove(selected);
		this.deselectBlock();
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		LinkedList<StorageBuildingBlock<String>> drawnBlocks = new LinkedList<StorageBuildingBlock<String>>();
		int currentX = 10;
		for (StorageBuildingBlock<String> block : blocks.keySet()) {
			if (drawnBlocks.contains(block)) {
				continue;
			}
			
			Iterator<BuildingBlock> iter = block.iterator();
			int currentY = this.getHeight() - 10;
			while (iter.hasNext()) {
				@SuppressWarnings("unchecked")
				StorageBuildingBlock<String> b2 = (StorageBuildingBlock<String>)iter.next();
				drawnBlocks.add(b2);
				g.setColor(Color.GREEN);
				Rectangle rect = new Rectangle(currentX, currentY - 50, 50, 50);
				g.fillRect(rect.x, rect.y, rect.width, rect.height);
				if (b2.equals(selected)) {
					g.setColor(Color.RED);
					g.drawRect(rect.x, rect.y, rect.width, rect.height);
				}
				blocks.put(b2, rect);
				g.setColor(Color.WHITE);
				g.drawString(b2.getValue(), currentX + 5, currentY - 30);
				
				currentY -= 60;
			}
			currentX += 60;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		for (StorageBuildingBlock<String> block : blocks.keySet()) {
			if (blocks.get(block).contains(p)) {
				selected = block;
				panel.setStackButtonsEnabled(true);
				this.repaint();
				return;
			}
		}
		deselectBlock();
		this.repaint();
	}
	
	public void deselectBlock() {
		selected = null;
		panel.setStackButtonsEnabled(false);
	}

	// Unneeded methods
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
