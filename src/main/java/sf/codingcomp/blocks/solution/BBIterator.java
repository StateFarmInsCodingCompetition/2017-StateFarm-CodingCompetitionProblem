package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BBIterator implements Iterator<BuildingBlock> {
	BuildingBlock block;
	boolean canRemove;
	public BBIterator(BuildingBlock block) {
		this.block = block;
		while (this.block.findBlockUnder() != null) {
			this.block = this.block.findBlockUnder();
		}
		canRemove = false;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return this.block != null;
	}

	@Override
	public BuildingBlock next() {
		// TODO Auto-generated method stub
		BuildingBlock bb = this.block;
		this.block = this.block.findBlockOver();
		canRemove = true;
		return bb;
	}
	
	@Override
	public void remove() {
		if (!canRemove) {
			throw new IllegalStateException();
		}
		//System.out.println(this.block.findBlockUnder().hashCode());
		BuildingBlock x = this.block.findBlockUnder();
		this.block.stackOver(this.block.findBlockUnder().findBlockUnder());
		/*x.stackUnder(this.block.findBlockOver());
		this.block = x;*/
		//System.out.println(this.block.hashCode());
		//System.out.println(this.block.findBlockOver().hashCode());
		//this.block = this.block.findBlockUnder();
		/*BuildingBlock x = this.block.findBlockUnder();
		if (x != null) {
			x.stackUnder(this.block.findBlockOver());
		}
		this.block = x;*/
		canRemove = false;
	}

}
