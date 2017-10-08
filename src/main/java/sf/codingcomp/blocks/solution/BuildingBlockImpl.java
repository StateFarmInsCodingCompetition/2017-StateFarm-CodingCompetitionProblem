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
    		if (b == null) {
    			if (below != null) {
    				BuildingBlock temp = below;
    				below = null;
    				temp.stackUnder(null);
    				return;
    			}
    			below = null;
    			return;
    		}
    		if (this.findBlockUnder() != null) {
    			this.findBlockUnder().stackUnder(null);
    		}
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
    		if (b == null) {
    			if (above != null) {
    				BuildingBlock temp = above;
    				above = null;
    				temp.stackOver(null);
    				return;
    			}
    			above = null;
    			return;
    		}
    		if (b.findBlockUnder() != null) {
    			b.findBlockUnder().stackUnder(null);
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
