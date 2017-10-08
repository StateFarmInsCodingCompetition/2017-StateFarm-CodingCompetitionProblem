package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	
	//Block this block is Underneath
	protected BuildingBlock blockSittingUnder = null;
	//Block this block is Over
	protected BuildingBlock blockSittingOver = null;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        Iterator itr = new BuildingBlockIterator(this);
        return itr;
    }

    //Puts this over object passed
    @Override
    public void stackOver(BuildingBlock b) {
    	if (blockSittingOver == b) { 
    		return;
    	}
    	if (blockSittingOver != null && b == null) { 
    		if(blockSittingUnder == null) {
    			blockSittingOver.stackUnder(null);
    		}
    		else { 
    			blockSittingOver.stackUnder(blockSittingUnder);
    		}
    	}
    	if (b != null) { 
    		if (b.findBlockUnder() == this.findBlockOver() && this.findBlockOver() != null) { 
    			throw new CircularReferenceException();
    		}
    	}
    	blockSittingOver = b;
    	if (b != null) { 
    		b.stackUnder(this);
    	}
    }

    //Puts this under object passed
    @Override
    public void stackUnder(BuildingBlock b) {
    	if (blockSittingUnder == b) { 
    		return;
    	}
    	if (blockSittingUnder != null && b == null) { 
    		if (blockSittingOver == null) { 
    			blockSittingUnder.stackOver(null);
    		}
    		else { 
    			blockSittingUnder.stackOver(blockSittingOver);
    		}
    	}
    	if (b != null) { 
    		if (b.findBlockOver() == this.findBlockUnder() && this.findBlockUnder() != null) { 
    			throw new CircularReferenceException();
    		}
    	}
    	blockSittingUnder = b;
    	if (b != null) { 
    		b.stackOver(this);
    	}
    }

    
    //returns block under this one
    @Override
    public BuildingBlock findBlockUnder() {
        return blockSittingOver;
    }

    //returns block over this one
    @Override
    public BuildingBlock findBlockOver() {
        return blockSittingUnder;
    }

}

class BuildingBlockIterator implements Iterator {
	
	private BuildingBlock current = null;
	
	BuildingBlockIterator(BuildingBlock b) { 
		BuildingBlock tmp = b;
		while (tmp.findBlockUnder() != null)
			tmp = tmp.findBlockUnder();
		current = tmp;
	}

	@Override
	public boolean hasNext() {
		if (current.findBlockOver() == null)  
			return false;
		return true;
	}

	@Override
	public BuildingBlock next() {
		BuildingBlock previous = current;
		current = current.findBlockOver();
		return previous;
	}
	
	public void remove() { 

		BuildingBlock tmp = current;
		current = current.findBlockOver();
		tmp.stackOver(null);
		tmp.stackUnder(null);
		
	}
	
	
}
