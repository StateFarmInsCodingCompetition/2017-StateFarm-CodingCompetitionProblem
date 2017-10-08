package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.NoSuchElementException;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

    private BuildingBlock above;
    private BuildingBlock below;

    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        Iterator<BuildingBlock> iterator = new Iterator<BuildingBlock>() {
            private BuildingBlock cur = findCurBlock();
            private boolean remove = false;

            private BuildingBlock findCurBlock() {
                BuildingBlock cur = BuildingBlockImpl.this;
                while (cur.findBlockUnder() != null) {
                    cur = cur.findBlockUnder();
                }
                return cur;
            }

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public BuildingBlock next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    remove = true;
                    BuildingBlock answer = cur;
                    cur = cur.findBlockOver();
                    return answer;
                }
            }

            @Override
            public void remove() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else if (!remove) {
                    throw new IllegalStateException();
                } else {
                    remove = false;
                    cur.stackOver(cur.findBlockUnder().findBlockUnder());
                }
            }
        };
        return iterator;
    }

    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
        if (b == null) {
            BuildingBlock prev = below;
            below = b;
            if (prev.findBlockOver() != null) {
                prev.stackUnder(null);
            }
        } else {
            if (this.findBlockOver() != null && b.findBlockUnder() != null
                    && this.findBlockOver() == b.findBlockUnder() && this
                    .below == null && b.findBlockOver() == null) {
                throw new CircularReferenceException();
            }
            if (below == null) {
                below = b;
                if (b.findBlockOver() != this) {
                    b.stackUnder(this);
                }
            } else {
                below.stackUnder(null);
                below = null;
                stackOver(b);
            }
        }
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
        if (b == null) {
            BuildingBlock prev = above;
            above = b;
            if (prev.findBlockUnder() != null) {
                prev.stackOver(null);
            }
        } else {
            if (this.findBlockUnder() != null && b.findBlockOver() != null
                    && this.findBlockUnder() == b.findBlockOver() && this
                    .above == null && b.findBlockUnder() == null) {
                throw new CircularReferenceException();
            }
            if (above == null) {
                above = b;
                if (b.findBlockUnder() != this) {
                    b.stackOver(this);
                }
            } else {
                above.stackOver(null);
                above = null;
                stackUnder(b);
            }
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
