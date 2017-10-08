package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import com.sun.xml.internal.rngom.dt.builtin.BuiltinDatatypeLibrary;
import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	private BuildingBlock under = null;
	private BuildingBlock over = null;

    @Override
    public Iterator<BuildingBlock> iterator() {
        return new BlockIterator();
    }
    
    private class BlockIterator implements Iterator<BuildingBlock> {
    	private boolean removed = true;
		private BuildingBlock cursor;
		
		public BlockIterator() {
			cursor = BuildingBlockImpl.this;
			while (cursor.findBlockUnder() != null) {
				cursor = cursor.findBlockUnder();
			}
		}
		
		public boolean hasNext() {
			return cursor != null;
		}
		
		public BuildingBlock next() {
			removed = false;
			if(this.hasNext()) {
				BuildingBlock current = cursor;
				cursor = cursor.findBlockOver();
				return current;
			}
			return null;
		}

		public void remove(){
			if (removed) {
				throw new IllegalStateException("exception");
			}
			removed = true;
			BuildingBlock up = cursor;
			BuildingBlock down = cursor.findBlockUnder().findBlockUnder();
			up.stackOver(down);
		}
    }

    @Override
    public void stackOver(BuildingBlock b) {
		BuildingBlock pointer = b;
		while (pointer != null) {
			if (pointer.equals(this)) {
				throw new CircularReferenceException();
			}
			pointer = pointer.findBlockUnder();
		}
		BuildingBlock old = this.under;
    	if (b == null) {
    		if (old != null) {
				this.under = null;
    			old.stackUnder(null);
			}

		} else {
			BuildingBlock replace = b.findBlockOver();
			if (replace == null && old == null) {
				this.under = b;
				b.stackUnder(this);
			} else if (old == null && replace != null) {
				if (!replace.equals(this)) {
					replace.stackOver(null);
					b.stackUnder(null);
					b.stackUnder(this);
				}
				this.under = b;
			} else if (old != null && replace == null) {
				old.stackUnder(null);
				this.stackOver(null);
				this.under = b;
				b.stackUnder(this);
			} else if (old != null && replace != null) {
				if (!(replace.equals(this) && old.equals(b))) {
					old.stackUnder(null);
					this.stackOver(null);
					replace.stackOver(null);
					b.stackUnder(null);
					this.under = b;
					b.stackUnder(this);
				}
			}
		}
    }
    @Override
    public void stackUnder(BuildingBlock b) {
		BuildingBlock pointer = b;
    	while (pointer != null) {
    		if (pointer.equals(this)) {
    			throw new CircularReferenceException();
			}
			pointer = pointer.findBlockOver();
		}
		BuildingBlock old = this.over;
		if (b == null) {
			if (old != null) {
				this.over = null;
				old.stackOver(null);
			}

		} else {
			BuildingBlock replace = b.findBlockUnder();
			if (replace == null && old == null) {
				this.over = b;
				b.stackOver(this);
			} else if (old == null && replace != null) {
				if (!replace.equals(this)) {
					replace.stackUnder(null);
					b.stackOver(null);
					b.stackOver(this);
				}
				this.over = b;
			} else if (old != null && replace == null) {
				old.stackOver(null);
				this.stackUnder(null);
				this.over = b;
				b.stackOver(this);
			} else if (old != null && replace != null) {
				if (!(replace.equals(this) && old.equals(b))) {
					old.stackOver(null);
					this.stackUnder(null);
					replace.stackUnder(null);
					b.stackOver(null);
					this.over = b;
					b.stackOver(this);
				}
			}
		}


    }

    @Override
    public BuildingBlock findBlockUnder() {
        return under;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return over;
    }

}
