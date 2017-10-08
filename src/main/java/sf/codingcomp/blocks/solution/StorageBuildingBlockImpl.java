package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StorageBuildingBlock;

public class StorageBuildingBlockImpl<T> extends BuildingBlockImpl implements StorageBuildingBlock<T> {

	private T value;
	
	public StorageBuildingBlockImpl() {
		super();
		this.value = null;
	}
	
    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;

    }

}
