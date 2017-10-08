package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	public BuildingBlock over, under;
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
    	return new BBIterator(this);
    }

    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
    	
    	if (this.under != null) {
    		((BuildingBlockImpl)this.under).over = null;
    		this.under = null;
    	}
    	if (b != null && ((BuildingBlockImpl)b).over != null) {
    		((BuildingBlockImpl)(((BuildingBlockImpl)b).over)).under = null;
    		((BuildingBlockImpl)b).over = null;
    	}
    	BuildingBlock x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockOver();
    	}
    	x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockUnder();
    	}
    	this.under = b;
    	if (b != null) {
    		((BuildingBlockImpl)b).over = this;
    	}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
    	
    	if (this.over != null) {
    		((BuildingBlockImpl)this.over).under = null;
    		this.over = null;
    	}
    	if (b != null && ((BuildingBlockImpl)b).under != null) {
    		((BuildingBlockImpl)(((BuildingBlockImpl)b).under)).over = null;
    		((BuildingBlockImpl)b).under = null;
    	}
    	BuildingBlock x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockOver();
    	}
    	x = this;
    	while (x != null) {
    		if (x == b) {
    			throw new CircularReferenceException();
    		}
    		x = x.findBlockUnder();
    	}
    	this.over = b;
    	if (b != null) {
    		((BuildingBlockImpl)b).under = this;
    	}
    }

    @Override
    public BuildingBlock findBlockUnder() {
        // TODO Auto-generated method stub
        return this.under;
    }

    @Override
    public BuildingBlock findBlockOver() {
        // TODO Auto-generated method stub
        return this.over;
    }
    
    public static void printConnections(ArrayList<BuildingBlock> A) {
    	if (0 == A.size()) {
    		System.out.println("The given ArrayList is empty.");
    		return;
    	}
    	System.out.println("Here are the hashCode values of the BuildingBlock objects in the given ArrayList:");
    	System.out.printf("[");
    	for (int i=0; i<A.size()-1; ++i) {
    		System.out.printf("%d, ", A.get(i).hashCode());
    	}
    	System.out.printf("%d]\n", A.get(A.size()-1).hashCode());
    	System.out.println("Here are the connections between the BuildingBlocks in the ArrayList (denoted by their hashCode values):");
    	HashSet<BuildingBlock> hs = new HashSet<BuildingBlock>();
    	HashMap<Integer, Integer> sizeCount = new HashMap<Integer, Integer>();
    	for (BuildingBlock bb: A) {
    		if (hs.contains(bb.iterator().next())) {
    			continue;
    		}
    		int size = 0;
    		for (BuildingBlock chainLink: bb) {
    			hs.add(chainLink);
    			System.out.printf("%d --- ", chainLink.hashCode());
    			++size;
    		}
    		if (!sizeCount.containsKey(size)) {
    			sizeCount.put(size, 0);
    		}
    		sizeCount.put(size, sizeCount.get(size)+1);
    		System.out.println("null");
    	}
    	System.out.println("Here are the number of stacks of each size:");
    	int[] sizes = new int[sizeCount.keySet().size()];
    	int pos = 0;
    	for (int x: sizeCount.keySet()) {
    		sizes[pos++] = x;
    	}
    	Arrays.sort(sizes);
    	for (int i=0; i<sizes.length; ++i) {
    		System.out.printf("%d: %d\n", sizes[i], sizeCount.get(sizes[i]));
    	}
    }

}
