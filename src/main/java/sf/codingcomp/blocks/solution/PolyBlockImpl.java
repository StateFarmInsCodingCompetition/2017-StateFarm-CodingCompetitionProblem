package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {  
	public ArrayList<PolyBlock> connected = new ArrayList<PolyBlock>();
    @Override
    public Iterator<PolyBlock> iterator() { // bfs
        // TODO Auto-generated method stub
    	
        return null;
    }
  

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	this.connected.add(aPolyBlock);
    	((PolyBlockImpl) aPolyBlock).connected.add(this);
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	Iterator itr = this.connected.iterator();
    	while(itr.hasNext()){
    		if(itr.next().equals(aPolyBlock)){
    			itr.remove();
    		}
    	}
    	
    	Iterator itr1 = ((PolyBlockImpl) aPolyBlock).connected.iterator();
    	while(itr1.hasNext()){
    		if(itr1.next().equals(this)){
    			itr1.remove();
    		}
    	}
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    	Iterator itr = this.connected.iterator();
    	while(itr.hasNext()){
    		if(itr.next().equals(aPolyBlock)){
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
        return 0;
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
    	PolyBlock copiedPolyBlock = new PolyBlockImpl(); 
    	
    	((PolyBlockImpl) copiedPolyBlock).connected = (ArrayList) this.connected.clone();
        return null;
    }

}
