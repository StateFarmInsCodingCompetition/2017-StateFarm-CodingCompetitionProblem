package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StoragePolyBlock;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {

	T data;
	
	public StoragePolyBlockImpl() {
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
