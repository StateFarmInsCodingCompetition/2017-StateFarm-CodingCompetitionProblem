package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.StoragePolyBlock;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {

	/**
	 * The value to store within this poly block
	 */
	private T value;
	
	/**
	 * Creates a new poly block with no value
	 */
	public StoragePolyBlockImpl() {
		
	}
	
	/**
	 * Creates a new poly block with the specified value
	 * @param value The value for this poly block
	 */
	public StoragePolyBlockImpl(T value) {
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
