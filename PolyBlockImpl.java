package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	//A set of references maintaining the connections, it's a set
	//so that Java takes care of equivalent entries for us and so
	//it does this quickly
	private Set<PolyBlock> connections = new IdentityHashSet<PolyBlock>();

	@Override
	public void connect(PolyBlock pb){
		if(pb==this) return;//we can't connect a block to itself
		if(pb!=null){
			//add references to each other in each other's sets
			connections.add(pb);
			((PolyBlockImpl) pb).connections.add(this);
		}
	}

	@Override
	public void disconnect(PolyBlock pb){
		if(pb==this) return;//we can't disconnect a block from itself
		if(pb!=null){
			//remove references to each other in each both sets
			connections.remove(pb);
			((PolyBlockImpl) pb).connections.remove(this);
		}
	}

	@Override
	public boolean contains(PolyBlock pb){
		return pb!=null && connections.contains(pb);
	}

	@Override
	public int connections(){
		return connections.size();
	}

	@Override
	public int size(){
		return copyIntoList().size();
	}

	@Override
	public PolyBlock copy(){
		return copy(new IdentityHashMap<PolyBlock, PolyBlock>());
	}
	
	private PolyBlock copy(Map<PolyBlock, PolyBlock> copies){
		//make a new instance of PolyBlock
		PolyBlock copy = new PolyBlockImpl();
		
		//prevent infinite recursion
		copies.put(this, copy);
		
		//connect the new instance to copies of each of the blocks connected to this one
		for(PolyBlock pb : connections){
			if(copies.containsKey(pb)){
				copy.connect(copies.get(pb));
			}else{
				copy.connect(((PolyBlockImpl) pb).copy(copies));
			}
		}
		
		return copy;
	}

	//because doing a depth-first iteration is SO much easier using recursion and the call stack
	private List<PolyBlock> copyIntoList(){
		return copyIntoList(new ArrayList<PolyBlock>(), new IdentityHashMap<PolyBlock, Boolean>());
	}
	
	private List<PolyBlock> copyIntoList(List<PolyBlock> list, Map<PolyBlock, Boolean> visited){
		//add this to the list
		list.add(this);
		
		//make sure we don't infinitely recurse
		visited.put(this, true);
		
		//now add all of the connected blocks if they haven't been added already
		for(PolyBlock connection : connections){
			if(!visited.containsKey(connection) || !visited.get(connection)){
				((PolyBlockImpl) connection).copyIntoList(list, visited);
			}
		}
		
		return list;
	}
	
	@Override
	public Iterator<PolyBlock> iterator(){
		return copyIntoList().iterator();
	}

	//because APPARENTLY, the jUnits use this
	@Override
	public boolean equals(Object obj){
		if(obj==this) return true;
		if(!(obj instanceof PolyBlock)) return false;
		
		return equals((PolyBlock) obj, new IdentityHashMap<PolyBlock, Boolean>());
	}
	
	//make sure the connectivity of the graph is the same
	private boolean equals(PolyBlock obj, Map<PolyBlock, Boolean> visited){
		visited.put(this, true);
		
		if(connections()!=obj.connections()) return false;
		
		Iterator<PolyBlock> itr1 = connections.iterator();
		Iterator<PolyBlock> itr2 = ((PolyBlockImpl)obj).connections.iterator();
		
		while(itr1.hasNext()&&itr2.hasNext()){
			PolyBlockImpl pb1 = (PolyBlockImpl) itr1.next();
			PolyBlockImpl pb2 = (PolyBlockImpl) itr2.next();
			
			if(visited.containsKey(pb1) && visited.get(pb1)) continue;
			if(!pb1.equals(pb2, visited)) return false;
		}
		
		return true;		
	}

	@Override
	public int hashCode(){
		return hashCode(1, 31, new IdentityHashMap<PolyBlock, Boolean>());
	}
	
	public int hashCode(int result, int prime, Map<PolyBlock, Boolean> visited){
		
		int r = result*prime + connections();
		visited.put(this, true);
		
		for(PolyBlock pb : connections){
			if(!visited.containsKey(pb) || !visited.get(pb)){
				r = ((PolyBlockImpl) pb).hashCode(r, prime, visited);
			}
		}
		
		return r;
	}
	
}
