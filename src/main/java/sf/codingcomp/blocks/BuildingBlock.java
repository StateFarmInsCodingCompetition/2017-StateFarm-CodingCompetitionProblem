package sf.codingcomp.blocks;


import sf.codingcomp.blocks.solution.BuildingBlockImpl;

import java.util.List;

/**
 * 
 * A building block may connect to one other building block above, and one below.
 * 
 */
public interface BuildingBlock extends Iterable<BuildingBlock>{

    /**
     *
     * @return the stack of blocks, from bottom to top
     */
	List<BuildingBlockImpl> getStack();
	
	/**
	 * 
	 * @param b	puts this object over the passed object
	 */
	void stackOver(BuildingBlock b);
	
	/**
	 * 
	 * @param b	puts this object under the passed object
	 */
	void stackUnder(BuildingBlock b);

	/**
	 * 
	 * @return	the <code>BuildingBlock</code> under the current one
	 */
	BuildingBlock findBlockUnder();
	
	/**
	 * 
	 * @return	the <code>BuildingBlock</code> over the current one
	 */
	BuildingBlock findBlockOver();
	
}
