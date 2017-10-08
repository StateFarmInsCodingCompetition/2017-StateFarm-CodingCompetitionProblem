package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

    BuildingBlock blockAbove;
    BuildingBlock blockUnder;


    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return new stackingBlocks();
    }

    @Override
    public void stackOver(BuildingBlock b) {
        if(b == null) {
//            BuildingBlock b = findBlockOver();
            BuildingBlock bb = findBlockUnder();
            blockUnder= null;
            if(bb != null) bb.stackUnder(null);
        }
        else if(findBlockUnder() == null || !findBlockUnder().equals(b)) {
            BuildingBlock bb = findBlockUnder();
            if(bb != null) bb.stackUnder(null);
            blockUnder = b;
            b.stackUnder(this);

        }
        // TODO Auto-generated method stub

    }

    @Override
    public void stackUnder(BuildingBlock b) {
        if(b == null) {
            BuildingBlock bb = findBlockOver();
            blockAbove = null;
            if(bb != null) bb.stackOver(null);
        }
        else if(findBlockOver() == null || !findBlockOver().equals(b)) {
            BuildingBlock bb = findBlockOver();
            if(bb != null) bb.stackOver(null);

            blockAbove = b;
            b.stackOver(this);
        }
        // TODO Auto-generated method stub

    }

    @Override
    public BuildingBlock findBlockUnder() {
        // TODO Auto-generated method stub
        return blockUnder;
    }

    @Override
    public BuildingBlock findBlockOver() {
        // TODO Auto-generated method stub
        return blockAbove;
    }

    private class stackingBlocks implements Iterator<BuildingBlock>{

        int cursor = 0;

        ArrayList<BuildingBlock> blocks;
        public stackingBlocks(){
            blocks = new ArrayList<>();
            blocks.add(BuildingBlockImpl.this);
            BuildingBlock b = findBlockUnder();
            while (b != null) {
                blocks.add(b);
                b = findBlockUnder();
            }
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public BuildingBlock next() {
            if(cursor == 0){
                cursor = blocks.size()-1;
                return blocks.get(cursor);
            }else{
                cursor --;
                return blocks.get(cursor);
            }
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super BuildingBlock> action) {

        }
    }


}
