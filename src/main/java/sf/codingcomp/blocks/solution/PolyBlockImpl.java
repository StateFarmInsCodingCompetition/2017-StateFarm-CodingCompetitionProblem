package sf.codingcomp.blocks.solution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	Set<PolyBlock> connections;
	public boolean isChain = false; 

	public PolyBlockImpl() {
		connections = new HashSet<PolyBlock>();
	}

	@Override
	public Iterator<PolyBlock> iterator() {
		// TODO Auto-generated method stub
		return null;
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
		isChain = true; 
		if(connections.size() < 1) {
			isChain = false; 
			return 1; 
		}
		
		int largest = 0;
		for (PolyBlock b : connections) {
			PolyBlockImpl _temp = (PolyBlockImpl) b; 
			if(!_temp.isChain && b.size() > largest) {
				largest = b.size(); 
			}
		}
		isChain = false; 
		return 1 + largest;
	}

	@Override
	public PolyBlock copy() {
//		return this; 
		
		PolyBlockImpl _copy = new PolyBlockImpl(); 
		for(PolyBlock b : connections) {
			_copy.connect(b);
		}
		return _copy; 
	}

}
