package sf.codingcomp.blocks.solution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	
	BuildingBlockImpl above;
	BuildingBlockImpl below;

    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return new BuildingBlockImplIterator();
    }

    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
    		BuildingBlockImpl newBottom = (BuildingBlockImpl) b;
    	
		if (newBottom == null) {
			if (below != null) {
				below.above = null;
			}
			below = null;
			return;
		}
		
		BuildingBlockImpl newBottomsOldTop = newBottom.above;
		
		if (below != null) {
			below.above = null;
		}

		if (newBottomsOldTop != null) {
			newBottomsOldTop.below = null;
		}
		
		newBottom.above = this;
		this.below = newBottom;
		
		if (hasCircle()) {
			below = null;
			newBottom.above = null;
			throw new CircularReferenceException();
		}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
//    		BuildingBlock oldTop = detachMyTop();
//    		((BuildingBlockImpl) oldTop).detachMyBottom();
    	
    		BuildingBlockImpl newTop = (BuildingBlockImpl) b;
    	
    		if (newTop == null) {
    			if (above != null) {
    				above.below = null;
    			}
    			above = null;
    			return;
    		}
    		
    		BuildingBlockImpl newTopsOldBottom = newTop.below;
    		
    		// if the guy I'm about to put above me B already has someone below him C
    		// then I have to change C's above
    		
    		if (above != null) {
    			above.below = null;
    		}
    		
    		if (newTopsOldBottom != null) {
    			newTopsOldBottom.above = null;
    		}
    		
    		newTop.below = this;
		this.above = newTop;
		
		if (hasCircle()) {
			above = null;
			newTop.below = null;
			throw new CircularReferenceException();
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
    
    private boolean hasCircle() {
    		HashSet<BuildingBlock> visited = new HashSet<>();
    		BuildingBlockImpl cur = this;
    		while (cur != null) {
    			if (visited.contains(cur)) return true;
    			else visited.add(cur);
    			cur = cur.above;	
    		}
    		cur = this;
    		visited.clear();
    		while (cur != null) {
    			if (visited.contains(cur)) return true;
    			else visited.add(cur);
    			cur = cur.below;	
    		}
    		return false;
    }
    
    private class BuildingBlockImplIterator implements Iterator<BuildingBlock> {
    		private BuildingBlockImpl cur;
    		private BuildingBlockImpl lastReturned;
    		private boolean justReturned = false;
    		
    		public BuildingBlockImplIterator() {
    			cur = BuildingBlockImpl.this;
    			while (cur.below != null) {
    				cur = cur.below;
    			}
    		}
    		
    		public boolean hasNext() {
    			return cur != null;
    		}
    		
    		public BuildingBlock next() {
    			if (hasNext()) {
    				BuildingBlockImpl toRtn = cur;
    				cur = cur.above;
    				lastReturned = toRtn;
    				justReturned = false;
    				return toRtn;
    			}
    			else {
    				throw new NoSuchElementException();
    			}
    		}
    		
    		public void remove() {
    			if (justReturned || lastReturned == null) {
    				throw new IllegalStateException();
    			} else {
    				if (lastReturned.below != null) {
    					lastReturned.below.above = lastReturned.above;
    				}
    				if (lastReturned.above != null) {
    					lastReturned.above.below = lastReturned.below;
    				}
    				lastReturned.above = null;
    				lastReturned.below = null;
    				justReturned = true;
    			}
    		}
    }
    

}
