package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	
	//Block over this block
	protected BuildingBlock blockSittingOver = null;
	//Block under this block
	protected BuildingBlock blockSittingUnder = null;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        Iterator itr = new BuildingBlockIterator(this);
        return itr;
    }

    //Puts this over object passed
    @Override
    public void stackOver(BuildingBlock b) {
    	if (blockSittingUnder == b) { 
    		return;
    	}
    	if (blockSittingUnder != null && b == null) { 
    		if(blockSittingOver == null) {
    			blockSittingUnder.stackUnder(null);
    		}
    		else { 
    			blockSittingUnder.stackUnder(blockSittingOver);
    		}
    	}
    	if (b != null) { 
    		if (b.findBlockUnder() == this.findBlockOver() && this.findBlockOver() != null) { 
    			throw new CircularReferenceException();
    		}
    	}
    	blockSittingUnder = b;
    	if (b != null) { 
    		b.stackUnder(this);
    	}
    }

    //Puts this under object passed
    @Override
    public void stackUnder(BuildingBlock b) {
    	if (blockSittingOver == b) { 
    		return;
    	}
    	if (blockSittingOver != null && b == null) { 
    		if (blockSittingUnder == null) { 
    			blockSittingOver.stackOver(null);
    		}
    		else { 
    			blockSittingOver.stackOver(blockSittingUnder);
    		}
    	}
    	if (b != null) { 
    		if (b.findBlockOver() == this.findBlockUnder() && this.findBlockUnder() != null) { 
    			throw new CircularReferenceException();
    		}
    	}
    	blockSittingOver = b;
    	if (b != null) { 
    		b.stackOver(this);
    	}
    }

    
    //returns block under this one
    @Override
    public BuildingBlock findBlockUnder() {
        return blockSittingUnder;
    }

    //returns block over this one
    @Override
    public BuildingBlock findBlockOver() {
        return blockSittingOver;
    }

}

class BuildingBlockIterator implements Iterator {
	
	private BuildingBlock current = null;
	private int mod = 0;
	private boolean nextCalled = false;
	
	BuildingBlockIterator(BuildingBlock b) { 
		BuildingBlock tmp = b;
		while (tmp.findBlockUnder() != null)
			tmp = tmp.findBlockUnder();
		current = tmp;
	}

	@Override
	public boolean hasNext() {
		if (current != null)  
			return true;
		else 
		return false;
	}

	@Override
	public BuildingBlock next() {
		BuildingBlock previous = current;
		current = current.findBlockOver();
		nextCalled = true;
		return previous;
	}
	
	public void remove() { 
		if (!nextCalled) { 
			throw new IllegalStateException();
		}
		BuildingBlock next = current.findBlockOver();
		current.stackOver(null);
		current.stackUnder(null);
		current = next;
		mod++;
		nextCalled = false;
		
	}
	
	
}
