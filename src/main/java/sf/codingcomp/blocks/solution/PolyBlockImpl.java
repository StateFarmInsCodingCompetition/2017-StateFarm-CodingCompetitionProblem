package sf.codingcomp.blocks.solution;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;
import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
	static int counter = 0;
	private int id;
	HashSet<PolyBlock> connectionSet = new HashSet<>();
	
	public PolyBlockImpl() {
		id = counter++;
	}
	
	public PolyBlockImpl(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	
	
	@Override
    public Iterator<PolyBlock> iterator() {
        // TODO Auto-generated method stub
        return new PolyBlockIterator();
    }

    public class PolyBlockIterator implements Iterator<PolyBlock> {   	
        	//Iterator<PolyBlock> connectionIter = connectionSet.iterator();
        	private HashSet<PolyBlock> visited = new HashSet<>();
        private Stack<PolyBlock> traversed = new Stack<>();
        
        //PolyBlockImpl.this.getClass().sizeHelper(visited, PolyStackImpl.this, traversed);
        
        public PolyBlockIterator() {
        		traversed.push(PolyBlockImpl.this);
        }
        
        @Override
        public boolean hasNext() {
        		if (traversed.isEmpty()) return false;
        		else return true;
        }

        @Override
        public PolyBlock next() {
        		if (hasNext()) {
        			PolyBlock curBlock = traversed.pop();
        			visited.add(curBlock);
        			PolyBlockImpl curBlockImpl = (PolyBlockImpl) curBlock;
        			for (PolyBlock pb : curBlockImpl.connectionSet) {
        				if (!visited.contains(pb) && !traversed.contains(pb)) {
        					traversed.push(pb);
        				}
        			}
        			return curBlock;
        		} else {
        			throw new NoSuchElementException();
        		}
        		
        		
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }   
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
    		HashSet<Integer> alreadyCopied = new HashSet<>();
        PolyBlockImpl temp = new PolyBlockImpl(this.id);
        alreadyCopied.add(this.id);
        for (PolyBlock conn : connectionSet) {
        		temp.connect(((PolyBlockImpl) conn).copyHelper(alreadyCopied));
        }
        return temp;
    }
    
    public PolyBlock copyHelper(HashSet<Integer> alreadyCopied) {
        PolyBlockImpl temp = new PolyBlockImpl(this.id);
        for (PolyBlock conn : connectionSet) {
        		if (alreadyCopied.contains(((PolyBlockImpl) conn).id)) {
        			
        			continue;
        		}
        		alreadyCopied.add(this.id);
        		temp.connect(((PolyBlockImpl) conn).copyHelper(alreadyCopied));
        }
        return temp;
    }
    
    @Override
    public boolean equals(Object toCompare) {
    		if (this == toCompare) {
    			return true;
    		} else if (toCompare == null) {
    			return false;
    		} else if (!(toCompare instanceof PolyBlockImpl)) {
    			return false;
    		} else {
    			PolyBlockImpl newBlock = (PolyBlockImpl) toCompare;
    			return this.connectionSet.equals(newBlock.connectionSet) && this.id == newBlock.id;
    		}
    }

}
