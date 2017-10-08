package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

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
            BuildingBlock bb = findBlockUnder();
            blockUnder= null;
            if(bb != null) bb.stackUnder(null);
        }
        else if(findBlockUnder() == null || !findBlockUnder().equals(b)) {
            BuildingBlock bb = findBlockUnder();
            BuildingBlock temp = this;
            while(temp.findBlockOver()!=null){
                if(b.equals(temp.findBlockOver())){
                    throw new CircularReferenceException();
                }
                temp = temp.findBlockOver();
            }
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
            BuildingBlock temp = this;
            while(temp.findBlockUnder()!=null){
                if(b.equals(temp.findBlockUnder())){
                    throw new CircularReferenceException();
                }
                temp = temp.findBlockUnder();
            }
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
        boolean iterated = false;

        ArrayList<BuildingBlock> blocks;

        public stackingBlocks(){
            blocks = new ArrayList<>();
            blocks.add(BuildingBlockImpl.this);
            BuildingBlock b = findBlockUnder();
            while (b != null) {
                blocks.add(b);
                b = b.findBlockUnder();
            }
            b = findBlockOver();
            while (b != null) {
                blocks.add(0, b);
                b = b.findBlockOver();
            }
        }

        @Override
        public boolean hasNext() {
            if(blocks.get(cursor).findBlockOver()!=null || !iterated){
                return true;
            }
            return false;
        }

        @Override
        public BuildingBlock next() {
            iterated = true;
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
            BuildingBlock b = blocks.get(cursor);
            if((b.findBlockOver()==null && b.findBlockUnder()==null) || !iterated){
                throw new IllegalStateException();
            }else{
                BuildingBlock bAbove = b.findBlockOver();
                BuildingBlock bUnder = b.findBlockUnder();
                bAbove.stackOver(bUnder);
                bUnder.stackUnder(bAbove);
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super BuildingBlock> action) {

        }
    }


}
