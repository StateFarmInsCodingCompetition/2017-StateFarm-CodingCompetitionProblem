package sf.codingcomp.blocks.solution;

import java.util.ArrayList;
import java.util.Iterator;

import sf.codingcomp.blocks.PolyBlock;

public class PolyBlockImpl implements PolyBlock {
    private ArrayList<PolyBlock> connection = new ArrayList<>();
    private int size = 1;

    @Override
    public Iterator<PolyBlock> iterator() {
        return null;
    }

    private class PolyBlockIterator implements Iterator<PolyBlock> {
        private boolean removed;
        private PolyBlock cursor;
        private int counter;

        public PolyBlockIterator() {
            cursor = PolyBlockImpl.this;
            removed = true;
        }

        public boolean hasNext() {
            return cursor != null;
        }

        public PolyBlock next() {
            removed = false;
            if(this.hasNext()) {
                PolyBlock current = cursor;
                cursor = cursor.copy();
                return current;
            }
            return null;
        }

//        public void remove(){
//            if (removed) {
//                throw new IllegalStateException("exception");
//            }
//            removed = true;
//            BuildingBlock up = cursor;
//            BuildingBlock down = cursor.findBlockUnder().findBlockUnder();
//            up.stackOver(down);
//        }
    }

    public ArrayList<PolyBlock> getConnection() {
        return connection;
    }

    @Override
    public void connect(PolyBlock aPolyBlock) {
        connection.add(aPolyBlock);
        size += aPolyBlock.size();
    }

    @Override
    public void disconnect(PolyBlock aPolyBlock) {
        connection.remove(aPolyBlock);
        size -= aPolyBlock.size();

    }

    @Override
    public boolean contains(PolyBlock aPolyBlock) {
        return connection.contains(aPolyBlock);
    }

    @Override
    public int connections() {
        return connection.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public PolyBlock copy() {
        return connection.get(0);
    }

}
