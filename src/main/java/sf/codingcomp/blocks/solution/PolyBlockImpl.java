package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.PolyBlock;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PolyBlockImpl implements PolyBlock {

    protected Set<PolyBlock> connections = new HashSet<>();

    private AtomicBoolean connectionLock = new AtomicBoolean();
    private AtomicBoolean iterationLock = new AtomicBoolean();

    @Override
    public void connect(PolyBlock aPolyBlock) {
        if (aPolyBlock == null || aPolyBlock == this) {
            return;
        }

        if (connectionLock.get()) {
            return;
        }

        this.connections.add(aPolyBlock);
        connectionLock.set(true);
        aPolyBlock.connect(this);
        connectionLock.set(false);
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        if (aPolyBlock == null) {
            return;
        }

        if (connectionLock.get()) {
            return;
        }

        this.connections.remove(aPolyBlock);
        connectionLock.set(true);
        aPolyBlock.disconnect(this);
        connectionLock.set(false);
    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return aPolyBlock != this && connections.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return connections.size();
    }

    @Override
    public int size() {
        int i = 0;
        for (PolyBlock polyBlock : this) {
            i++;
        }
        return i;
    }

    @Override
    public PolyBlock copy() {
        Map<PolyBlock, PolyBlock> copies = new HashMap<>();

        iterator().forEachRemaining(block -> copies.put(block, new PolyBlockImpl()));

        copies.keySet().forEach(block -> {
            for (PolyBlock inner : ((PolyBlockImpl) block).connections) {
                copies.get(block).connect(copies.get(inner));
            }
        });

        return copies.get(this);
    }

    @Override
    public Iterator<PolyBlock> iterator() {
        if(iterationLock.get()){
            return null;
        }

        iterationLock.set(true);
        PolyBlockIterator i = new PolyBlockIterator(this);
        iterationLock.set(false);

        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PolyBlockImpl that = (PolyBlockImpl) o;

        return connections.size() == that.connections.size();
    }


    private class PolyBlockIterator implements Iterator<PolyBlock> {

        private LinkedList<PolyBlock> stack = new LinkedList<>();
        private PolyBlock current;

        private PolyBlockIterator(PolyBlock pb) {
            this.prepare(pb);
        }

        private void prepare(PolyBlock p) {
            Set<PolyBlock> visited = new HashSet<>();
            Stack<PolyBlock> stack = new Stack<>();

            stack.push(p);

            while (!stack.isEmpty()) {
                PolyBlock pb = stack.pop();
                visited.add(pb);
                PolyBlockImpl polyBlock = (PolyBlockImpl) pb;
                for (PolyBlock b : polyBlock.connections) {
                    if (visited.contains(b)) {
                        continue;
                    }
                    stack.push(b);
                }
            }

            this.stack.addAll(visited);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public PolyBlock next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            current = stack.pop();

            return current;
        }

        @Override
        public void remove() {
            PolyBlock pb = current;
            if (pb == null) {
                throw new NoSuchElementException();
            }
            PolyBlockImpl pbi = (PolyBlockImpl) pb;

            for (PolyBlock b : pbi.connections) {
                if (b == pbi) {
                    b.disconnect(pb);
                }
            }
        }
    }
}
