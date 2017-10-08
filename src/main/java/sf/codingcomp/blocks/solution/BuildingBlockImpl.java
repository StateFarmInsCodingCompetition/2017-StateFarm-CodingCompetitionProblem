package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	private BuildingBlock blockUnder;
	
	private BuildingBlock blockOver;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        List<BuildingBlock> list = new LinkedList<BuildingBlock>();

        /*
         * Add blocks above
         */
        BuildingBlock targetBlock = this;
        while(targetBlock != null){
        	list.add(targetBlock);
        	targetBlock = targetBlock.findBlockOver();
        }
        
        /*
         * Add blocks below
         */
        targetBlock = this.blockUnder;
        while(targetBlock != null){
        	list.add(0, targetBlock);
        	targetBlock = targetBlock.findBlockUnder();
        }
        
        // Return list iterator
        return list.iterator();
    }

    @Override
    public void stackOver(BuildingBlock b) {
    	/*
    	 * Check for circular block stacks
    	 */
    	BuildingBlock targetBlock = this;
    	while(targetBlock != null){
    		if(targetBlock == b ){
    			throw new CircularReferenceException();
    		}
    		targetBlock = targetBlock.findBlockOver();
    	}
    	
        this.setBlockUnder(b);
        b.setBlockOver(this);
        
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    	/*
    	 * Check for circular block stacks
    	 */
    	BuildingBlock targetBlock = this;
    	while(targetBlock != null){
    		if(targetBlock == b ){
    			throw new CircularReferenceException();
    		}
    		targetBlock = targetBlock.findBlockUnder();
    	}
    	
        this.setBlockOver(b);
        /*
         * If the block under already has something over, nullify it.
         */
        if(b.findBlockUnder() != null && b.findBlockUnder() != this){
        	b.findBlockUnder().setBlockOver(null);
        }
        b.setBlockUnder((BuildingBlock) this);
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return this.blockUnder;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return this.blockOver;
    }

	public void setBlockOver(BuildingBlock b) {
		this.blockOver = b;
	}

	public void setBlockUnder(BuildingBlock b) {
		this.blockUnder = b;
	}

	
}
