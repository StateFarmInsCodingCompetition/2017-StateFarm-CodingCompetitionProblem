package sf.codingcomp.blocks.solution;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;



public class BlockIterator<BuildingBlock> implements Iterator<BuildingBlock>{
	
	
	public BlockIterator(ArrayList<BuildingBlock> block) {
		
		ArrayList<BuildingBlock> stacked = new ArrayList<BuildingBlock>(block);
		Iterator check = stacked.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return true;
				
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
