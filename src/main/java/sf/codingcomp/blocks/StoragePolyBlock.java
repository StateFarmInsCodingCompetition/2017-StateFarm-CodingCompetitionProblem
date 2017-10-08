package sf.codingcomp.blocks;


/**
 * Like a PolyBlock, except that it also manages a value.
 *
 */
public interface StoragePolyBlock<T> extends PolyBlock {
	
	T getValue();

	void setValue(T value);
}
