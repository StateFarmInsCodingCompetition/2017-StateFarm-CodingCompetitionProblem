package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
    private List<PolyBlock> connections = new ArrayList<>();
    @Override
    public Iterator<PolyBlock> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        if(aPolyBlock==null) return;
        if(Objects.equals(this, aPolyBlock)) return;
        if(this.contains(aPolyBlock)) return;
        this.connections.add(aPolyBlock);
        aPolyBlock.connect(this);
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        if(aPolyBlock==null) return;
        if(!this.contains(aPolyBlock)) return;
        this.connections.remove(aPolyBlock);
        aPolyBlock.disconnect(this);
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return connections.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return connections.size();
    }

    @Override
    public int size() {
        return 1+connections.size();
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
