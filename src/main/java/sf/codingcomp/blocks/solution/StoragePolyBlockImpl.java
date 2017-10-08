package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StoragePolyBlock;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {
	T val;
    @Override
    public T getValue() {
        // TODO Auto-generated method stub
        return val;
    }

    @Override
    public void setValue(T value) {
        // TODO Auto-generated method stub
    	val = value;
    }

}
