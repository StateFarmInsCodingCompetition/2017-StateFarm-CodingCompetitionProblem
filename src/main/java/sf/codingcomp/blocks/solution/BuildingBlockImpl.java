package sf.codingcomp.blocks.solution;

import java.util.Arrays;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	BuildingBlockImpl above = null;
	BuildingBlockImpl below = null;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
    		return new Iterator<BuildingBlock>() {
	    			@Override
	    			public BuildingBlock next() {
	    				return (BuildingBlock) below.iterator();
	    			}

				@Override
				public boolean hasNext() {
					if (below != null) {
						return true;
					}
					return false; 
				}
    		};
    }

    @Override
    public void stackOver(BuildingBlock b) {
    		if (b == null) {
    			this.below.above = null;
    			this.below = null;
    			return;
    		}
    		
    		BuildingBlockImpl bi = (BuildingBlockImpl) b;
    		
    		if (this.below != null) {
    			this.below.above = null;
    		}
    		
    		if (bi.above != null) {
    			bi.above.below = null;
    		}
    		bi.above = this;
    		this.below = bi;
        
    		// Test for Circular Reference
    		BuildingBlockImpl tempiter1 = this;
    		BuildingBlockImpl tempiter2 = this;
    		while (tempiter1 != null && tempiter2 != null && tempiter2.below != null) {
    			tempiter1 = tempiter1.below;
    			tempiter2 = tempiter2.below.below;
    			
    			if (tempiter1 == tempiter2) {
    				this.below = null;
    				bi.above = null;
    				throw new CircularReferenceException();
    			}
    		}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    		if (b == null) {
    			this.above.below = null;
    			this.above = null;
    			return;
    		}
    		
		BuildingBlockImpl bi = (BuildingBlockImpl) b;
    		if (this.above != null) {
    			this.above.below = null;
    		}
    		
    		if (bi.below != null) {
    			bi.below.above = null;
    		}
    		bi.below = this;
    		this.above = bi;
    		
    		// Test for Circular Reference
    		BuildingBlockImpl tempiter1 = this;
    		BuildingBlockImpl tempiter2 = this;
    		while (tempiter1 != null && tempiter2 != null && tempiter2.above != null) {
    			tempiter1 = tempiter1.above;
    			tempiter2 = tempiter2.above.above;
    			
    			if (tempiter1 == tempiter2) {
    				this.above = null;
    				bi.below = null;
    				throw new CircularReferenceException();
    			}
    		}
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return below;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return above;
    }

}
