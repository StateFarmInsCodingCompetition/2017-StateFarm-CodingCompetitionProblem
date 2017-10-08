package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	/**
	 * List which holds the connections for the {@link PolyBlock}
	 */
	public List<PolyBlock> connections;

	public PolyBlockImpl() {
		this.connections = new ArrayList<PolyBlock>();
	}

	@Override
	public Iterator<PolyBlock> iterator() {
		List<PolyBlock> totalBlocks = retrieveFullList(this);
		return totalBlocks.iterator();
	}

	@Override
	public void connect(PolyBlock aPolyBlock) {
		/*
		 * Appropriately connects a PolyBlock
		 */
		if (aPolyBlock != null) {
			if (!connections.contains(aPolyBlock)) {
				connections.add(aPolyBlock);
				aPolyBlock.connect(this);
			}
		}

	}

	@Override
	public void disconnect(PolyBlock aPolyBlock) {
		connections.remove(aPolyBlock);

	}

	@Override
	public boolean contains(PolyBlock aPolyBlock) {
		return connections.contains(aPolyBlock);
	}

	@Override
	public int connections() {
		return this.connections.size();
	}

	@Override
	public int size() {
		List<PolyBlock> totalBlocks = retrieveFullList(this);
		return totalBlocks.size();
	}
	
	/**
	 * Get list of all blocks connected with block passed
	 */
	private List<PolyBlock> retrieveFullList(PolyBlock block){
		List<PolyBlock> totalBlocks = new ArrayList<PolyBlock>();	
		totalBlocks.add(block);
		addBlocksFromConnections(totalBlocks, block);
		return totalBlocks;
	}
	
	/**
	 * Recursive implementation to get blocks in a graph/network of blocks
	 */
	private void addBlocksFromConnections(List<PolyBlock> totalBlocks, PolyBlock polyBlock){
		for(PolyBlock block : ((PolyBlockImpl) polyBlock).connections){
			if(!totalBlocks.contains(block)){
				totalBlocks.add(block);
				addBlocksFromConnections(totalBlocks, block);
			}
		}
	}

	
	@Override
	public PolyBlock copy(){
		/*
		 * Rudimentary implementation of deep copy logic
		 */
		PolyBlockImpl block = new PolyBlockImpl();
		block.connections = new ArrayList<PolyBlock>(this.connections);
		return (PolyBlock) block;
	}

}
