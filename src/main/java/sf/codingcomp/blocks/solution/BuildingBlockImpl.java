package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	private BuildingBlockImpl top, bottom;
	private List<BuildingBlockImpl> connects;
	
	public BuildingBlockImpl() {
		top = null;
		bottom = null;
		connects = new ArrayList<>();
	}
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        return new BBIterator();
    }
    
    private class BBIterator implements Iterator<BuildingBlock> {
    		private boolean hasTop = false;
    		private boolean hasBottom = false;
    		private boolean hasRemoved = true;
    		private boolean notBottom = true;
    		private BuildingBlock current = null;
    		private int counter = 0;
    		
    		public BBIterator() {
    			current = (BuildingBlockImpl) current;
    		}
    		
    		public boolean hasNext() {
    			if (findBlockOver() == null && !notBottom) {
    				return false;
    			}
    			else {
    				return true;
    			}
    		}
    		
    		public BuildingBlock next() {
    			hasRemoved = false;
    			
    			if (notBottom) {
    				while (notBottom) {
    					if (counter == 0) {
    					current = findBlockUnder();
    					if (current == null) {
    						return findBlockOver().findBlockUnder();
    					}
    					counter = 1;
    					}
    					else {
    						current = current.findBlockUnder();
    					}
    					notBottom = current.findBlockUnder() != null;
    				}
    				return current;
    			} else {
    				return current = findBlockOver();
    			}
    			
    		}
    		
    		public void remove() {
    			if(hasRemoved)
                    throw new IllegalStateException();
    			current = null;
    			hasRemoved = true;
    		}
    		
    		
    }

    @Override
    public void stackOver(BuildingBlock b) {
    		if (b == null) {
    			if (this.bottom != null) {
    				this.bottom.top = null;
    			}
    			this.bottom = null;
    		}
    		else {
    		BuildingBlockImpl a = (BuildingBlockImpl) b;
    		if (!Collections.disjoint(this.connects, a.connects)) {
    			throw new CircularReferenceException();
    		}
    		else {
    		if (bottom != null) {
    			this.bottom.top = null;
    		}
    		if (a.top != null) {
    			a.top.bottom = null;
    		}
        this.bottom = a;
        a.top = this;
        a.connects.add(this);
        this.connects.add(a);
    		}
    		}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
    		if (b == null) {
    			if (this.top != null) {
    				this.top.bottom = null;
    			}
    			this.top = null;
    		}
    		else {
    		BuildingBlockImpl a = (BuildingBlockImpl) b;
    		if (!Collections.disjoint(this.connects, a.connects)) {
    			throw new CircularReferenceException();
    		}
    		else {
    		if (top != null) {
    			this.top.bottom = null;
    		}
        top = a;
        if (a.bottom != null) {
        		a.bottom.top = null;
        }
        a.bottom = this;
        a.connects.add(this);
        this.connects.add(a);
    		}
    		}
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return this.bottom;
    }

    @Override
    public BuildingBlock findBlockOver() {
    		return this.top;
    }

}
