package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.Stack;

import sf.codingcomp.blocks.CircularReferenceException;
import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
	private Stack<PolyBlock> blockStack;
	private int connections;
	public PolyBlockImpl()
	{
		blockStack = new Stack<>();
		blockStack.push(this);
		connections = 0;
	}
	
	public PolyBlockImpl(Stack<PolyBlock> blockStack)
	{
		this.blockStack = blockStack;
		this.blockStack.push(this);
		connections = 0;
	}
	
    @Override
    public Iterator<PolyBlock> iterator() {
        return blockStack.iterator();
    }
    
    public Stack<PolyBlock> getBlockStack() {
    	return blockStack;
    }

    @Override
    public void connect(PolyBlock aPolyBlock) throws CircularReferenceException {
        blockStack.add(aPolyBlock);
        connections++;
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
    	blockStack.remove(aPolyBlock);
    	connections--;
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
    	return blockStack.contains(aPolyBlock);
    }
    
    @Override
    public int connections() {
    	return connections;
    }

    @Override
    public int size() {
        return blockStack.size();
    }

    @Override
    public PolyBlock copy() {
        return new PolyBlockImpl(blockStack);
    }

}
