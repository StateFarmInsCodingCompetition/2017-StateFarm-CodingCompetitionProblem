package sf.codingcomp.blocks.solution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import sf.codingcomp.blocks.CircularReferenceException;
import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
	
	HashSet<PolyBlock> connectionSet = new HashSet<>();
	
    @Override
    public Iterator<PolyBlock> iterator() {   	
	    	Iterator<PolyBlock> it = new Iterator<PolyBlock>() {
	            private int currentIndex = 0;
	            	Iterator<PolyBlock> connectionIter = connectionSet.iterator();
	            	private HashSet<PolyBlock> visited = new HashSet<>();
		        private Stack<PolyBlock> traversed = new Stack<>();
		        PolyBlockImpl.this.getClass().sizeHelper(visited, PolyStackImpl.this, traversed);
		        
	            @Override
	            public boolean hasNext() {
	            		if (currentIndex == 0) return true;
	            		else {
	            			return connectionIter.hasNext();
	            		}
	            }
	
	            @Override
	            public PolyBlock next() {
	            		if (currentIndex == 0) {
	            			currentIndex++;
	            			return PolyBlockImpl.this;
	            		} else {
	            			currentIndex++;
	            			return connectionIter.next();
	            		}
	            }
	
	            @Override
	            public void remove() {
	                throw new UnsupportedOperationException();
	            }
	    	};    
	    return it;
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        if (aPolyBlock != null && aPolyBlock != this && !connectionSet.contains(aPolyBlock)) {
        		connectionSet.add(aPolyBlock);
        		((PolyBlockImpl) aPolyBlock).connectionSet.add(this);
        } 
//        else if (aPolyBlock == this) {
//        		throw new CircularReferenceException("Attempt to connect block to itself");
//        }
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        if (aPolyBlock != null && connectionSet.contains(aPolyBlock)) {
        		connectionSet.remove(aPolyBlock);
        		((PolyBlockImpl) aPolyBlock).connectionSet.remove(this);
        }
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return connectionSet.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return connectionSet.size();
    }

    @Override
    public int size() {
    		HashSet<PolyBlock> visited = new HashSet<>();
    		//Integer sizeCounter = new Integer(0);
    		Stack<PolyBlock> traversed = new Stack<>();
    		sizeHelper(visited, this, traversed);
        return traversed.size();
    }
    public void sizeHelper(HashSet<PolyBlock> visited, PolyBlock current, Stack<PolyBlock> traversed) {
    		if (!visited.contains(current)) {
    			traversed.push(current);
    			visited.add(current);
    			for (PolyBlock aBlock : ((PolyBlockImpl) current).connectionSet) {
    				if (!visited.contains(aBlock)) {
    					sizeHelper(visited, aBlock, traversed);
    				}
    			}
    		}
    }

    @Override
    public PolyBlock copy() {
//        PolyBlockImpl temp = new PolyBlockImpl();
//        for (PolyBlock conn : connectionSet) {
//        		temp.connectionSet.add(conn);
//        }
//        return temp;
    		return this;
    }

}
