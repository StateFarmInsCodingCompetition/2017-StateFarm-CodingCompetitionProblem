package sf.codingcomp.blocks.solution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

	Set<PolyBlockImpl> connects = new HashSet<PolyBlockImpl>();
    @Override
    public Iterator<PolyBlock> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    		if (aPolyBlock == this) return;
    		if (aPolyBlock == null) return;
    		PolyBlockImpl pi = (PolyBlockImpl) aPolyBlock;
    		connects.add(pi);
    		pi.connects.add(this);
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    		if (aPolyBlock == this) return;
    		if (aPolyBlock == null) return;
    		PolyBlockImpl pi = (PolyBlockImpl) aPolyBlock;
    		connects.remove(pi);
    		pi.connects.remove(this);
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
    		if (connects.contains(aPolyBlock)) {
    			return true;
    		}
        return false;
    }

    @Override
    public int connections() {
        // TODO Auto-generated method stub
        return connects.size();
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
