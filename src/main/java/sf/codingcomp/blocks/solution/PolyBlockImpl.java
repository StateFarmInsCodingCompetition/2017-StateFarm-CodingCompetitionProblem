package sf.codingcomp.blocks.solution;

import java.util.*;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
    private Set<PolyBlockImpl> conns = new LinkedHashSet<>();

    @Override
    public Iterator<PolyBlock> iterator() {
        Stack<PolyBlockImpl> init = new Stack<>();
        init.push(this);
        Set<PolyBlockImpl> initSet = new HashSet<>();
        initSet.add(this);
        return new Iterator<PolyBlock>() {
            Stack<PolyBlockImpl> current = init;
            Set<PolyBlockImpl> visited = initSet;
            @Override
            public boolean hasNext() {
                return current.size() != 0;
            }

            @Override
            public PolyBlock next() {
                PolyBlockImpl ret =  current.peek();
                while(!current.isEmpty()) {
                    for (PolyBlockImpl conn : current.peek().conns) {
                        if (!visited.contains(conn)) {
                            current.push(conn);
                            visited.add(conn);
                            return ret;
                        }
                    }
                    current.pop();
                }
                return ret;
            }
        };
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        if(aPolyBlock == this) return;
        if(aPolyBlock instanceof PolyBlockImpl) {
            PolyBlockImpl b = (PolyBlockImpl) aPolyBlock;
            conns.add(b);
            b.conns.add(this);
        }
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        if(aPolyBlock instanceof PolyBlockImpl) {
            PolyBlockImpl b = (PolyBlockImpl) aPolyBlock;
            conns.remove(b);
            b.conns.remove(this);
        }
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return conns.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return conns.size();
    }

    @Override
    public int size() {
        // TODO union find
        int count = 0;
        for (PolyBlock polyBlock : this) {
            count++;
        }
        return count;
    }

    @Override
    public PolyBlock copy() {
        Map<PolyBlockImpl, PolyBlockImpl> origToCopy = new HashMap<>();
        for(PolyBlock pInter : this) {
            PolyBlockImpl p = (PolyBlockImpl) pInter;
            origToCopy.put(p, new PolyBlockImpl());
        }

        for(PolyBlock p : this) {
            for(PolyBlock p2 : ((PolyBlockImpl) p).conns) {
                origToCopy.get(p).connect(origToCopy.get(p2));
            }
        }

        return origToCopy.get(this);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PolyBlockImpl)) {
            return false;
        }
        return this.connections() == ((PolyBlockImpl) o).connections();
    }
}
