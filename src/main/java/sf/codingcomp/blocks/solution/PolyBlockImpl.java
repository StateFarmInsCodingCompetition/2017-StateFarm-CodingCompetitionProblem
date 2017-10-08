package sf.codingcomp.blocks.solution;

import java.util.*;

import sf.codingcomp.blocks.BuildingBlock;
import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
    private List<PolyBlock> directConnections = new ArrayList<>();
    @Override
    public Iterator<PolyBlock> iterator() {
        return new PolyBlockIterator(this);
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        if(aPolyBlock==null) return;
        if(Objects.equals(this, aPolyBlock)) return;
        if(this.contains(aPolyBlock)) return;
        this.directConnections.add(aPolyBlock);
        aPolyBlock.connect(this);
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        if(aPolyBlock==null) return;
        if(!this.contains(aPolyBlock)) return;
        this.directConnections.remove(aPolyBlock);
        aPolyBlock.disconnect(this);
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return directConnections.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return directConnections.size();
    }

    @Override
    public int size() {
        int count = 0;
        for (PolyBlock polyBlock : this) {
            count++;
        }
        return count;
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PolyBlock> getDirectConnections() {
        return directConnections;
    }

    public class PolyBlockIterator implements Iterator<PolyBlock> {
        private PolyBlock current;
        private HashSet<PolyBlock> visitedPolyBlocks = new HashSet<>();
        private Stack<PolyBlock> polyStack = new Stack<>();
        private int localItr = 0;

        public PolyBlockIterator(PolyBlock current) {
            this.current = current;
            visitedPolyBlocks.add(current);
            polyStack.push(current);
        }

        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public PolyBlock next() {
            PolyBlock temp = current;
            this.current = nextImpl();
            return temp;
        }

        private PolyBlock nextImpl(){
            while(polyStack.size()>0){
                PolyBlock topBlock = polyStack.peek();
                List<PolyBlock> children = topBlock.getDirectConnections();

                while (localItr<children.size()){
                    PolyBlock interested = children.get(localItr);
                    if(visitedPolyBlocks.contains(interested)) {
                        localItr++;
                        continue;
                    }
                    polyStack.push(interested);
                    visitedPolyBlocks.add(interested);
                    localItr++;
                    return interested;
                }
                polyStack.pop();
                localItr = 0;
            }
            return null;
        }

    }

}
