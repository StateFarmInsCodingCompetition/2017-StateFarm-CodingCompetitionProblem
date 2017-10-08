package sf.codingcomp.blocks.solution;

import java.util.Arrays;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

	BuildingBlock above = null;
	BuildingBlock below = null;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
//        return findBlockOver().findBlockUnder();
    		return this.iterator();
    }

    @Override
    public void stackOver(BuildingBlock b) {
    		if (above == null) {
    			this.above = b;
    	        b.stackUnder(this);
    		} else {
    			this.above.stackUnder(null);
    			this.above = b;
    	        b.stackUnder(this);
    		}
        
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    		if (below == null) {
			this.below = b;
	        b.stackOver(this);
		} else {
			this.below.stackOver(null);
			this.below = b;
	        b.stackOver(this);
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
