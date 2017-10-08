package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {   
	public ArrayList<PolyBlock> connected = new ArrayList<PolyBlock>();
	public int iterPos;
	public boolean eqInProgress;
	private HashSet<Long> visited;
    @Override 
    public Iterator<PolyBlock> iterator() { // bfs  
        // TODO Auto-generated method stub
    	return new PBIterator(this);
    }
  

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	for (PolyBlock x: this.connected) {
    		if (x == aPolyBlock) {
    			return;
    		}
    	}
    	if(!(this==aPolyBlock) && aPolyBlock != null){
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
	    		if(itr.next()==aPolyBlock){
	    			itr.remove();
	    		}
	    	}	    	
	    	Iterator itr1 = ((PolyBlockImpl) aPolyBlock).connected.iterator();
	    	while(itr1.hasNext()){
	    		if(itr1.next()==this){
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
    		if(itr.next()==(aPolyBlock)){
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
				if(it.next()==(pb)){
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
        
    public void copyrec(PolyBlockImpl pos, PolyBlockImpl target, HashMap<PolyBlockImpl, PolyBlockImpl> map) {
    	for (int i=0; i<pos.connected.size(); ++i) {
    		if (map.containsKey(pos.connected.get(i))) {
    			target.connected.add((PolyBlock)(map.get(pos.connected.get(i))));
    		} else {
    			PolyBlockImpl pb = new PolyBlockImpl();
    			target.connected.add(pb);
    			map.put((PolyBlockImpl)pos.connected.get(i), pb);
    			copyrec((PolyBlockImpl)pos.connected.get(i), pb, map);
    		}
    	}
    }

    @Override
    public PolyBlock copy() {
    	PolyBlockImpl pbi = new PolyBlockImpl();
    	HashMap<PolyBlockImpl, PolyBlockImpl> map = new HashMap<PolyBlockImpl, PolyBlockImpl>();
    	map.put(this, pbi);
    	copyrec(this, pbi, map);
    	return pbi;
    }
    
    private boolean testeq(PolyBlockImpl a, PolyBlockImpl b) {
    	long x = (1L<<32)*a.hashCode()+b.hashCode();
    	if (visited.contains(x)) {
    		return true;
    	}
    	visited.add(x);
    	if (a.connected.size() != b.connected.size()) {
    		return false;
    	}
    	for (int i=0; i<a.connected.size(); ++i) {
    		if (!testeq((PolyBlockImpl)a.connected.get(i), (PolyBlockImpl)b.connected.get(i))) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public boolean equals(Object other) {
    	visited = new HashSet<Long>();
    	if (connected.size() != ((PolyBlockImpl)other).connected.size()) {
    		return false;
    	}
    	for (int i=0; i<connected.size(); ++i) {
    		if (!testeq((PolyBlockImpl)(connected.get(i)), (PolyBlockImpl)(((PolyBlockImpl)other).connected.get(i)))) {
    			return false;
    		}
    	}
    	return true;
    }
    
    private int recursivelyNumber(PolyBlockImpl node, int number, HashMap<PolyBlockImpl, Integer> map) {
    	if (map.containsKey(node))
    		return number;
    	map.put(node, number++);
    	for (PolyBlock child: node.connected) {
    		number = recursivelyNumber((PolyBlockImpl)child, number, map);
    	}
    	return number;
    }
    
    public void printGraphStats() {
    	System.out.printf("This PolyBlock includes a total of %d vertices.\n", size());
    	System.out.printf("If we number the vertices 1 through %d, we get the following adjacency lists.\n", size());
    	HashMap<PolyBlockImpl, Integer> numbers = new HashMap<PolyBlockImpl, Integer>();
    	recursivelyNumber(this, 1, numbers);
    	PolyBlockImpl arr[] = new PolyBlockImpl[size()];
    	for (PolyBlockImpl node: numbers.keySet()) {
    		arr[numbers.get(node)-1] = node;
    	}
    	for (int i=0; i<arr.length; ++i) {
    		System.out.printf("%d: [", i+1);
    		int y=0;
    		for (PolyBlock pb: arr[i].connected) {
    			System.out.printf("%d", numbers.get(((PolyBlockImpl)pb)));
    			if (++y != arr[i].connected.size()) {
    				System.out.printf(", ");
    			} else {
    				System.out.printf("]\n");
    			}
    		}
    	}
    }
}
