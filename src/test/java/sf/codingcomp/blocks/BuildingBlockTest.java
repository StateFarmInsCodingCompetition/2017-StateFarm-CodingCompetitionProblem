package sf.codingcomp.blocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import sf.codingcomp.blocks.solution.BuildingBlockImpl;

public class BuildingBlockTest {

    BuildingBlock block1;
    BuildingBlock block2;
    BuildingBlock block3;
    BuildingBlock block4;

    @Before
    public void setup() {
        block1 = new BuildingBlockImpl();
        block2 = new BuildingBlockImpl();
        block3 = new BuildingBlockImpl();
        block4 = new BuildingBlockImpl();
    }

    @Test
    public void testCallingSeveralTimes() {
        getBlock1().stackUnder(getBlock2());
        getBlock1().stackUnder(getBlock2());
        getBlock1().stackUnder(getBlock2());

        assertEquals(getBlock2(), getBlock1().findBlockOver());
        assertEquals(null, getBlock1().findBlockUnder());
    }

    @Test
    public void testPreconditionsAreValid() {
        assertNull(getBlock1().findBlockOver());
        assertNull(getBlock1().findBlockUnder());
        assertNull(getBlock2().findBlockOver());
        assertNull(getBlock2().findBlockUnder());
        assertNull(getBlock3().findBlockOver());
        assertNull(getBlock3().findBlockUnder());
        assertNull(getBlock4().findBlockOver());
        assertNull(getBlock4().findBlockUnder());
    }

    @Test
    public void testStackingTwoBlocks() {
        getBlock1().stackUnder(getBlock2());
        assertNull(getBlock2().findBlockOver());
        assertEquals(getBlock1(), getBlock2().findBlockUnder());
        assertEquals(getBlock2(), getBlock1().findBlockOver());
        assertNull(getBlock1().findBlockUnder());
    }

    @Test
    public void testUnStackingByBlock1() {
        testStackingTwoBlocks();
        getBlock1().stackUnder(null);
        testPreconditionsAreValid();
    }

    @Test
    public void testUnStackingByBlock2() {
        testStackingTwoBlocks();
        getBlock2().stackOver(null);
        testPreconditionsAreValid();
    }

    @Test
    public void testReplacingBlock1WithBlock3() {
        testStackingTwoBlocks();
        getBlock2().stackOver(getBlock3());
        assertNull(getBlock1().findBlockOver());
        assertNull(getBlock1().findBlockUnder());
        assertNull(getBlock2().findBlockOver());
        assertEquals(getBlock3(), getBlock2().findBlockUnder());
        assertEquals(getBlock2(), getBlock3().findBlockOver());
        assertNull(getBlock3().findBlockUnder());
        assertNull(getBlock4().findBlockOver());
        assertNull(getBlock4().findBlockUnder());
    }

    @Test
    public void testIteratingBlocksStackedUnder() {
        getBlock1().stackUnder(getBlock2());
        getBlock2().stackUnder(getBlock3());
        getBlock3().stackUnder(getBlock4());

        Iterator<BuildingBlock> it = getBlock1().iterator();
        BuildingBlock b = it.next();
        assertSame(getBlock1(), b);
        b = it.next();
        assertSame(getBlock2(), b);
        b = it.next();
        assertSame(getBlock3(), b);
        b = it.next();
        assertSame(getBlock4(), b);
    }

    @Test
    public void testIteratingBlocksStackedOver() {
        getBlock1().stackOver(getBlock2());
        getBlock2().stackOver(getBlock3());
        getBlock3().stackOver(getBlock4());

        Iterator<BuildingBlock> it = getBlock1().iterator();
        BuildingBlock b = it.next();
        assertSame(getBlock4(), b);
        b = it.next();
        assertSame(getBlock3(), b);
        b = it.next();
        assertSame(getBlock2(), b);
        b = it.next();
        assertSame(getBlock1(), b);
    }

    @Test
    public void testRemovingABlockWhileIterating() {
        getBlock1().stackOver(getBlock2());
        getBlock2().stackOver(getBlock3());
        getBlock3().stackOver(getBlock4());

        Iterator<BuildingBlock> it = getBlock3().iterator();
        BuildingBlock b = it.next();
        assertSame(getBlock4(), b);
        b = it.next();
        assertSame(getBlock3(), b);

        it.remove();

        b = it.next();
        assertSame(getBlock2(), b);
        b = it.next();
        assertSame(getBlock1(), b);

        assertNull(getBlock1().findBlockOver());
        assertEquals(getBlock2(), getBlock1().findBlockUnder());
        assertEquals(getBlock1(), getBlock2().findBlockOver());
        assertEquals(getBlock4(), getBlock2().findBlockUnder());
        assertNull(getBlock3().findBlockOver());
        assertNull(getBlock3().findBlockUnder());
        assertEquals(getBlock2(), getBlock4().findBlockOver());
        assertNull(getBlock4().findBlockUnder());
    }

    @Test
    public void testRemovingABlockTwiceWhileIterating() {
        getBlock1().stackOver(getBlock2());
        getBlock2().stackOver(getBlock3());
        getBlock3().stackOver(getBlock4());

        Iterator<BuildingBlock> it = getBlock3().iterator();
        BuildingBlock b = it.next();
        assertSame(getBlock4(), b);
        b = it.next();
        assertSame(getBlock3(), b);

        it.remove();

        try {
            it.remove();
        } catch(IllegalStateException ise) {
            return;// success
        }
        fail("By contract, remove may only be called once for each next.");
    }

    @Test
    public void testRemovingABlockBeforeIterating() {
        getBlock1().stackOver(getBlock2());
        getBlock2().stackOver(getBlock3());
        getBlock3().stackOver(getBlock4());

        Iterator<BuildingBlock> it = getBlock3().iterator();
        try {
            it.remove();
        } catch(IllegalStateException ise) {
            return;// success
        }
        fail("By contract, remove may only be called after next.");
    }

    @Test
    public void testHasNextWorks() {
        getBlock1().stackOver(getBlock2());
        getBlock2().stackOver(getBlock3());
        getBlock3().stackOver(getBlock4());

        Iterator<BuildingBlock> it = getBlock3().iterator();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }

    /**
     * Blocks should be in a line, references should not be possible between
     * three Blocks.
     */
    @Test
    public void testTrianglesAreNotPossible() {
        getBlock2().stackUnder(getBlock1());
        assertEquals(getBlock1(), getBlock2().findBlockOver());
        assertEquals(getBlock2(), getBlock1().findBlockUnder());
        getBlock3().stackUnder(getBlock1());
        assertEquals(getBlock1(), getBlock3().findBlockOver());
        assertEquals(getBlock3(), getBlock1().findBlockUnder());
        assertNull(getBlock2().findBlockOver());
    }

    @Test
    public void testCirclesThrowACircularReferenceExceptionWhenStackingUnder() {
        try {
            getBlock1().stackUnder(getBlock2());
            getBlock2().stackUnder(getBlock3());
            getBlock3().stackUnder(getBlock1());
            fail("Connecting three blocks in a circle should not be allowed!");
        } catch(CircularReferenceException cre) {
            assertNull(getBlock3().findBlockOver());
            assertNull(getBlock1().findBlockUnder());
        }
    }

    @Test
    public void testCirclesThrowACircularReferenceExceptionWhenStackingOver() {
        try {
            getBlock1().stackOver(getBlock2());
            getBlock2().stackOver(getBlock3());
            getBlock3().stackOver(getBlock1());
            fail("Connecting three blocks in a circle should not be allowed!");
        } catch(CircularReferenceException cre) {
            assertNull(getBlock3().findBlockUnder());
            assertNull(getBlock1().findBlockOver());
        }
    }

    public BuildingBlock getBlock1() {
        return block1;
    }

    public void setBlock1(BuildingBlock aBlock) {
        this.block1 = aBlock;
    }

    public BuildingBlock getBlock2() {
        return block2;
    }

    public void setBlock2(BuildingBlock aBlock) {
        this.block2 = aBlock;
    }

    public BuildingBlock getBlock3() {
        return block3;
    }

    public void setBlock3(BuildingBlock aBlock) {
        this.block3 = aBlock;
    }

    public BuildingBlock getBlock4() {
        return block4;
    }

    public void setBlock4(BuildingBlock aBlock) {
        this.block4 = aBlock;
    }
}
