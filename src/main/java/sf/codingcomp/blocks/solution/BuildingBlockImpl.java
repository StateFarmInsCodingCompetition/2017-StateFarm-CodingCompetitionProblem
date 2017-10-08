package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	
	/**
	 * The building blocks above and below this building block
	 */
	private BuildingBlockImpl above, below;

    @Override
    public Iterator<BuildingBlock> iterator() {
    		// Create a "ghost" starter block, so the first time next() is called, it will begin at this block
    		BuildingBlockImpl starter = new BuildingBlockImpl();
    		starter.above = this;
    		
    		// Create and return the Iterator
        return new Iterator<BuildingBlock>() {
        		BuildingBlockImpl current = starter;

			@Override
			public boolean hasNext() {
				return current.above != null;
			}

			@Override
			public BuildingBlock next() {
				current = current.above;
				return current;
			}}
        ;
    }

    @Override
    public void stackOver(BuildingBlock b) {
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
			implementation.above = this;
    		}
    		
    		// Prevent triangles
		if (this.below != null) {
			this.below.above = null;
		}
		
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
    			
			implementation.below = this;
    		}
    		
    		// Prevent triangles
		if (this.above != null) {
			this.above.below = null;
		}
		
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
    			if (current.equals(b)) {
    				return true;
    			}
    			current = current.above;
    		}
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
			if (current.equals(b)) {
				return true;
			}
			current = current.below;
		}
		return false;
}

}
