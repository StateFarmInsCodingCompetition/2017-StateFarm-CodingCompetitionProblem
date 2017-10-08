package sf.codingcomp.blocks.solution;

import java.util.Iterator;
import java.util.LinkedList;

import sf.codingcomp.blocks.BuildingBlock;

public class BuildingBlockImpl implements BuildingBlock {
	
	public static LinkedList<BuildingBlock> stack = new LinkedList<BuildingBlock>();
	
    @Override
    public Iterator<BuildingBlock> iterator() {
        // TODO Auto-generated method stub
    	return null;
    }

    @Override
    public void stackOver(BuildingBlock b) {
    	if (stack.contains(this) && stack.contains(b)) {
    		stack.remove(this);
    		stack.remove(b);
    		System.out.println("stackOver 1");
    	} 
    	if (!stack.contains(this) && !stack.contains(b)) {
    	    	stack.add(this);
    	        stack.add(b);
    	        System.out.println("stackOver 2");
    	        return;
    	       }
    	 
       if (stack.contains(b)) {
    	 int index = stack.indexOf(b);
    	  stack.add(index, this);
    	  System.out.println("stackOver 3");
       }

    }

    @Override
    public void stackUnder(BuildingBlock b) {
    	if (stack.contains(this) && stack.contains(b)) {
    		stack.remove(this);
    		stack.remove(b);
    		System.out.println("stackUnder 1");
    	}
    		
    	if (!stack.contains(this) && !stack.contains(b)) {
    	stack.add(b);
        stack.add(this);
        System.out.println("stackUnder 2");
        return;
       }
       
       if (stack.contains(b)) {
      	 int index = stack.indexOf(b);
      	  stack.add(index + 1, this);
      	System.out.println("stackUnder 3");
         }

    }

    @Override
    public BuildingBlock findBlockUnder() {
    	if (!stack.contains(this)) {
    		return null;
    	}
    	if (stack.getLast() == this)
        	return null;
        return stack.get(stack.indexOf(this) + 1);
    }

    @Override
    public BuildingBlock findBlockOver() {
    	if (!stack.contains(this)) {
    		return null;
    	}
    	if (stack.getFirst() == this)
        	return null;
        return stack.get(stack.indexOf(this) + - 1);
    }

}
