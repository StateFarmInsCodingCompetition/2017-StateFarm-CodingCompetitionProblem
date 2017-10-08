package sf.codingcomp.blocks;


/**
 * 
 * Like a BuildingBlock, but it also manages a value
 *
 * @param <T>
 */
public interface StorageBuildingBlock<T> extends BuildingBlock {
	
	T getValue();

	void setValue(T value);
	
}
