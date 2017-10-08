package sf.codingcomp.blocks.solution;

import java.util.Arrays;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

	public BuildingBlock above = null;
	public BuildingBlock below = null;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
    		return this.iterator();
    }

    @Override
    public void stackOver(BuildingBlock b) {
    		if (this.findBlockUnder() == b) {
			return;
		}
//	    	if (b == null) {
//			if (this.findBlockUnder() != null) {
//				this.below.stackUnder(null);
//			}
//			below = null;
//			above = null;
//		} else {
//	    		if (below == null) {
//				below = b;
//		        b.stackOver(this);
//			} else {
//				below.stackOver(null);
//				below = b;
//		        b.stackOver(this);
//			}
//		}
    	
    		below = b;
    		if (b != null) {
    			below.stackUnder(this);
    		}
        
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    		if (this.findBlockOver() == b) {
    			return;
    		}
//	    	if (b == null) {
//				above = null;
//				below = null;
//		} else {
//	    		if (above == null) {
//	    			this.above = b;
//	    	        b.stackOver(this);
//	    		} else {
//	    			this.above.stackOver(null);
//	    			this.above = b;
//	    	        b.stackOver(this);
//	    		}
//		}
    		if (b == null) {
    			above = null;
    			return;
    		}
    		if (above != null && above.findBlockUnder() != null) {
    			above.findBlockUnder().stackUnder(null);
    		}
    		above = b;
    		if (b != null) {
    			above.stackOver(this);
    		}
    }

    @Override
    public BuildingBlock findBlockUnder() {
        // TODO Auto-generated method stub
        return below;
    }

    @Override
    public BuildingBlock findBlockOver() {
        // TODO Auto-generated method stub
        return above;
    }

}
