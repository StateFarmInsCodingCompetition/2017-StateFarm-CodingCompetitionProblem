package sf.codingcomp.blocks;

import java.util.Iterator;

public class BuildingBlockIterator implements Iterator<BuildingBlock> {

	private BuildingBlock startingBlock;
	private BuildingBlock currentBlock;
	private BuildingBlock nextBlock;
	private boolean canRemove;
	
	public BuildingBlockIterator(BuildingBlock start) {
		startingBlock = start;
		
		//move head to bottom block
		while(startingBlock.findBlockUnder() != null) {
			startingBlock = startingBlock.findBlockUnder();
			if(startingBlock.equals(start)) {
				throw new CircularReferenceException();
			}
		}
		//check for circular pattern
		currentBlock = startingBlock;
		while(currentBlock.findBlockOver() != null) {
			currentBlock = currentBlock.findBlockOver();
			if(currentBlock.equals(startingBlock)) {
				throw new CircularReferenceException();
				
			}
		}
		
		currentBlock = null;
		nextBlock = startingBlock;
		canRemove = false;
	}

	@Override
	public boolean hasNext() {
		return (nextBlock != null);
	}

	@Override
	public BuildingBlock next() {
		if(hasNext())
		{
			currentBlock = nextBlock;
			nextBlock = currentBlock.findBlockOver();
			canRemove = true;
			return currentBlock;
		}
		return null;
	}

	@Override
	public void remove() {
		if(canRemove) {
			BuildingBlock mergeOver = currentBlock.findBlockOver();
			BuildingBlock mergeUnder = currentBlock.findBlockUnder();
			next();
			mergeOver.stackOver(mergeUnder);
			mergeUnder.stackUnder(mergeOver);
			Iterator.super.remove();
			canRemove = false;
		}
		else {
			throw new IllegalStateException();
		}
	}
	
}
