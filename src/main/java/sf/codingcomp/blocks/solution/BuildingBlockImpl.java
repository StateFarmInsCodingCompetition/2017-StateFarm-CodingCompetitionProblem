package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.BuildingBlockIterator;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	private BuildingBlock blockOver, blockUnder;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
    	return new BuildingBlockIterator(this);
    }

    @Override
    public void stackOver(BuildingBlock b) {
    	if(getBlockUnder() == null) {
    		setBlockUnder(b);
    		if(b != null)
    			b.stackUnder(this);
    		return;
    	}
    	if(getBlockUnder().equals(b)) return;
    	
    	else {
        	BuildingBlock under = getBlockUnder();
        	setBlockUnder(b);
        	under.stackUnder(b);
    	}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    	if(getBlockOver() == null) {
    		setBlockOver(b);
    		if(b != null)
    			b.stackOver(this);
    		return;
    	}
    	if(getBlockOver().equals(b)) return;
    	
    	else {
        	BuildingBlock over = getBlockOver();
        	setBlockOver(b);
        	over.stackOver(b);
    	}
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return getBlockUnder();
    }

    @Override
    public BuildingBlock findBlockOver() {
        return getBlockOver();
    }

	public BuildingBlock getBlockOver() {
		return blockOver;
	}

	public void setBlockOver(BuildingBlock blockOver) {
		this.blockOver = blockOver;
	}
	public BuildingBlock getBlockUnder() {
		return blockUnder;
	}

	public void setBlockUnder(BuildingBlock blockUnder) {
		this.blockUnder = blockUnder;
	}

}
