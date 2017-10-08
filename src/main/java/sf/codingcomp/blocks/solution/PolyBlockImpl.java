package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	/**
	 * The connections to/from this poly block
	 */
	private volatile List<PolyBlock> connections = new ArrayList<PolyBlock>();
	
	/**
	 * Performs the contains method using the '==' comparison operator rather than 'equals()'
	 * @param blocks The blocks to search through
	 * @param block The block to are searching for
	 * @return Whether block exists within blocks (using the '==' comparison operator)
	 */
	private static boolean surfaceContains(Collection<PolyBlock> blocks, PolyBlock block) {
		for (PolyBlock blk : blocks) {
			if (blk == block) {
				return true;
			}
		}
		return false;
	}
	
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
			if (!surfaceContains(currentBlocks, block)) {
				((PolyBlockImpl)block).getAllBlocks(currentBlocks);
			}
		}
		
		//Return the list
		return currentBlocks;
	}
	
	/**
	 * Recursively creates a copy of all connections to this node (used for the 'copy()' method)
	 * @param oldToNew The current mapping of copied nodes (used for the recursive process)
	 * @return A mapping of the connections in the current graph to their replicas in the copied graph
	 */
	private Map<PolyBlock, PolyBlock> copyConnections(Map<PolyBlock, PolyBlock> oldToNew) {
		//When this method is called, it's guaranteed that this PolyBlock has not been connected yet
		PolyBlockImpl myClone = new PolyBlockImpl();
		oldToNew.put(this, myClone);
		
		// Copy each of this nodes connections, and connect the copies to the new clone
		for (PolyBlock block : connections) {
			if (!surfaceContains(new ArrayList<PolyBlock>(oldToNew.keySet()), block)) {
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

        		/**
        		 * The current index of the iterator
        		 */
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
	    	// Disallow connecting to self
	    	if (aPolyBlock == this) {
	    		return;
	    	}
	    	
	    	// Disallow connecting to a null block
	    	if (aPolyBlock == null) {
	    		return;
	    	}
	    	
	    	// Add connection to this block and to the newly connected block if not already connected
        if (!contains(aPolyBlock)) {
        		connections.add(aPolyBlock);
        		((PolyBlockImpl)aPolyBlock).connections.add(this);
        }
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
	    	// Cannot disconnect from null PolyBlock
	    	if (aPolyBlock == null) {
	    		return;
	    	}
	    	
	    	// Remove connection from this block and the other block
        connections.remove(aPolyBlock);
        ((PolyBlockImpl)aPolyBlock).connections.remove(this);
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return surfaceContains(connections, aPolyBlock);
    }

    @Override
    public int connections() {
        return connections.size();
    }

    @Override
    public int size() {
    		// Will recursively get all the connected blocks to this block and return the number of total blocks
        return getAllBlocks(new ArrayList<PolyBlock>()).size();
    }

    @Override
    public PolyBlock copy() {
    		// Run the recursive 'copyConnections()' method, and then find the clone of this block
    		return copyConnections(new HashMap<PolyBlock, PolyBlock>()).get(this);
    }
    
    @Override
    public boolean equals(Object other) {
	    	if (!(other instanceof PolyBlockImpl)) {
	    		// Ensure the other object is a Poly Block
	    		return false;
	    	}
	    	
	    	PolyBlockImpl block = (PolyBlockImpl) other;
	    	
	    	List<PolyBlock> graphA = getAllBlocks(new ArrayList<PolyBlock>());
	    	List<PolyBlock> graphB = block.getAllBlocks(new ArrayList<PolyBlock>());
	    	
	    	// Compare graph sizes
	    	if (graphA.size() != graphB.size()) {
	    		return false;
	    	}
	    	
	    	// Create arrays representing a list of the number of connections from each node
	    	int[] sizesA = new int[graphA.size()];
	    	int[] sizesB = new int[graphB.size()];
	    	for (int i = 0; i < graphA.size(); i++) {
	    		sizesA[i] = ((PolyBlockImpl)graphA.get(i)).connections();
	    		sizesB[i] = ((PolyBlockImpl)graphB.get(i)).connections();
	    	}
	    	
	    	// Compare the sorted number of connections to determine graph equality
	    	Arrays.sort(sizesA);
	    	Arrays.sort(sizesB);
	    	return Arrays.equals(sizesA, sizesB);
    }
    
    public List<PolyBlock> getConnections() {
    	return connections;
    }
}
