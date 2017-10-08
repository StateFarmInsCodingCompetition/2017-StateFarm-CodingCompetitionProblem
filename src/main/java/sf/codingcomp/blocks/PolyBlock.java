package sf.codingcomp.blocks;


/**
 * 
 * Object which can have an unlimited amount of connections to other PolyBlocks,
 * but can be sequentially iterated.
 * 
 */
public interface PolyBlock extends Iterable<PolyBlock> {

	/**
	 * Connect the current PolyBlock to the passed PolyBlock.
	 * 
	 * @param aPolyBlock
	 *            the PolyBlock to connect to
	 */
	void connect(PolyBlock aPolyBlock);

	/**
	 * Disconnect from the passed PolyBlock.
	 * 
	 * @param aPolyBlock
	 *            the PolyBlock to disconnect from
	 */
	void disconnect(PolyBlock aPolyBlock);

	/**
	 * Whether the current PolyBlock is directly connected to another
	 * PolyBlock.
	 * 
	 * @param aPolyBlock
	 *            the PolyBlock to look for
	 * @return whether this PolyBlock has a connection to the passed
	 *         PolyBlock
	 */
	boolean contains(PolyBlock aPolyBlock);

	/**
	 * 
	 * @return the number of connections the PolyBlock your invoking has
	 */
	int connections();

	/**
	 * 
	 * @return the total number of continuously connected PolyBlocks
	 */
	int size();
	
	/**
	 * Returns a copy of all connected PolyBlocks
	 * @return
	 */
	PolyBlock copy();

}
