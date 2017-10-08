package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

public class BuildingBlockImpl implements BuildingBlock {

    private BuildingBlock blockBelow;
    private BuildingBlock blockAbove;

    private AtomicBoolean lockBelow = new AtomicBoolean();
    private AtomicBoolean lockAbove = new AtomicBoolean();

    @Override
    public Iterator<BuildingBlock> iterator() {
        if(blockBelow != null){
            return blockBelow.iterator();
        }

        return new BuildingBlockIterator(this);
    }

    @Override
    public void stackOver(BuildingBlock b) {
        if(lockBelow.get()){
            return;
        }

        lockBelow.set(true);

        if(contains(b)){
            throw new CircularReferenceException();
        }

        if(blockBelow != null){
            blockBelow.stackUnder(null);
        }

        blockBelow = b;

        if(b != null){
            b.stackUnder(this);
        }

        lockBelow.set(false);
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        if(lockAbove.get()){
            return;
        }

        lockAbove.set(true);

        if(contains(b)){
            throw new CircularReferenceException();
        }

        if(blockAbove != null){
            blockAbove.stackOver(null);
        }

        blockAbove = b;

        if(b != null){
            b.stackOver(this);
        }

        lockAbove.set(false);
    }

    private boolean contains(BuildingBlock b){
        if(b != null){
            for(BuildingBlock block : this){
                if(!block.equals(blockAbove) && !block.equals(blockBelow)){
                    if(block.equals(b)){
                        return true;
                    }
                }
            }
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

    private class BuildingBlockIterator implements Iterator<BuildingBlock> {

        private BuildingBlock pointer;
        private BuildingBlock previous;

        private BuildingBlockIterator(BuildingBlock pointer){
            this.pointer = pointer;
        }

        @Override
        public boolean hasNext(){
            return pointer != null;
        }

        @Override
        public BuildingBlock next(){
            if(pointer == null){
                throw new NoSuchElementException();
            }

            previous = pointer;
            pointer = pointer.findBlockOver();

            return previous;
        }

        @Override
        public void remove(){
            if(previous == null){
                throw new IllegalStateException();
            }

            BuildingBlock above = previous.findBlockOver();
            BuildingBlock below = previous.findBlockUnder();

            previous.stackOver(null);
            previous.stackUnder(null);

            above.stackOver(below);

            previous = null;
        }

    }

}
