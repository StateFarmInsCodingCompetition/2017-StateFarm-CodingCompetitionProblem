package sf.codingcomp.blocks.solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	Set<PolyBlock> connections;
	Set<PolyBlock> seen;

	public PolyBlockImpl() {
		connections = new HashSet<PolyBlock>();
	}

	@Override
	public Iterator<PolyBlock> iterator() {
		Stack<PolyBlock> toVisitInit = new Stack<PolyBlock>();
		toVisitInit.add(this);
		HashSet<PolyBlock> seenIteratorInit = new HashSet<PolyBlock>();
		seenIteratorInit.add(this);
		
		return new Iterator<PolyBlock>() {
			Stack<PolyBlock> toVisit = toVisitInit;
			HashSet<PolyBlock> seenIterator = seenIteratorInit;
			
			@Override
			public boolean hasNext() {
				return (toVisit.size() > 0);
			}

			@Override
			public PolyBlock next() {
				PolyBlockImpl visitNext = (PolyBlockImpl) toVisit.peek();
				while (!toVisit.isEmpty()) {
					for (PolyBlock p : visitNext.connections) {
						if (!seenIterator.contains(p)) {
							toVisit.add(p);
							seenIterator.add(p);
							return visitNext;
						}
					}
					toVisit.pop();
				}
				return visitNext;
			}
			
		};
	}

	@Override
	public void connect(PolyBlock aPolyBlock) {
		if (aPolyBlock != null && aPolyBlock != this) {
			if (!contains(aPolyBlock)) {
				connections.add(aPolyBlock);
			}
			if (!aPolyBlock.contains(this)) {
				aPolyBlock.connect(this);
			}
		}
	}

	@Override
	public void disconnect(PolyBlock aPolyBlock) {
		if (aPolyBlock != null) {
			if (contains(aPolyBlock)) {
				connections.remove(aPolyBlock);
			}
			if (aPolyBlock.contains(this)) {
				aPolyBlock.disconnect(this);
			}
		}
	}

	@Override
	public boolean contains(PolyBlock aPolyBlock) {
		return connections.contains(aPolyBlock);
	}

	@Override
	public int connections() {
		return connections.size();
	}

	@Override
	public int size() {
		seen = new HashSet<PolyBlock>();
		bfs(this);
		return seen.size();
	}

	@Override
	public PolyBlock copy() {
//		return this; 
		Map <PolyBlockImpl, PolyBlockImpl> copy = new HashMap <PolyBlockImpl, PolyBlockImpl> (); 
		
		//Creates a map of every existing connected polyblock mapped to the hash of the existing block 
		for(PolyBlock p : this) {
			copy.put((PolyBlockImpl) p, new PolyBlockImpl()); 
		} 
		
		//Iterates through polyblocks and their connections to re-create the connections in the copy 
		for(PolyBlock p : this) {
			for(PolyBlock q : ( (PolyBlockImpl) p).connections) {
				copy.get(p).connect(q); 
			}
		}
		
		//returns copy of this object 
		return copy.get(this); 
	}
	
	private void bfs (PolyBlock b) {
		seen.add(b);
		for (PolyBlock cb : ((PolyBlockImpl) b).connections) {
			if (!seen.contains(cb)) {
				seen.add(cb);
				bfs(cb);
			}
		}
		
		return;
	}
}
