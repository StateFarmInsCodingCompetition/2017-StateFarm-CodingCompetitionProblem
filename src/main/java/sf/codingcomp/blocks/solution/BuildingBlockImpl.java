package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	private BuildingBlock blockAbove;
	private BuildingBlock blockBelow;

	@Override
	public Iterator<BuildingBlock> iterator() {
		return new Iterator<BuildingBlock>() {

			private BuildingBlock next = BuildingBlockImpl.this;
			private int index = 0;
			private boolean removed = false;
			
			@Override
			public boolean hasNext() {
				while (next.findBlockUnder() != null) {
					next = next.findBlockUnder();
				}
				BuildingBlock temp = next;
				for (int i = 0; i < index; i++) {
					temp = temp.findBlockOver();
				}
				return temp != null;
			}

			@Override
			public BuildingBlock next() {
				while (next.findBlockUnder() != null) {
					next = next.findBlockUnder();
				}
				BuildingBlock temp = next;
				for (int i = 0; i < index; i++) {
					temp = temp.findBlockOver();
				}
				index++;
				removed = false;
				return temp;
			}
			
			@Override
			public void remove() {
				if (index == 0) {
					throw new IllegalStateException("By contract, remove may only be called after next.");
				}
				if (removed) {
					throw new IllegalStateException("By contract, remove may only be called once for each next.");
				}
				
				removed = true;
				while (next.findBlockUnder() != null) {
					next = next.findBlockUnder();
				}
				BuildingBlock temp = next;
				for (int i = 0; i < index; i++) {
					temp = temp.findBlockOver();
				}
				BuildingBlock over = temp.findBlockOver();
				BuildingBlock under = temp.findBlockUnder();
				if (over != null) {
					((BuildingBlockImpl) over).stackOverHelper(under, true);
				}
				if (under != null) {
					((BuildingBlockImpl) under).stackUnderHelper(over, true);
				}
			}

		};
	}

	@Override
	public void stackOver(BuildingBlock b) {
		stackOverHelper(b, false);
	}
	
	private void stackOverHelper(BuildingBlock b, boolean secondIter) {
		BuildingBlock prevBelow = this.blockBelow;
		if (b == null) {
			if (prevBelow != null) {
				this.blockBelow = null;
				if (!secondIter)
					((BuildingBlockImpl) prevBelow).stackUnderHelper(null, true);
			}
			return;
		}
		
		if (this.blockBelow == b) {
			return;
		}
		BuildingBlock temp = b;
		while (temp != null) {
			if (temp.equals(this)) {
				throw new CircularReferenceException("Connecting three blocks in a circle should not be allowed!");
			}
			temp = temp.findBlockUnder();
		}
		
		this.blockBelow = b;
		if (!secondIter) {
			if (prevBelow != null)
				((BuildingBlockImpl) prevBelow).stackUnderHelper(null, true);
			
			if (b.findBlockUnder() == null || !b.findBlockUnder().equals(this)) {
				if (b.findBlockOver() != null) {
						((BuildingBlockImpl) b.findBlockOver()).stackOverHelper(null, true);
				}
			}
			((BuildingBlockImpl) b).stackUnderHelper(this, true);
		}
	}

	@Override
	public void stackUnder(BuildingBlock b) {
		stackUnderHelper(b, false);
	}
	
	private void stackUnderHelper(BuildingBlock b, boolean secondIter) {
		BuildingBlock prevAbove = this.blockAbove;
		if (b == null) {
			if (prevAbove != null) {
				this.blockAbove = null;
				if (!secondIter)
					((BuildingBlockImpl) prevAbove).stackOverHelper(null, true);

			}
			return;
		}
		
		if (this.blockAbove == b) {
			return;
		}
		BuildingBlock temp = b;
		while (temp != null) {
			if (temp.equals(this)) {
				throw new CircularReferenceException("Connecting three blocks in a circle should not be allowed!");
			}
			temp = temp.findBlockOver();
		}
		
		this.blockAbove = b;
		if (!secondIter) {
			if (prevAbove != null) 
				((BuildingBlockImpl) prevAbove).stackOverHelper(null, true);
			
			if (b.findBlockOver() == null || !b.findBlockOver().equals(this)) {
				if (b.findBlockUnder() != null) {
					((BuildingBlockImpl) b.findBlockUnder()).stackUnderHelper(null, true);
				}
			} 
			((BuildingBlockImpl) b).stackOverHelper(this, true);
		}
	}
	

	@Override
	public BuildingBlock findBlockUnder() {
		// TODO Auto-generated method stub
		return blockBelow;
	}

	@Override
	public BuildingBlock findBlockOver() {
		// TODO Auto-generated method stub
		return blockAbove;
	}

}
