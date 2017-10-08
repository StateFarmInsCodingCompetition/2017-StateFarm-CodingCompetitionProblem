package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

    BuildingBlockImpl under = null;
    BuildingBlockImpl over = null;

    @Override
    public Iterator<BuildingBlock> iterator() {

        List<BuildingBlockImpl> stack = getStack();

        return new Iterator<BuildingBlock>() {
            int idx = -1;
            List<BuildingBlockImpl> s = stack;
            boolean hasRemoved = false;
            @Override
            public boolean hasNext() {
                return idx+1 < s.size();
            }

            @Override
            public BuildingBlock next() {
                idx += 1;
                BuildingBlockImpl curr = s.get(idx);
                hasRemoved = false;
                return curr;
            }

            @Override
            public void remove() {
                if (idx == -1 || hasRemoved) {
                    throw new IllegalStateException();
                }
                BuildingBlockImpl curr = s.get(idx);
                curr.over.under = curr.under;
                curr.under.over = curr.over;
                curr.over = null;
                curr.under = null;
                hasRemoved = true;
            }
        };
    }

    /**
     * Get all blocks in the stack of this block, from bottom to top
     * @return the stack of blocks
     */
    public List<BuildingBlockImpl> getStack() {
        BuildingBlockImpl b = this;
        while (b.under != null) {
            b = b.under;
        }

        List<BuildingBlockImpl> stack = new ArrayList<>();
        stack.add(b);
        while (b.over != null) {
            stack.add(b.over);
            b = b.over;
        }
        return stack;
    }

    @Override
    public void stackOver(BuildingBlock b) {
        if (b==null) {
            this.under.over = null;
            this.under = null;
        }
        else {
            if (this.under != null) {
                this.under.over = null;
            }
            BuildingBlockImpl bl = (BuildingBlockImpl) b;
            if (bl.over != null) {
                bl.over.under = null;
            }
            bl.over = this;
            this.under = bl;

            if (isLoopUnder()) {
                this.under = null;
                bl.over = null;
                throw new CircularReferenceException();
            }
        }
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        if (b==null) {
            this.over.under = null;
            this.over = null;
        }
        else {
            if (this.over != null) {
                this.over.under = null;
            }
            BuildingBlockImpl bl = (BuildingBlockImpl) b;
            if (bl.under != null) {
                bl.under.over = null;
            }
            bl.under = this;
            this.over = bl;

            if (isLoopOver()) {
                this.over = null;
                bl.under = null;
                throw new CircularReferenceException();
            }
        }
    }

    private boolean isLoopOver() {
        // floyd cycle algorithm to detect a loop over
        BuildingBlockImpl slow = this;
        BuildingBlockImpl fast = this;
        while (slow != null && fast != null && fast.over != null) {
            slow = slow.over;
            fast = fast.over.over;

            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    private boolean isLoopUnder() {
        // floyd cycle algorithm to detect a loop under
        BuildingBlockImpl slow = this;
        BuildingBlockImpl fast = this;
        while (slow != null && fast != null && fast.under != null) {
            slow = slow.under;
            fast = fast.under.under;

            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return this.under;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return this.over;
    }

}
