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
        List blockList = Collections.synchronizedList(new ArrayList());
        BuildingBlock tmp = this;
        while (tmp.findBlockOver() != null) { 
        	tmp = tmp.findBlockOver();
        }
        while (tmp.findBlockOver() != null) { 
        	blockList.add(tmp);
        	tmp = tmp.findBlockOver();
        }
        blockList.add(tmp);
        
        Iterator itr = blockList.iterator();
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
    	if (b.findBlockUnder() == this.findBlockOver() && this.findBlockOver() != null) { 
    		throw new CircularReferenceException();
    	}
    	blockSittingOver = b;
    	b.stackUnder(this);
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
    	if (b.findBlockOver() == this.findBlockUnder() && this.findBlockUnder() != null) { 
    		throw new CircularReferenceException();
    	}
    	blockSittingUnder = b;
    	b.stackOver(this);
    	 
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
