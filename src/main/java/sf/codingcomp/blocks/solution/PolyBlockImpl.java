package sf.codingcomp.blocks.solution;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {

    ArrayList<PolyBlock> blocks;

    @Override
    public Iterator<PolyBlock> iterator() {
        // TODO Auto-generated method stub
        return new PBlocks();
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int connections() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PolyBlock copy() {
        // TODO Auto-generated method stub
        return null;
    }

    private class PBlocks implements Iterator<PolyBlock>{

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public PolyBlock next() {
            return null;
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super PolyBlock> action) {

        }
    }
}
