package sf.codingcomp.blocks.solution;

import java.util.Iterator;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.CircularReferenceException;

public class BuildingBlockImpl implements BuildingBlock {

	//wooooo linked lists
	private BuildingBlock top;
	private BuildingBlock bottom;

    @Override
    public void stackOver(BuildingBlock b){
    	//if we don't need to change anything, don't
    	if(b==bottom) return;
    	
    	//check to see we aren't making circles
    	if(b!=null) checkCircularReferences(b);
    	
    	//fix b to be below this
    	if(b!=null){
    		((BuildingBlockImpl) b).replaceTop(this);
    	}
    	
    	//fix this to be above b
    	replaceBottom(b);
    	
    }

    @Override
    public void stackUnder(BuildingBlock b){
    	if(b==top) return;//if we don't need to change anything, don't
    	if(b!=null) checkCircularReferences(b);//check to see we aren't making circles

    	//fix b to be above this
    	if(b!=null){
    		((BuildingBlockImpl) b).replaceBottom(this);
    	}
    	
    	//fix this to be below b
    	replaceTop(b);
    }
    
    private void replaceBottom(BuildingBlock b){
    	if(bottom!=null) ((BuildingBlockImpl) bottom).top = null;
    	bottom = b;
    }
    
    private void replaceTop(BuildingBlock b){
    	if(top!=null) ((BuildingBlockImpl) top).bottom = null;
    	top = b;
    }
    
    private void checkCircularReferences(BuildingBlock b){
    	BuildingBlock a = this;
    	while(assertNonEqual(a, b) & (a=a.findBlockOver())!=null);
    	a = this;
    	while(assertNonEqual(a, b) & (a=a.findBlockUnder())!=null);
    }
    
    //so we don't have to copy paste in the circle reference checking
    private static boolean assertNonEqual(BuildingBlock a, BuildingBlock b){
    	if(a==b) throw new CircularReferenceException();
    	return true;
    }
    
    //helper method for iterator.remove()
    private void removeFromStack(){
    	
    	((BuildingBlockImpl) bottom).top = top;
    	((BuildingBlockImpl) top).bottom = bottom;
    	
    	top = null;
    	bottom = null;
    }
    
    @Override
    public BuildingBlock findBlockUnder(){
        return bottom;
    }

    @Override
    public BuildingBlock findBlockOver(){
        return top;
    }

    public BuildingBlock findBottomBlock(){
    	BuildingBlock b = this;
    	while(b.findBlockUnder()!=null){
    		b = b.findBlockUnder();
    	}
    	return b;
    }
    
    @Override
    public Iterator<BuildingBlock> iterator(){
        return new Iterator<BuildingBlock>() {

        	//start the iteration on the block block in the stack
        	private BuildingBlock current = BuildingBlockImpl.this.findBottomBlock();
        	
        	//keep track of the previously returned block for use on remove
        	private BuildingBlock previous = null;
        	
			@Override
			public boolean hasNext(){
				return current!=null;
			}

			@Override
			public BuildingBlock next(){
				previous = current;
				current = previous.findBlockOver();
				return previous;
			}

			@Override
			public void remove(){
				if(previous==null) throw new IllegalStateException("Can only call remove once per next() call");
				
				//un-stack the block
				((BuildingBlockImpl) previous).removeFromStack();
				previous = null;
			}
			
		};
    }
    
}
