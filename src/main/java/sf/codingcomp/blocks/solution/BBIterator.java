package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BBIterator implements Iterator<BuildingBlock> {
	BuildingBlock block;
	public BBIterator(BuildingBlock block) {
		this.block = block;
		while (this.block.findBlockOver() != null) {
			this.block = this.block.findBlockOver();
		}
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return this.block != null;
	}

	@Override
	public BuildingBlock next() {
		// TODO Auto-generated method stub
		BuildingBlock bb = this.block.findBlockUnder();
		this.block = bb;
		return bb;
	}

}
