package sf.codingcomp.blocks.solution;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
	Map<PolyBlockImpl, LinkedList<PolyBlockImpl>> connections = new HashMap<>();
	
    @Override
    public Iterator<PolyBlock> iterator() {
        return new PBIterator();
    }
    
    private class PBIterator implements Iterator<PolyBlock> {
    	PolyBlockImpl curr;
    	
    	public PBIterator() {
    		curr = new PolyBlockImpl();
    	}
    	
    	public boolean hasNext() {
    		return connections.get(curr).size() != 0;
    	}
    	
    	public PolyBlock next() {
    		PolyBlockImpl c = curr;
    		curr = connections.get(curr).getFirst();
    		return c;
    	}
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
    	PolyBlockImpl ablock = (PolyBlockImpl) aPolyBlock;
        if(this != ablock) {
        	if(connections.containsKey(this)) {
	        	if(ablock != null) {
	        	LinkedList<PolyBlockImpl> temp = connections.get(this);
	        	temp.add(ablock);
	        	connections.put(this, temp);
	        	}
	        } else {
	        	if(ablock != null) {
	        		LinkedList<PolyBlockImpl> temp = new LinkedList<>();
	        		temp.add(ablock);
	            	connections.put(this, temp);
	        	}
	        }
	        if (connections.containsKey(ablock)) {
	        	if(ablock != null) {
	        		LinkedList<PolyBlockImpl> temp = connections.get(ablock);
	            	temp.add(this);
	            	connections.put(ablock, temp);
	        	}
	        } else {
	        	if(ablock != null) {
	        		LinkedList<PolyBlockImpl> temp = new LinkedList<>();
	            	temp.add(this);
	            	connections.put(ablock, temp);
	        	}
	        }
        }
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
    	PolyBlockImpl ablock = (PolyBlockImpl) aPolyBlock;
        if(connections.containsKey(this)) {
        	LinkedList<PolyBlockImpl> temp = connections.get(this);
        	temp.remove(ablock);
        	connections.put(this, temp);
        }
        if (connections.containsKey(ablock)) {
        	LinkedList<PolyBlockImpl> temp = connections.get(ablock);
        	temp.remove(this);
        	connections.put(ablock, temp);
        }
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
    	PolyBlockImpl ablock = (PolyBlockImpl) aPolyBlock;
        if(connections.containsKey(ablock)) {
        	return connections.get(ablock).contains(this);
        } else if(connections.containsKey(this)){
        	return connections.get(this).contains(ablock);
        }
        return false;
    }

    @Override
    public int connections() {
    	if(!connections.containsKey(this)) {
    		return 0;
    	} else if(connections.get(this).isEmpty()) {
    		return 0;
    	}
    	return connections.get(this).size();
    }

    @Override
    public int size() {
    	if(connections.size() == 0) {
    		return 1;
    	}
        return connections.size();
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

}