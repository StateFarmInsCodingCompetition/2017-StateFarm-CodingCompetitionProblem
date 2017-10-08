package sf.codingcomp.blocks.solution;

import sf.codingcomp.blocks.PolyBlock;
import sf.codingcomp.blocks.StoragePolyBlock;

import java.util.HashMap;
import java.util.Map;

public class StoragePolyBlockImpl<T> extends PolyBlockImpl implements StoragePolyBlock<T> {
    private T value;

    public StoragePolyBlockImpl(T value){
        this.value = value;
    }

    public StoragePolyBlockImpl(){
        this(null);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public PolyBlock copy() {
        Map<PolyBlock, PolyBlock> copies = new HashMap<>();

        iterator().forEachRemaining(block -> {
            if(block instanceof StoragePolyBlockImpl){
                copies.put(block, new StoragePolyBlockImpl<>(((StoragePolyBlockImpl) block).value));
            }else{
                copies.put(block, new PolyBlockImpl());
            }
        });

        copies.keySet().forEach(block -> {
            for (PolyBlock inner : ((PolyBlockImpl) block).connections) {
                copies.get(block).connect(copies.get(inner));
            }
        });

        return copies.get(this);
    }

}
