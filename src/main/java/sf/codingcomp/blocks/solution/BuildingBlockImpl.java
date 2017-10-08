package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	public BuildingBlockImpl under; // Block under us
	public BuildingBlockImpl over; // Block over us

	public BuildingBlockImpl() {
		this.under = null;
		this.over = null;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<BuildingBlock> iterator() {
		BuildingBlockImpl firstBlock = new BuildingBlockImpl();
		firstBlock.over = (BuildingBlockImpl) getFirstBlock(this); 

		return new Iterator<BuildingBlock>() {
			BuildingBlockImpl currentBlock = firstBlock;
			boolean callRemoved = false;

			@Override
			public boolean hasNext() {
				if (currentBlock.over != null) {
					return true;
				}
				return false;
			}

			@Override
			public BuildingBlock next() {
				currentBlock = currentBlock.over;
				callRemoved = false;
				return currentBlock;
			}

			public void remove() {
				if (callRemoved == false && currentBlock.under != null) {
					currentBlock.under.over = currentBlock.over; // Sets the block below ours to reference the block
																	// above
					currentBlock.over.under = currentBlock.under; // Sets the block above ours to reference block below
					BuildingBlockImpl _temp = currentBlock.under; // Sets a temp ref to the under block
					// Destroys reference on the current block
					currentBlock.over = null;
					currentBlock.under = null;
					currentBlock = _temp; // Sets it finally to ref block above
					_temp = null; // Destroys the temp

					callRemoved = true;
				} else {
					throw new IllegalStateException();
				}
			}

		};
	}

	/** 
	 * @param b is placed under our object 
	 * Handles null objects and potential loops created through the blocks s
	 */
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

		if (checkCircUnder()) {
			this.under = null;
			_temp.stackUnder(null);
			throw new CircularReferenceException();
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

		if (checkCircOver()) {
			this.over = null;
			_temp.stackOver(null);
			throw new CircularReferenceException();
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

	private BuildingBlock getFirstBlock(BuildingBlock b) {
		BuildingBlock currentBlock = this;

		while (currentBlock.findBlockUnder() != null) {
			currentBlock = currentBlock.findBlockUnder();
		}

		return currentBlock;
	}

	private boolean checkCircOver() {
		BuildingBlockImpl _temp = this.over;
		while (_temp != null) {
			if (_temp.equals(this)) {
				return true;
			}
			_temp = (BuildingBlockImpl) _temp.findBlockOver();
		}
		return false;
	}

	private boolean checkCircUnder() {
		BuildingBlockImpl _temp = this.under;
		while (_temp != null) {
			if (_temp.equals(this)) {
				return true;
			}
			_temp = (BuildingBlockImpl) _temp.findBlockUnder();
		}
		return false;
	}
}
