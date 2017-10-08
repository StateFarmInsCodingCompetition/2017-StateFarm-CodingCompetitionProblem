package sf.codingcomp.blocks.solution;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

    private BuildingBlock blockAbove;
    private BuildingBlock blockBelow;

    @Override
    public Iterator<BuildingBlock> iterator() {
        return new BuildingBlockIterator(this);
    }

    @Override
    public void stackOver(BuildingBlock b) {
        //this is being stacked on top of b
        if(b == null){ //block below is being unassigned
            if(this.blockBelow==null) return;

            BuildingBlock oldBelow = this.blockBelow;
            this.blockBelow = null;
            oldBelow.stackUnder(null);
            return;
        }

        if(Objects.equals(this.blockBelow,b)) return;
        if(wouldAddingOverCauseCircular(b)) throw new CircularReferenceException();
        if(this.blockBelow!=null){
            this.blockBelow.stackUnder(null);
            b.stackUnder(null);
        }

        this.blockBelow = b;
        b.stackUnder(this);
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        //this is being stacked below b
        if(b == null){ //block above is being unassigned
            if(this.blockAbove==null) return;

            BuildingBlock oldAbove = this.blockAbove;
            this.blockAbove = null;
            oldAbove.stackOver(null);
            return;
        }
        if(Objects.equals(this.blockAbove,b)) return;
        if(wouldAddingUnderCauseCircular(b)) throw new CircularReferenceException();
        if(this.blockAbove!=null){
            this.blockBelow.stackOver(null);
            b.stackOver(null);
        }
        this.blockAbove = b;
        b.stackOver(this);
    }

    private boolean wouldAddingUnderCauseCircular(BuildingBlock candidate){
        //Testing validity of adding this under the candidate
        BuildingBlock test = this;
        while (test.findBlockUnder()!=null){
            test = test.findBlockUnder();
            if(Objects.equals(test,candidate)) return true;
        }
        return false;
    }

    private boolean wouldAddingOverCauseCircular(BuildingBlock candidate){
        //Testing validity of adding this under the candidate
        BuildingBlock test = this;
        while (test.findBlockOver()!=null){
            test = test.findBlockOver();
            if(Objects.equals(test,candidate)) return true;
        }
        return false;
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return blockBelow;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return blockAbove;
    }

    public class BuildingBlockIterator implements Iterator<BuildingBlock>{

        BuildingBlock root;
        BuildingBlock current;

        public BuildingBlockIterator(BuildingBlock root) {
            while(root.findBlockUnder()!=null) root = root.findBlockUnder();
            this.root = root;
            this.current = root;
        }

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public BuildingBlock next() {
            BuildingBlock temp = current;
            this.current = current.findBlockOver();
            return temp;
        }

        @Override
        public void remove() {
            if(Objects.equals(root,current)) throw new IllegalStateException();

        }

    }

}
