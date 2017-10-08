package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

    BuildingBlock blockAbove;
    BuildingBlock blockUnder;

    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void stackOver(BuildingBlock b) {
        if(findBlockUnder() == null || !findBlockUnder().equals(b)) {
            blockUnder = b;
            b.stackUnder(this);
        }
        // TODO Auto-generated method stub

    }

    @Override
    public void stackUnder(BuildingBlock b) {
        if(findBlockOver() == null || !findBlockOver().equals(b)) {
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
