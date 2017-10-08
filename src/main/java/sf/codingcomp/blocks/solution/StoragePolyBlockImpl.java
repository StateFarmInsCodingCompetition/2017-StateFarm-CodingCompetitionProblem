package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StoragePolyBlock;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {

	T val = null;

	@Override
	public T getValue() {
		return val;
	}

	@Override
	public void setValue(T value) {
		val = value;
	}
}
