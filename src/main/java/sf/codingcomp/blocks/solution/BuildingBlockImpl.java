package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {

	public BuildingBlockImpl under; // Block under us
	public BuildingBlockImpl over; // Block over us

	public BuildingBlockImpl() {
		this.under = null;
		this.over = null;
	}

	@Override
	public Iterator<BuildingBlock> iterator() {
		return null; 
	}

	// Puts block under us
	@Override
	public void stackOver(BuildingBlock b) {
		BuildingBlockImpl _temp = (BuildingBlockImpl) b;

		if (this.under == null) {
			this.under = _temp;
		} else {
			this.under.over = null;
			this.under = _temp;
		}

		if (b != null && b.findBlockOver() != this) {
			_temp.stackUnder(this);
		}
	}

	// Puts block over us
	@Override
	public void stackUnder(BuildingBlock b) {
		BuildingBlockImpl _temp = (BuildingBlockImpl) b;

		if (this.over == null) {
			this.over = _temp;
		} else {
			this.over.under = null;
			this.over = _temp;
		}

		if (b != null && b.findBlockUnder() != this) {
			_temp.stackOver(this);
		}
	}

	// Returns block under us
	@Override
	public BuildingBlock findBlockUnder() {
		return this.under;
	}

	// Returns block over us
	@Override
	public BuildingBlock findBlockOver() {
		return this.over;
	}

}
