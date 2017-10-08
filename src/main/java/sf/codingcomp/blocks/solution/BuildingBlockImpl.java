package sf.codingcomp.blocks.solution;


import java.util.ArrayList;
import java.util.Iterator;


import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {
	
	
	ArrayList<BuildingBlock> blist;
	public BuildingBlockImpl(){
		blist = new ArrayList();
		blist.add(this);
		
	}
	public ArrayList<BuildingBlock> getList(){
		return blist;
	}
	public void setList(ArrayList<BuildingBlock> newList){
		blist = newList;
	}
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
    	return null;
    }
    	
    @Override
    public void stackOver(BuildingBlock b) {
        // TODO Auto-generated method stub
    	int loc = blist.indexOf(this);
    	
    	BuildingBlock temp = null;
    	if(loc - 1 != -1){
    		temp = blist.get(loc-1);
    	}
    	if(loc!=0 && blist.get(loc-1) == b){
    		return;
    	}
    		
    		if(b!=null){
	    		BuildingBlockImpl copy = (BuildingBlockImpl)b;
	    		ArrayList<BuildingBlock> otherList = copy.getList();
	    		for(BuildingBlock each : otherList){
	    			if(each==this){
	    				throw new CircularReferenceException();
	    			}
	    		}
	    		int otherBlockLoc = otherList.indexOf(b);
	    		while(blist.indexOf(this)!=0){
	    			blist.remove(0);
	    		}
	    		ArrayList<BuildingBlock> newList = new ArrayList();
	    		
	    		
	    		while(otherList.size()> otherBlockLoc+1){
	    			otherList.remove(otherList.size()-1);
	    		}
	    		newList.addAll(otherList);
		    		
	    		
	    		newList.addAll(blist);
	    		this.setList(newList);
	    		copy.setList(newList);
    		} else{
    			while(blist.indexOf(this)!=0){
	    			blist.remove(0);
	    		}	
    		}
    		if(temp != null){
    			if(temp.findBlockOver()==this)
    			temp.stackUnder(null);
    		}
    	
        
    }

    @Override
    public void stackUnder(BuildingBlock b) {
        // TODO Auto-generated method stub
	int loc = blist.indexOf(this);
    	
    	BuildingBlock temp = null;
    	if(loc + 1 != blist.size()){
    		temp = blist.get(loc+1);
    	}
    	
    	if(loc!= blist.size()-1 && blist.get(loc+1) == b){
    		return;
    	}
    		if(b!=null){
	    		BuildingBlockImpl copy = (BuildingBlockImpl)b;
	    		ArrayList<BuildingBlock> otherList = copy.getList();
	    		for(BuildingBlock each : otherList){
	    			if(each==this){
	    				throw new CircularReferenceException();
	    			}
	    		}
	    		int otherBlockLoc = otherList.indexOf(b);
	    		while(blist.size()> loc+1){
	    			blist.remove(blist.size()-1);
	    		}
	    		ArrayList<BuildingBlock> newList = new ArrayList();
	    		
	    		
	    		while(otherList.indexOf(b)!= 0){
	    			otherList.remove(0);
	    		}
	    		newList.addAll(blist);
		    		
	    		
	    		newList.addAll(otherList);
	    		this.setList(newList);
	    		copy.setList(newList);
    		} else{
    			while(blist.size()> loc+1){
	    			blist.remove(blist.size()-1);
	    		}
    		}
			if(temp != null){
				if(temp.findBlockOver()==this)
				temp.stackUnder(null);
			}
    	
    	
    }

    @Override
    public BuildingBlock findBlockUnder() {
        // TODO Auto-generated method stub
    	int loc = blist.indexOf(this);
    	if(loc==0){
    		return null;
    	}
    	else{
    		return blist.get(loc-1);
    	}
    }

    @Override
    public BuildingBlock findBlockOver() {
        // TODO Auto-generated method stub
        int loc = blist.indexOf(this);
        if(loc==blist.size()-1){
        	return null;
        }
        else{
        	return blist.get(loc+1);
        }
    }
    
}

