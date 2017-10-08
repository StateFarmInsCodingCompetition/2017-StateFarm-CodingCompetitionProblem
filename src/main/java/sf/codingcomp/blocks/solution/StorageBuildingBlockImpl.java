package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StorageBuildingBlock;

public class StorageBuildingBlockImpl<T> extends BuildingBlockImpl implements StorageBuildingBlock<T> {

	T data = null;
	
	public StorageBuildingBlockImpl() {
		this.data = null;
	}
	
    @Override
    public T getValue() {
        return this.data;
    }

    @Override
    public void setValue(T value) {
        this.data = value;

    }

}
