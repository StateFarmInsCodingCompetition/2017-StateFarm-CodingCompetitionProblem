package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.Stack;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	private Stack<BuildingBlock> blockStack;
	public BuildingBlockImpl() {
		blockStack = new Stack<>();
		blockStack.push(this);
	}
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        return blockStack.iterator();
    }
    
    public Stack<BuildingBlock> getBlockStack() {
    	return blockStack;
    }

    @Override
    public void stackOver(BuildingBlock b) throws CircularReferenceException {
    	if (b.findBlockOver() == null &&
        	!impl(b).getBlockStack().contains(this) &&
        	!this.getBlockStack().contains(impl(b)))
        {
        	Stack<BuildingBlock> otherStack = impl(b).getBlockStack();
        	
        	// Add this block to the top of
        	// the stack contained in the block b
        	otherStack.push(this);
        }
        else
        	throw new CircularReferenceException();
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        if (b.findBlockUnder() == null)
    	{
        	// Create a new, blank stack, and
        	// declare otherStack for the stack
        	// under the BuildingBlock b
        	Stack<BuildingBlock> myStack = new Stack<>(),
        						 otherStack = impl(b).getBlockStack();
        	
        	// Add the stack contained in the
        	// block b to this block (thereby
        	// stacking this block "under" block
        	// b
        	myStack.push(this);
        	for (BuildingBlock bb : otherStack)
        		myStack.push(bb);
    	}
    }
    
    /**
     * Cast the building block b as its implemented
 	 * form and add this block to b.
     * @param b the building block to convert to its
     * implemented form
     * @return
     */
    private BuildingBlockImpl impl(BuildingBlock b)
    {
    	return (BuildingBlockImpl)b;
    }

    @Override
    public BuildingBlock findBlockUnder() {
    	int index = getBlockStack().indexOf(this);
        if (index == 0)
        	return null;
        else
        	return getBlockStack().get(index - 1);
    }

    @Override
    public BuildingBlock findBlockOver() {
    	int index = getBlockStack().indexOf(this);
        if (index == getBlockStack().size() - 1)
        	return null;
        else
        	return getBlockStack().get(index + 1);
    }

}
