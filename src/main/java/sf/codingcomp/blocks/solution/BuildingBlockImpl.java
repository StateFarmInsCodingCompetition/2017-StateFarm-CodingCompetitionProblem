package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

    BuildingBlock blockAbove;
    BuildingBlock blockUnder;

    public BuildingBlock getBlockAbove() {
        return blockAbove;
    }

    public void setBlockAbove(BuildingBlock blockAbove) {
        this.blockAbove = blockAbove;
    }

    public BuildingBlock getBlockUnder() {
        return blockUnder;
    }

    public void setBlockUnder(BuildingBlock blockUnder) {
        this.blockUnder = blockUnder;
    }

    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return null;
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

}
