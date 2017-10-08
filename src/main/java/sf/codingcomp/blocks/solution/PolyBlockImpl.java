package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {   
	public ArrayList<PolyBlock> connected = new ArrayList<PolyBlock>();
	public int iterPos;
    @Override 
    public Iterator<PolyBlock> iterator() { // bfs  
        // TODO Auto-generated method stub
    	return new PBIterator(this);
    	/*
    	Iterator<PolyBlock> itr = new Iterator<PolyBlock>(){

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public PolyBlock next() {
				// TODO Auto-generated method stub
				return null;
			}
    		
    	};
    	
        return itr;*/
    }
  

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	for (PolyBlock x: this.connected) {
    		if (x == aPolyBlock) {
    			return;
    		}
    	}
    	if(!this.equals(aPolyBlock) && aPolyBlock != null){
    		this.connected.add(aPolyBlock);
        	((PolyBlockImpl) aPolyBlock).connected.add(this);
    	}	
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	if(aPolyBlock != null){
	    	Iterator itr = this.connected.iterator();
	    	while(itr.hasNext()){
	    		if(itr.next().equals(aPolyBlock)){
	    			itr.remove();
	    		}
	    	}	    	
	    	Iterator itr1 = ((PolyBlockImpl) aPolyBlock).connected.iterator();
	    	while(itr1.hasNext()){
	    		if(itr1.next().equals(this)){
	    			itr1.remove();
	    		}
	    	}
    	}
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	Iterator itr = this.connected.iterator();
    	while(itr.hasNext()){
    		if(itr.next().equals(aPolyBlock)){
    			return true;
    		}
    	}
        return false;
    }

    @Override
    public int connections() {
        // TODO Auto-generated method stub
        return this.connected.size();
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
    	HashSet<PolyBlock> hs = new HashSet<PolyBlock>();
    	hs.add(this);
    	Stack<PolyBlock> st = new Stack<PolyBlock>();
    	for(PolyBlock pb: this.connected){
    		st.push(pb);  		
    	}
    	while(st.size() > 0){
			PolyBlock pb = st.pop();
			Iterator it = hs.iterator();
			boolean alreadyFound = false;
			while(it.hasNext()){
				if(it.next().equals(pb)){
					alreadyFound = true;
					break;
				}
			}
			
			if(!alreadyFound){
				hs.add(pb);
				for(PolyBlock pb1: ((PolyBlockImpl) pb).connected){
		    		st.push(pb1);  		
		    	}
			}
		}
        return hs.size();
    }
    
    public void dfs(HashSet hs, PolyBlock pb){
    	
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
    	PolyBlock copiedPolyBlock = new PolyBlockImpl(); 
    	
    	((PolyBlockImpl) copiedPolyBlock).connected = (ArrayList<PolyBlock>) this.connected.clone();
    	
        return copiedPolyBlock;
    }

}
