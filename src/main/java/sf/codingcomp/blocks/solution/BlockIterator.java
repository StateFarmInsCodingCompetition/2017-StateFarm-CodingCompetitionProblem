package sf.codingcomp.blocks.solution;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;



public class BlockIterator<BuildingBlock> implements Iterator<BuildingBlock>{
	
	
	ArrayList<BuildingBlock> stacked;
	Iterator check;
	
	public BlockIterator(ArrayList<BuildingBlock> block) {
		
		stacked = new ArrayList<BuildingBlock>(block);
		check = stacked.iterator();
	}
	
	@Override
	public boolean hasNext() {
		if (check.hasNext())
		return true;
		else
			return false;
				
	}

	@Override
	public BuildingBlock next() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void remove() {
        
    }

}
