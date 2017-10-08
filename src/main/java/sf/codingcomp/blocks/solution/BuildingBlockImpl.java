package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {
	// Attribute that will hold the block that is under the reference
	// block(this.obj)
	private BuildingBlock blockUnder;
	// Attribute that will hold the block that is over the reference block(this.obj)
	private BuildingBlock blockOver;

	public BuildingBlockImpl() {
		this.blockUnder = null;
		this.blockOver = null;
	}

	@Override
	public Iterator<BuildingBlock> iterator() {
		@SuppressWarnings("unused")
		Iterator<BuildingBlock> it = new Iterator<BuildingBlock>() {
			@Override
			public boolean hasNext() {
				// How to check the Block over attribute of the current item being looked
				// at by the iterator?
				if (blockOver == null) {
					return false;
				}
				return true;
			}

			@Override
			public BuildingBlock next() {
				while (hasNext()) {
					// Return the object's blockOver attribute
					return blockOver;
				}
				return null;
			}
		};
		return null;
	}

	public void blockOverSetter(BuildingBlock b) {
		this.blockOver = b;
	}

	public void blockUnderSetter(BuildingBlock b) {
		this.blockUnder = b;
	}

	@Override
	public void stackOver(BuildingBlock b) {
		if (b == null) {
			BuildingBlock under = this.blockUnder;
			under.blockOverSetter(null);
			this.blockUnder = null;

		}
		this.blockUnder = b;
		b.blockOverSetter(this);

	}

	@Override
	public void stackUnder(BuildingBlock b) {
		if (b == null) {
			BuildingBlock over = this.blockOver;
			over.blockUnderSetter(null);
			this.blockOver = null;
		}

		this.blockOver = b;
		b.blockUnderSetter(this);

	}

	@Override
	public BuildingBlock findBlockUnder() {
		return this.blockUnder;
	}

	@Override
	public BuildingBlock findBlockOver() {
		return this.blockOver;
	}

}
