package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	public BuildingBlock over, under;
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
    	return new BBIterator(this);
    }

    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
    	
    	if (this.under != null) {
    		((BuildingBlockImpl)this.under).over = null;
    		this.under = null;
    	}
    	if (b != null && ((BuildingBlockImpl)b).over != null) {
    		((BuildingBlockImpl)(((BuildingBlockImpl)b).over)).under = null;
    		((BuildingBlockImpl)b).over = null;
    	}
    	BuildingBlock x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockOver();
    	}
    	x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockUnder();
    	}
    	this.under = b;
    	if (b != null) {
    		((BuildingBlockImpl)b).over = this;
    	}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
    	
    	if (this.over != null) {
    		((BuildingBlockImpl)this.over).under = null;
    		this.over = null;
    	}
    	if (b != null && ((BuildingBlockImpl)b).under != null) {
    		((BuildingBlockImpl)(((BuildingBlockImpl)b).under)).over = null;
    		((BuildingBlockImpl)b).under = null;
    	}
    	BuildingBlock x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockOver();
    	}
    	x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockUnder();
    	}
    	this.over = b;
    	if (b != null) {
    		((BuildingBlockImpl)b).under = this;
    	}
    }

    @Override
    public BuildingBlock findBlockUnder() {
        // TODO Auto-generated method stub
        return this.under;
    }

    @Override
    public BuildingBlock findBlockOver() {
        // TODO Auto-generated method stub
        return this.over;
    }

}
