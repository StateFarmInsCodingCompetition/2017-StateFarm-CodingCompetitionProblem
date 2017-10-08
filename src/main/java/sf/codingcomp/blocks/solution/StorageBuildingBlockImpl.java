package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StorageBuildingBlock;

public class StorageBuildingBlockImpl<T> extends BuildingBlockImpl implements StorageBuildingBlock<T> {

	T val = null;

	@Override
	public T getValue() {
		return val;
	}

	@Override
	public void setValue(T value) {
		this.val = value; 
	}

}
