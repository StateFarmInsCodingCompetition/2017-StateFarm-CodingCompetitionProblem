package sf.codingcomp.blocks;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sf.codingcomp.blocks.solution.StoragePolyBlockImpl;

public class StoragePolyConnectorTest extends PolyBlockTest {

    private StoragePolyBlock<String> block1;
    private StoragePolyBlock<Integer> block2;
    private StoragePolyBlock<String> block3;
    private StoragePolyBlock<Integer> block4;
    private StoragePolyBlock<String> block5;

    @Before
    public void setUp() throws Exception {
        block1 = new StoragePolyBlockImpl<>();
        block2 = new StoragePolyBlockImpl<>();
        block3 = new StoragePolyBlockImpl<>();
        block4 = new StoragePolyBlockImpl<>();
        block5 = new StoragePolyBlockImpl<>();
    }

    @Test
    public void testWithAString() {
        getBlock1().setValue("");
        assertEquals("", getBlock1().getValue());
    }

    @Test
    public void testWithAnInteger() {
        Integer i = new Integer(2);
        getBlock2().setValue(i);
        assertEquals(i, getBlock2().getValue());
    }

    public StoragePolyBlock<String> getBlock1() {
        return block1;
    }

    public void setBlock1(StoragePolyBlock<String> aBlock) {
        this.block1 = aBlock;
    }

    public StoragePolyBlock<Integer> getBlock2() {
        return block2;
    }

    public void setBlock2(StoragePolyBlock<Integer> aBlock) {
        this.block2 = aBlock;
    }

    public StoragePolyBlock<String> getBlock3() {
        return block3;
    }

    public void setBlock3(StoragePolyBlock<String> aBlock) {
        this.block3 = aBlock;
    }

    public StoragePolyBlock<Integer> getBlock4() {
        return block4;
    }

    public void setBlock4(StoragePolyBlock<Integer> aBlock) {
        this.block4 = aBlock;
    }

    public StoragePolyBlock<String> getBlock5() {
        return block5;
    }

    public void setBlock5(StoragePolyBlock<String> aBlock) {
        this.block5 = aBlock;
    }

}
