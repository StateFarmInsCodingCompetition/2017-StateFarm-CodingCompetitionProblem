package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
/**
 * 
 * A building block may connect to one other building block above, and one below.
 * 
 * @author  Saul Lopez
 * @version 1.0
 * @since   2017-10-07 
*/
public class BuildingBlockImpl implements BuildingBlock {
	
	//attribs/variables
	private ArrayList<BuildingBlock> bBlocksList; //[top...bottom]
	private Iterator<BuildingBlock> iterator;	
	
	//constructor
	/**
	 * Constructor. no-arg.
	 * 
	 */
	public BuildingBlockImpl() {
		bBlocksList = new ArrayList<BuildingBlock>();
		iterator = bBlocksList.iterator();
	}

	//methods - those with override already have docs in interface.
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
        return iterator;
    }

    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
    	if (stackableHandle(b)) {
    		return; //end
    	}
    	
    	//ex: a.stackOver(b);
    	iterator = bBlocksList.iterator(); //reset iterator
    	BuildingBlock obj = this;
    	//search in arraylist for passed BuildingBlock and place a over b.    	
    	int index = 0;
    	while(iterator.hasNext()) {
	    	if (iterator.equals(b)) {
	    		
	    		break;
	    	}
	    	iterator.next();
	    	index++;
    	}
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
    	stackableHandle(b);

    }

    @Override
    public BuildingBlock findBlockUnder() {
        // TODO Auto-generated method stub
    	BuildingBlock bBlock;
    	bBlock = iterator.next();
        return bBlock;
    }

    @Override
    public BuildingBlock findBlockOver() {
        // TODO Auto-generated method stub
    	BuildingBlock bBlock;
    	bBlock = iterator.next();
        return bBlock;
    }
    
    private boolean stackableHandle(BuildingBlock b) {
    	if(bBlocksList.isEmpty()) {
    		System.out.println("No blocks exist--thus, " + b + "does not exist.");
        	return true;    		
    	}
    	return false;
    }

}
