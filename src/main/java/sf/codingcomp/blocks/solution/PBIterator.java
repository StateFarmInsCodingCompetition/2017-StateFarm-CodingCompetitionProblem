package sf.codingcomp.blocks.solution;

import java.util.*;

import sf.codingcomp.blocks.PolyBlock;

public class PBIterator implements Iterator<PolyBlock> {
	PolyBlockImpl block;
	ArrayList<PolyBlock> arr;
	HashSet<Integer> codes;
	int pos;
	public PBIterator(PolyBlock block) {
		((PolyBlockImpl) block).iterPos = 0;
		this.block = (PolyBlockImpl)block;
		arr = new ArrayList<PolyBlock>();
		codes = new HashSet<Integer>();
		codes.clear();
		dfsadd(this.block);
		pos = 0;
	}
	
	private void dfsadd(PolyBlockImpl b) {
		if (codes.contains(b.hashCode())) {
			return;
		}
		codes.add(b.hashCode());
		arr.add(b);
		for (PolyBlock child: b.connected) {
			dfsadd((PolyBlockImpl)child);
		}
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return pos < arr.size();
	}

	@Override
	public PolyBlock next() {
		// TODO Auto-generated method stub
		return arr.get(pos++);
	}

}
