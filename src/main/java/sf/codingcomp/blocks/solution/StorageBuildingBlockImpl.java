package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StorageBuildingBlock;

public class StorageBuildingBlockImpl<T> extends BuildingBlockImpl implements StorageBuildingBlock<T> {
	
	/**
	 * The value to store within this building block
	 */
	private T value;
	
	/**
	 * Creates a new building block with no value
	 */
	public StorageBuildingBlockImpl() {
		
	}
	
	/**
	 * Creates a new building block with the specified value
	 * @param value The value for this building block
	 */
	public StorageBuildingBlockImpl(T value) {
		this.value = value;
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
