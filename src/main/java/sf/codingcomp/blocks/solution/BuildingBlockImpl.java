package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.Objects;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

    private BuildingBlock blockAbove;
    private BuildingBlock blockBelow;

    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return null;
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
        if(this.blockBelow!=null){
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
        if(this.blockAbove!=null){
            b.stackOver(null);
        }
        this.blockAbove = b;
        b.stackOver(this);
    }

    @Override
    public BuildingBlock findBlockUnder() {
        return blockBelow;
    }

    @Override
    public BuildingBlock findBlockOver() {
        return blockAbove;
    }

}
