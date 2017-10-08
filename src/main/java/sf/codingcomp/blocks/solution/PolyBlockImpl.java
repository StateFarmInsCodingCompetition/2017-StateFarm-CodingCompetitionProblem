package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.ArrayList;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
	public ArrayList<PolyBlock> list;
	private int connections;
	private static int idnum = 0;
	public int id;

	public PolyBlockImpl() {
		this.list =  new ArrayList<PolyBlock>();
		connections = 0;
		this.id = ++idnum;
	}
	
    @Override
    public Iterator<PolyBlock> iterator() {
    	ArrayList<PolyBlock>tempList =  new ArrayList<PolyBlock>();
    	tempList.add(this);
    	PolyBlockImpl temp = null;
    	for(int i = 0; i < tempList.size(); i++) {
    		if(tempList.get(i) instanceof PolyBlockImpl) {
    			 temp = (PolyBlockImpl)tempList.get(i);
				for(PolyBlock block: temp.list){
				  if(!tempList.contains(block)) {
					  tempList.add((PolyBlockImpl)block);
				  }
				}
        	}
    	}
    	return tempList.iterator();
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
    	if(aPolyBlock == null || aPolyBlock == this)
    		return;
    	if(!this.list.contains(aPolyBlock)) {
        	if(this.list.add(aPolyBlock)) {
        		connections++;
            	aPolyBlock.connect(this);
        	}
    	}
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
    	if(aPolyBlock == this)
    		return;
    	if(this.list.contains(aPolyBlock)) {
        	if(this.list.remove(aPolyBlock)) {
        		connections--;
            	aPolyBlock.disconnect(this);
        	}
    	}
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
    	return this.list.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return connections;
    }

    @Override
    public int size() {
    	ArrayList<PolyBlock>tempList =  new ArrayList<PolyBlock>();
    	tempList.add(this);
    	PolyBlockImpl temp = null;
    	for(int i = 0; i < tempList.size(); i++) {
    		if(tempList.get(i) instanceof PolyBlockImpl) {
    			 temp = (PolyBlockImpl)tempList.get(i);
				for(PolyBlock block: temp.list){
				  if(!tempList.contains(block)) {
					  tempList.add((PolyBlockImpl)block);
				  }
				}
        	}
    	}
    	return tempList.size();
    }
    

    @Override
    public PolyBlock copy() {
    	PolyBlockImpl temp = new PolyBlockImpl();
    	temp.id = this.id;
    	int size = this.list.size();
    	for(int i = 0; i < size; i++) {
    		temp.connect(list.get(i));
    	}
        return temp;
    }
    
    @Override 
    public boolean equals(Object o) {
    	if(o == null)
    		return false;
    	if(this==o)
    		return true;
    	PolyBlockImpl temp = null;
    	if(o instanceof PolyBlockImpl) {
    		temp = (PolyBlockImpl) o;
    	}
    	else return false;
    	if(this.list.size() != temp.list.size())
    		return false;
    	for(int i = 0; i < this.list.size(); i++) {
    		PolyBlockImpl t1 = (PolyBlockImpl)temp.list.get(i);
    		PolyBlockImpl t2 = (PolyBlockImpl)this.list.get(i);
    		if(t1.id != t2.id)
    			return false;
    	}
    	if(this.id != temp.id) return false;
        return true;
    }
    


}
