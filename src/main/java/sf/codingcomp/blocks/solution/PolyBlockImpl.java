package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.PolyBlock;
/**
 * 
 * Object which can have an unlimited amount of connections to other PolyBlocks,
 * but can be sequentially iterated.
 * 
 * @author  Saul Lopez
 * @version 1.0
 * @since   2017-10-07 
*/
public class PolyBlockImpl implements PolyBlock {

	//attribs/variables
	private ArrayList<PolyBlock> pBlocksList; //[top...bottom]
	private Iterator<PolyBlock> iterator;	

	//constructor
	/**
	 * Constructor. no-arg.
	 * 
	 */
	public PolyBlockImpl() {
		pBlocksList = new ArrayList<PolyBlock>();
		iterator = pBlocksList.iterator();
	}

	
    @Override
    public Iterator<PolyBlock> iterator() {
        // TODO Auto-generated method stub
        return iterator;
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int connections() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
