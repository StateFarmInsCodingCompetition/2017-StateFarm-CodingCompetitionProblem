package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StoragePolyBlock;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {
	
	private T value;

	@Override
	public T getValue(){
		return value;
	}

	@Override
	public void setValue(T value){
		this.value = value;
	}

}
