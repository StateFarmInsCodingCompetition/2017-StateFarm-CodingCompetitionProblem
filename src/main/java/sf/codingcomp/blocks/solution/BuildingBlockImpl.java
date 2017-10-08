package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {
	
	protected BuildingBlock above = null;
	protected BuildingBlock below = null;
	private BuildingBlock me = this;
	public void setBelow(BuildingBlock b) {
		below = b;
	}


    @Override
    public Iterator<BuildingBlock> iterator() {
        Iterator<BuildingBlock> it = new Iterator<BuildingBlock>(){
        	//private int cIndex = 0;

			@Override
			public boolean hasNext() {
				
				return (below == null);
			}

			@Override
			public BuildingBlock next() {
				if(hasNext()) {
					return below;
				}else {
					return me;
				}
			}
			
			@Override
			public void remove() {
				findBlockUnder().stackUnder(findBlockOver());
				findBlockOver().stackOver(findBlockUnder());
    			stackOver(null);
    			stackUnder(null);
			}
        	
        };
        return it;
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    	if(b != null) {
    		if(b.findBlockUnder() == null || b.findBlockUnder() == this) {
    			this.above = b;
    			if(b.findBlockUnder() != this) {
    				b.stackOver(this);
    			}
    			
    			
    		}else {
    			b.findBlockUnder().stackUnder(null);
    			b.stackOver(null);
    			stackUnder(b);
    		}
    	}
    	else {
    		this.above = b;
    	}
        
    }

    @Override
    public void stackOver(BuildingBlock b) {
    	if(b != null) {
    		if(b.findBlockOver() == null || b.findBlockOver()==this) {
    			this.below = b;
    			if(b.findBlockOver() != this) {
    				b.stackUnder(this);
    			}
    			
    			
    		}else {
    			b.findBlockOver().stackUnder(null);
    			b.stackUnder(null);
    			stackOver(b);
    		}
    	}
    	else {
    		this.below = b;
    	}
        
    }

    @Override
    public BuildingBlock findBlockUnder() {
    	
        return below;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return above;
    }

}
