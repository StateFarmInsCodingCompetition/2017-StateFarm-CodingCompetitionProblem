package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StoragePolyBlock;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {
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

}
