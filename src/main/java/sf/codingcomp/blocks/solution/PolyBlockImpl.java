package sf.codingcomp.blocks.solution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

    private Set<PolyBlock> connectedPolyblocks = new HashSet<>();

    @Override
    public Iterator<PolyBlock> iterator() {
        // TODO Auto-generated method stub
        Set<PolyBlock> total = new HashSet<>();
        getAll(this, total);
        return total.iterator();
    }

    private void getAll(PolyBlockImpl cur, Set<PolyBlock> set) {
        set.add(cur);
        for (PolyBlock next: cur.connectedPolyblocks) {
            PolyBlockImpl implNext = (PolyBlockImpl) next;
            if (!set.contains(next)) {
                getAll(implNext, set);
            }
        }
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
        if (aPolyBlock != null && aPolyBlock != this) {
            connectedPolyblocks.add(aPolyBlock);
            if (!aPolyBlock.contains(this)) {
                aPolyBlock.connect(this);
            }
        }
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
        if (aPolyBlock != null && aPolyBlock != this) {
            connectedPolyblocks.remove(aPolyBlock);
            if (aPolyBlock.contains(this)) {
                aPolyBlock.disconnect(this);
            }
        }
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
        return connectedPolyblocks.contains(aPolyBlock);    }

    @Override
    public int connections() {
        // TODO Auto-generated method stub
        return connectedPolyblocks.size();
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        Set<PolyBlock> total = new HashSet<>();
        getAll(this, total);
        return total.size();
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
