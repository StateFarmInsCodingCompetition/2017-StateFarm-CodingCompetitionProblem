package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
	public ArrayList<PolyBlock> directConnects = new ArrayList<PolyBlock>();


	@Override
	public Iterator<PolyBlock> iterator() {
		// TODO Auto-generated method stub
		directConnects.add(this);
		Iterator<PolyBlock> iter = directConnects.iterator();
		return iter;
	}

	@Override
	public void connect(PolyBlock aPolyBlock) {
		// TODO Auto-generated method stub
		if(aPolyBlock!=null) {
			if(!aPolyBlock.equals(this)) {
				directConnects.add(aPolyBlock);
				
				if(!aPolyBlock.contains(this)){
					aPolyBlock.connect(this);
				}
				
			}
		}

	}

	@Override
	public void disconnect(PolyBlock aPolyBlock) {
		// TODO Auto-generated method stub
		if(aPolyBlock != null){
			for(PolyBlock x : directConnects) {
				if(x.equals(aPolyBlock)) {
					directConnects.remove(x);
				}
			}
		}


	}

	@Override
	public boolean contains(PolyBlock aPolyBlock) {
		// TODO Auto-generated method stub
		for(PolyBlock x: directConnects) {
			if(x.equals(aPolyBlock)) {
				return true;
			}
		}
		for(PolyBlock x: directConnects) {
			if(x.contains(aPolyBlock)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public int connections() {
		// TODO Auto-generated method stub
		return directConnects.size();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		int size = 1;
		if(directConnects.size()>1){
			for(PolyBlock x: directConnects) { 
				size++;
			}
		}
		else if (directConnects.size()==1){
			if(directConnects.get(0).connections()==1) {
				size++;
			}
			else {
				size=directConnects.get(0).size();
			}
		}

		return size;
	}

	@Override
	public PolyBlock copy() {
		// TODO Auto-generated method stub
		return this;
	}

	public boolean isDirectConnect(PolyBlock aPolyBlock) {
		boolean direct = false;
		for(PolyBlock x: directConnects) {
			if (x.equals(aPolyBlock)){
				direct = true;
			}
		}
		return direct;
	}
}
