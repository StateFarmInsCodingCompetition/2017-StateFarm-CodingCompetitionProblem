package sf.codingcomp.blocks.solution;

import java.util.Arrays;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

	BuildingBlock above;
	BuildingBlock below;
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
//        return findBlockOver().findBlockUnder();
        return Arrays.<BuildingBlock>asList(above.findBlockUnder()).iterator();
    }

    @Override
    public void stackOver(BuildingBlock b) {
        this.above = b;
        
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        this.below = b;
        
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
