package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StorageBuildingBlock;

public class StorageBuildingBlockImpl<T> extends BuildingBlockImpl implements StorageBuildingBlock<T> {
	
	T value;
	
    @Override
    public T getValue() {
        // TODO Auto-generated method stub
        return value;
    }

    @Override
    public void setValue(T value) {
        // TODO Auto-generated method stub
    		this.value = value;

    }
    
    @Override
    public String toString() {
    		return value.toString();
    }

}
