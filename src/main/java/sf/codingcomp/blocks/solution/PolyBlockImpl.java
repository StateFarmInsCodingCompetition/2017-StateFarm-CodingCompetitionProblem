package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	private List<PolyBlock> connections = new ArrayList<PolyBlock>();
	
	/**
	 * Recursively finds all blocks connected to this block and neighbors 
	 * @param currentBlocks The list of blocks we have already found
	 * @return The same list of blocks for easy use when calling
	 */
	private List<PolyBlock> getAllBlocks(List<PolyBlock> currentBlocks) {
		//Add this block to the list since it's guaranteed to not already be in the list
		currentBlocks.add(this);
		
		//Call getAllBlocks on all neighbors that are not already added to the list
		for (PolyBlock block : connections) {
			if (!currentBlocks.contains(block)) {
				((PolyBlockImpl)block).getAllBlocks(currentBlocks);
			}
		}
		
		//Return the list
		return currentBlocks;
	}
	
	private Map<PolyBlock, PolyBlock> copyConnections(Map<PolyBlock, PolyBlock> oldToNew) {
		//When this method is called, it's guaranteed that this PolyBlock has not been connected yet
		PolyBlockImpl myClone = new PolyBlockImpl();
		oldToNew.put(this, myClone);
		
		for (PolyBlock block : connections) {
			if (!oldToNew.containsKey(block)) {
				((PolyBlockImpl)block).copyConnections(oldToNew);
			}
			
			myClone.connections.add(oldToNew.get(block));
		}
		
		return oldToNew;
	}
	
    @Override
    public Iterator<PolyBlock> iterator() {
    	List<PolyBlock> allBlocks = getAllBlocks(new ArrayList<PolyBlock>());
    	
        return new Iterator<PolyBlock>() {

        	private int curIndex = 0;
        	
			@Override
			public boolean hasNext() {
				return curIndex < allBlocks.size();
			}

			@Override
			public PolyBlock next() {
				return allBlocks.get(curIndex++);
			}
        	
        };
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
    	//Disallow connecting to self
    	if (aPolyBlock == this) {
    		return;
    	}
    	
    	//Null check
    	if (aPolyBlock == null) {
    		return;
    	}
    	
    	//Add connection to this block and to the newly connected block if not already connected
        if (!connections.contains(aPolyBlock)) {
        	connections.add(aPolyBlock);
        	((PolyBlockImpl)aPolyBlock).connections.add(this);
        }
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
    	//Cannot disconnect from null PolyBlock
    	if (aPolyBlock == null) {
    		return;
    	}
    	
    	//Remove connection from this block and the other block
        connections.remove(aPolyBlock);
        ((PolyBlockImpl)aPolyBlock).connections.remove(this);
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return connections.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return connections.size();
    }

    @Override
    public int size() {
    	//Will recursively get all the connected blocks to this block
        return getAllBlocks(new ArrayList<PolyBlock>()).size();
    }

    @Override
    public PolyBlock copy() {
    	return copyConnections(new HashMap<PolyBlock, PolyBlock>()).get(this);
    }
    
    @Override
    public boolean equals(Object other) {
    	if (!(other instanceof PolyBlockImpl)) {
    		return false;
    	}
    	
    	PolyBlockImpl block = (PolyBlockImpl) other;
    	
    	return this == block;
    }
}
