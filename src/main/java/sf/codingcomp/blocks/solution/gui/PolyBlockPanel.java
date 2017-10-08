package sf.codingcomp.blocks.solution.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import sf.codingcomp.blocks.PolyBlock;
import sf.codingcomp.blocks.solution.StoragePolyBlockImpl;

public class PolyBlockPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int TOOL_DRAG = 0;
	private static final int TOOL_CONNECT = 1;
	private static final int TOOL_DISCONNECT = 2;
	
	private static final int FPS = 60;
	private static final int BLOCK_SIZE = 30;
	private static final Font BLOCK_ID_FONT = new Font("Arial", Font.PLAIN, 20);
	private static final Font TOOLS_HEADER = new Font("Arial", Font.PLAIN, 30);
	private static final Font TOOLS_CONT = new Font("Arial", Font.PLAIN, 20);
	
	private static class BlockInfo {
		
		private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		private static int curLetter = 0;
		
		public String id;
		public Point loc;
		
		public BlockInfo(Point loc) {
			setLoc(loc);
			
			int cur = curLetter++;
			this.id = "";
			
			int i = 1;
			while (cur >= letters.length() * i) {
				i++;
			}
			if (i > 1) {
				this.id += letters.charAt((i - 2) % letters.length());
			}
			
			this.id += letters.charAt(cur % letters.length());
		}
		
		public void setLoc(Point loc) {
			this.loc = new Point(loc.x - BLOCK_SIZE / 2, loc.y - BLOCK_SIZE / 2);
		}
	}
	
	private List<StoragePolyBlockImpl<BlockInfo>> blocks;
	
	private StoragePolyBlockImpl<BlockInfo> getBlockById(String id) {
		synchronized (blocks) {
			for (StoragePolyBlockImpl<BlockInfo> block : blocks) {
				if (block.getValue().id.equals(id)) {
					return block;
				}
			}
		}
		return null;
	}
	
	/**
	 * Mouse location
	 */
	private Point mse;
	
	private int hoverTool = -1;
	private int selectedTool = 0;
	private boolean inTools = false;
	
	private String hoverNode;
	private String selectedNode;
	private String dragNode;
	
	public PolyBlockPanel() {
		blocks = Collections.synchronizedList(new ArrayList<StoragePolyBlockImpl<BlockInfo>>());
		mse = new Point(0, 0);
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				dragNode = null;
				
				if (selectedNode != null && hoverNode != null && !selectedNode.equals(hoverNode)) {
					if (selectedTool == TOOL_CONNECT) {
						getBlockById(selectedNode).connect(getBlockById(hoverNode));						
					} else if (selectedTool == TOOL_DISCONNECT) {
						getBlockById(selectedNode).disconnect(getBlockById(hoverNode));
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//Right click
				if (e.getButton() == 3) {
					if (!inTools) {
						StoragePolyBlockImpl<BlockInfo> newBlock = new StoragePolyBlockImpl<BlockInfo>();
						newBlock.setValue(new BlockInfo(mse));
						blocks.add(newBlock);
					}
				} else if (e.getButton() == 1) {
					if (hoverTool != -1) {
						selectedTool = hoverTool;
					}
					
					selectedNode = hoverNode;
					if (selectedNode != null) {
						if (selectedTool == TOOL_DRAG) {
							dragNode = selectedNode;
						}
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mse.x = e.getX();
				mse.y = e.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mse.x = e.getX();
				mse.y = e.getY();
				
				if (dragNode != null) {
					BlockInfo inf = getBlockById(dragNode).getValue();
					inf.setLoc(mse);
				}
			}
		});
		
		//Draw thread
		new Thread(() -> {
			while (true) {
				this.repaint();
				
				try {
					Thread.sleep(1000 / FPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D)g1.create();
		
		try {
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			drawToolBox(g);

			//Draw connections
			for (StoragePolyBlockImpl<BlockInfo> block : blocks) {
				BlockInfo inf = block.getValue();
				
				g.setColor(Color.black);
				for (PolyBlock pb : block.getConnections()) {
					@SuppressWarnings("unchecked")
					StoragePolyBlockImpl<BlockInfo> conn = (StoragePolyBlockImpl<BlockInfo>)pb;
					BlockInfo connInf = conn.getValue();
					
					g.drawLine(inf.loc.x + BLOCK_SIZE / 2, inf.loc.y + BLOCK_SIZE / 2, connInf.loc.x + BLOCK_SIZE / 2, connInf.loc.y + BLOCK_SIZE / 2);
				}
			}
			
			g.setFont(BLOCK_ID_FONT);
			FontMetrics fm = g.getFontMetrics(BLOCK_ID_FONT);
			String hoverNode = null;
			for (StoragePolyBlockImpl<BlockInfo> block : blocks) {
				BlockInfo inf = block.getValue();
				
				g.setColor(Color.WHITE);
				g.fillRect(inf.loc.x, inf.loc.y, BLOCK_SIZE, BLOCK_SIZE);
				
				g.setColor(Color.black);
				if (mse.x >= inf.loc.x && mse.y >= inf.loc.y && mse.x < inf.loc.x + BLOCK_SIZE && mse.y < inf.loc.y + BLOCK_SIZE) {
					hoverNode = inf.id;
					g.setColor(Color.blue);
				}
				
				if (inf.id.equals(selectedNode)) {
					g.setColor(Color.GREEN);
				}
				
				g.drawRect(inf.loc.x, inf.loc.y, BLOCK_SIZE, BLOCK_SIZE);
				g.drawString(inf.id, inf.loc.x + (BLOCK_SIZE - fm.stringWidth(inf.id)) / 2, inf.loc.y + BLOCK_SIZE / 2 + 7);
			}
			this.hoverNode = hoverNode;
		} finally {
			g.dispose();
		}
	}
	
	private void drawToolBox(Graphics2D g1) {
		int wid = 120;
		int hei = 150;
		
		Graphics2D g = (Graphics2D) g1.create();
		
		try {
			int tx = getWidth() - wid - 6;
			g.translate(tx, 0);
			Point ms = new Point(mse.x - tx, mse.y);
			inTools = ms.x >= 0 && ms.y >= 0 && ms.x < wid && ms.y < hei;
			
			g.setColor(Color.yellow);
			g.fillRect(0, 0, wid, hei);
			
			g.setFont(TOOLS_HEADER);
			g.setColor(Color.BLACK);
			FontMetrics fm = g.getFontMetrics(TOOLS_HEADER);
			String str = "Tools";
			g.drawString(str, (wid - fm.stringWidth(str)) / 2, 30);
			
			String[] tools = {"Drag", "Connect", "Disconnect"};
			int cury = 40;
			int dy = 30;
			g.setFont(TOOLS_CONT);
			fm = g.getFontMetrics(TOOLS_CONT);
			int toolId = 0;
			int hoverTool = -1;
			for (String tool : tools) {
				g.setColor(Color.black);
				if (ms.x >= 0 && ms.y >= cury && ms.x < wid && ms.y < cury + dy) {
					g.setColor(Color.BLUE);
					hoverTool = toolId;
				}
				
				if (toolId == selectedTool) {
					g.setColor(Color.pink);
				}
				
				g.drawString(tool, (wid - fm.stringWidth(tool)) / 2, cury + 23);
				cury += dy;
				toolId++;
			}
			this.hoverTool = hoverTool;
		} finally {
			g.dispose();
		}
	}
}
