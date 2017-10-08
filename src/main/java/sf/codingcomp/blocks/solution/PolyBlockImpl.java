package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	private HashMap<Integer, PolyBlock> connections;

	public PolyBlockImpl() {
		this.connections = new HashMap<Integer, PolyBlock>();
	}

	@Override
	public Iterator<PolyBlock> iterator() {
		return this.collectBlocks(new ArrayList<PolyBlock>()).iterator();
	}

	@Override
	public void connect(PolyBlock aPolyBlock) {
		if (null != aPolyBlock && this != aPolyBlock) {

			if (!this.connections.containsKey(aPolyBlock.hashCode())) {
				this.connections.put(aPolyBlock.hashCode(), aPolyBlock);
				((PolyBlockImpl)aPolyBlock).connections.put(this.hashCode(), this);
			}
		}
	}

	@Override
	public void disconnect(PolyBlock aPolyBlock) {
		if (null != aPolyBlock) {
			this.connections.remove(aPolyBlock.hashCode());
			((PolyBlockImpl)aPolyBlock).connections.remove(this.hashCode());
		}
	}

	@Override
	public boolean contains(PolyBlock aPolyBlock) {
		return connections.containsKey(aPolyBlock.hashCode());
	}

	@Override
	public int connections() {
		return connections.size();
	}

	@Override
	public int size() {
		return this.collectBlocks(new ArrayList<PolyBlock>()).size();
	}

	@Override
	public PolyBlock copy() {
		return null;
	}

	/**
	 * DFS over block network
	 * 
	 * @param visited A collection of all blocks currently found
	 * @return All blocks in the network
	 */
	private ArrayList<PolyBlock> collectBlocks(ArrayList<PolyBlock> visited) {
		visited.add(this);
		for (PolyBlock block : this.connections.values()) {
			if (!visited.contains(block)) {
				((PolyBlockImpl) block).collectBlocks(visited);
			}
		}
		return visited;
	}
	
	

	
}
