package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {
	
	BuildingBlock above;
	BuildingBlock below;
	public BuildingBlockImpl(){
		above = null;
		below = null;
		
	}
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
    	BuildingBlock temp = null;
    	if(below != null){
    		temp = below;
    	}
    	if(below != b){
    		below = b;
    		if(b != null)
    			b.stackUnder(this);
    		if(temp != null)
    			temp.stackUnder(null);
    	}
        
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
    	BuildingBlock temp = null;
    	if(above != null){
    		temp = above;
    	}
    	
    	if(above != b){
    		above = b;
    		if(b != null)
    			b.stackOver(this);
    		if(temp != null)
    			temp.stackOver(null);
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
