package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

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
    	}
    	if (((BuildingBlockImpl)b).over != null) {
    		((BuildingBlockImpl)(((BuildingBlockImpl)b).over)).under = null;
    	}
    	this.under = b;
    	((BuildingBlockImpl)b).over = this;
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
    	if (this.over != null) {
    		((BuildingBlockImpl)this.over).over = null;
    	}
    	if (((BuildingBlockImpl)b).under != null) {
    		((BuildingBlockImpl)(((BuildingBlockImpl)b).under)).over = null;
    	}
    	this.over = b;
    	((BuildingBlockImpl)b).under = this;
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
