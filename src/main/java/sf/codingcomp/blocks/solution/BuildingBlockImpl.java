package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	
	/**
	 * The building block above this building block
	 */
	private BuildingBlockImpl above;
	
	/**
	 * The building block below this building block
	 */
	private BuildingBlockImpl below;

    @Override
    public Iterator<BuildingBlock> iterator() {
    		// Create a "ghost" starter block, so the first time next() is called, it will begin at this block
    		BuildingBlockImpl starter = new BuildingBlockImpl();
    		starter.above = this.getBottomOfStack();
    		
    		// Create and return the Iterator
        return new Iterator<BuildingBlock>() {
        		/**
        		 * Whether or not the current block has been removed (preventing double removes)
        		 */
        		boolean hasRemoved = false;
        		
        		/**
        		 * The current block the iterator is pointing to
        		 */
        		BuildingBlockImpl current = starter;
        		
        		/**
        		 * The next block it will go to (or null)
        		 */
        		BuildingBlockImpl next = current.above;

			@Override
			public boolean hasNext() {
				return next != null;
			}

			@Override
			public BuildingBlock next() {
				current = next;
				next = current.above;
				
				// Update the hasRemoved flag since we are pointing to a new block now
				hasRemoved = false;
				
				return current;
			}
			
			@Override
			public void remove() {
				// If next has not been called (current == starter) or the current block has already been removed
				if (current.equals(starter) || hasRemoved) {
					throw new IllegalStateException();
				}
				
				// Update the above/below pointers of this block and its surrounding blocks
				BuildingBlockImpl oldBelow = current.below, oldAbove = current.above;
				oldBelow.above = oldAbove;
				oldAbove.below = oldBelow;
				current.above = null;
				current.below = null;
				
				// Set the hasRemoved flag to true to prevent double removes
				hasRemoved = true;
			}
        };
    }

    @Override
    public void stackOver(BuildingBlock b) {
    		// Cast b to our implementation, since we know we will not be mixing implementations
        BuildingBlockImpl implementation = (BuildingBlockImpl)b;
        
    		if (b != null) {
    			// If b already exists below this block in the stack, throw an exception
    			if (this.aboveLineContains(b)) {
    				throw new CircularReferenceException();
    			}
    			
    			// Prevent triangles
    			if (implementation.above != null) {
    				implementation.above.below = null;
    			}
    			
    			// Update the passed block's pointer
			implementation.above = this;
    		}
    		
    		// Prevent triangles
		if (this.below != null) {
			this.below.above = null;
		}
		
		// Update this block's pointer
		this.below = implementation;
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        BuildingBlockImpl implementation = (BuildingBlockImpl)b;
        
    		if (b != null) {
    			// If b already exists below this block in the stack, throw an exception
    			if (this.belowLineContains(b)) {
    				throw new CircularReferenceException();
    			}
    			
    			// Prevent triangles
    			if (implementation.below != null) {
    				implementation.below.above = null;
    			}
    			
    			// Update the passed block's pointer
			implementation.below = this;
    		}
    		
    		// Prevent triangles
		if (this.above != null) {
			this.above.below = null;
		}
		
		// Update this block's pointer
		this.above = implementation;
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return below;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return above;
    }
    
    /**
     * Check to see whether block b already exists above this block
     * @param b The block to check for
     * @return Whether b exists above this block
     */
    private boolean aboveLineContains(BuildingBlock b) {
    		BuildingBlockImpl current = above;
    		while (current != null) {
    			// If we find block b, return immediately
    			if (current.equals(b)) {
    				return true;
    			}
    			current = current.above;
    		}
    		
    		// If we reach the top of the stack, return false
    		return false;
    }
    
    /**
     * Check to see whether block b already exists below this block
     * @param b The block to check for
     * @return Whether b exists below this block
     */
    private boolean belowLineContains(BuildingBlock b) {
		BuildingBlockImpl current = below;
		while (current != null) {
			// If we find block b, return immediately
			if (current.equals(b)) {
				return true;
			}
			current = current.below;
		}
		
		// If we reach the bottom of the stack, return false
		return false;
    }
    
    /**
     * Finds the bottom of the stack from this block
     * @return The bottom of the stack
     */
    private BuildingBlockImpl getBottomOfStack() {
		BuildingBlockImpl current = this;
		while (current.below != null) {
			// Iterate until there is no block below the current block
			current = current.below;
		}
		return current;
    }

}
