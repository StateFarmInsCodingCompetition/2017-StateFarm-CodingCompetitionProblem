package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StorageBuildingBlock;

public class StorageBuildingBlockImpl<T> extends BuildingBlockImpl implements StorageBuildingBlock<T> {
	private T value;

	public StorageBuildingBlockImpl() {
		this.value = null;
	}
	
    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
    	this.value = value;
    }
}
