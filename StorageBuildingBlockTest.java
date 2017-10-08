package sf.codingcomp.blocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import sf.codingcomp.blocks.solution.StorageBuildingBlockImpl;

public class StorageBuildingBlockTest extends BuildingBlockTest {

    StorageBuildingBlock<String> block1;
    StorageBuildingBlock<Integer> block2;
    StorageBuildingBlock<String> block3;
    StorageBuildingBlock<Integer> block4;

    @Before
    public void setup() {
        block1 = new StorageBuildingBlockImpl<>();
        block2 = new StorageBuildingBlockImpl<>();
        block3 = new StorageBuildingBlockImpl<>();
        block4 = new StorageBuildingBlockImpl<>();
    }

    @Test
    public void testInitialState() {
        assertNull(getBlock1().getValue());
        assertNull(getBlock1().findBlockOver());
        assertNull(getBlock1().findBlockUnder());
    }

    @Test
    public void testStringValue() {
        getBlock1().setValue("");
        assertEquals("", block1.getValue());
    }

    @Test
    public void testIntegerValue() {
        Integer i = new Integer(1);
        getBlock2().setValue(i);
        assertEquals(i, block2.getValue());
    }

    @Test
    public void testIteratingAndGettingValues() {
        block1.setValue("1");
        block2.setValue(2);
        block1.stackUnder(block2);
        block3.setValue("3");
        block2.stackUnder(block3);
        block4.setValue(4);
        block3.stackUnder(block4);
        Iterator<BuildingBlock> smhIterator = block1.iterator();
        for(int i = 1; i < 4; i++) {
            @SuppressWarnings("unchecked")
            StorageBuildingBlock<Object> smh = (StorageBuildingBlock<Object>) smhIterator.next();
            if (i % 2 == 0) {
                assertEquals(new Integer(i), smh.getValue());
            } else {
                assertEquals(new Integer(i).toString(), smh.getValue());
            }
        }
    }

    public StorageBuildingBlock<String> getBlock1() {
        return block1;
    }

    public void setSmh1(StorageBuildingBlock<String> smh1) {
        this.block1 = smh1;
    }

    public StorageBuildingBlock<Integer> getBlock2() {
        return block2;
    }

    public void setSmh2(StorageBuildingBlock<Integer> smh2) {
        this.block2 = smh2;
    }

    public StorageBuildingBlock<String> getBlock3() {
        return block3;
    }

    public void setSmh3(StorageBuildingBlock<String> smh3) {
        this.block3 = smh3;
    }

    public StorageBuildingBlock<Integer> getBlock4() {
        return block4;
    }

    public void setSmh4(StorageBuildingBlock<Integer> smh4) {
        this.block4 = smh4;
    }
}
