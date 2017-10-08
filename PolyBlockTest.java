package sf.codingcomp.blocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sf.codingcomp.blocks.solution.PolyBlockImpl;

public class PolyBlockTest {

    private PolyBlock block1;
    private PolyBlock block2;
    private PolyBlock block3;
    private PolyBlock block4;
    private PolyBlock block5;

    @Before
    public void setUp() throws Exception {
        block1 = new PolyBlockImpl();
        block2 = new PolyBlockImpl();
        block3 = new PolyBlockImpl();
        block4 = new PolyBlockImpl();
        block5 = new PolyBlockImpl();
    }

    @After
    public void tearDown() throws Exception {
        setBlock1(null);
        setBlock2(null);
        setBlock3(null);
        setBlock4(null);
        setBlock5(null);
    }

    @Test
    public void testIterator() {
        assertNotNull(getBlock1().iterator());
    }

    @Test
    public void testIteratorWithOneElement() {
        Iterator<PolyBlock> it = getBlock1().iterator();
        assertTrue(it.hasNext());
        assertEquals(getBlock1(), it.next());
    }

    @Test
    public void testIteratorWithTwoElements() {
        getBlock1().connect(getBlock2());
        List<PolyBlock> fbs = new ArrayList<PolyBlock>();
        for(PolyBlock fb : getBlock1()) {
            fbs.add(fb);
        }
        assertEquals(2, fbs.size());
        assertTrue(fbs.contains(getBlock1()));
        assertTrue(fbs.contains(getBlock2()));
    }

    @Test
    public void testIteratorWithThreeElementsInSeries() {
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        List<PolyBlock> fbs = new ArrayList<PolyBlock>();
        for(PolyBlock fb : getBlock1()) {
            fbs.add(fb);
        }
        assertEquals(3, fbs.size());
        assertTrue(fbs.contains(getBlock1()));
        assertTrue(fbs.contains(getBlock2()));
        assertTrue(fbs.contains(getBlock3()));
    }

    @Test
    public void testIteratorWithThreeElementsInATriangle() {
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        getBlock3().connect(getBlock1());
        List<PolyBlock> fbs = new ArrayList<PolyBlock>();
        for(PolyBlock fb : getBlock1()) {
            fbs.add(fb);
        }
        assertEquals(3, fbs.size());
        assertTrue(fbs.contains(getBlock1()));
        assertTrue(fbs.contains(getBlock2()));
        assertTrue(fbs.contains(getBlock3()));
    }

    @Test
    public void testIteratorWithFiveElementsInAPlus() {
        getBlock1().connect(getBlock2());
        getBlock1().connect(getBlock3());
        getBlock1().connect(getBlock4());
        getBlock1().connect(getBlock5());
        List<PolyBlock> fbs = new ArrayList<PolyBlock>();
        for(PolyBlock fb : getBlock1()) {
            fbs.add(fb);
        }
        assertEquals(5, fbs.size());
        assertTrue(fbs.contains(getBlock1()));
        assertTrue(fbs.contains(getBlock2()));
        assertTrue(fbs.contains(getBlock3()));
        assertTrue(fbs.contains(getBlock4()));
        assertTrue(fbs.contains(getBlock5()));
    }

    @Test
    public void testIteratorWithFiveElementsInBothAStarAndPentagon() {
        connectAsStarAndPentagram();

        List<PolyBlock> fbs = new ArrayList<PolyBlock>();
        for(PolyBlock fb : getBlock1()) {
            fbs.add(fb);
        }
        assertEquals(5, fbs.size());
        assertTrue(fbs.contains(getBlock1()));
        assertTrue(fbs.contains(getBlock2()));
        assertTrue(fbs.contains(getBlock3()));
        assertTrue(fbs.contains(getBlock4()));
        assertTrue(fbs.contains(getBlock5()));
    }

    @Test
    public void testConnectWithNull() {
        assertEquals(0, getBlock1().connections());
        assertEquals(1, getBlock1().size());
        getBlock1().connect(null);
        assertEquals(0, getBlock1().connections());
        assertEquals(1, getBlock1().size());
    }

    @Test
    public void testConnectWithAnotherPolyBlock() {
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        getBlock1().connect(getBlock2());
        assertEquals(1, getBlock1().connections());
        assertEquals(1, getBlock2().connections());
        assertEquals(2, getBlock1().size());
        assertEquals(2, getBlock2().size());
    }

    @Test
    public void testConnectWithThreePolyBlocksInSeries() {
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(0, getBlock3().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        assertEquals(1, getBlock1().connections());
        assertEquals(2, getBlock2().connections());
        assertEquals(1, getBlock3().connections());
        assertEquals(3, getBlock1().size());
        assertEquals(3, getBlock2().size());
        assertEquals(3, getBlock3().size());
    }

    @Test
    public void testConnectWithThreePolyBlocksInATriangle() {
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(0, getBlock3().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        getBlock3().connect(getBlock1());
        assertEquals(2, getBlock1().connections());
        assertEquals(2, getBlock2().connections());
        assertEquals(2, getBlock3().connections());
        assertEquals(3, getBlock1().size());
        assertEquals(3, getBlock2().size());
        assertEquals(3, getBlock3().size());
    }

    @Test
    public void testConnectWithFivePolyBlocksInAPlus() {
        verifyPreCondition();

        getBlock2().connect(getBlock1());
        getBlock3().connect(getBlock1());
        getBlock4().connect(getBlock1());
        getBlock5().connect(getBlock1());

        assertEquals(4, getBlock1().connections());
        assertEquals(1, getBlock2().connections());
        assertEquals(1, getBlock3().connections());
        assertEquals(1, getBlock4().connections());
        assertEquals(1, getBlock5().connections());

        assertEquals(5, getBlock1().size());
        assertEquals(5, getBlock2().size());
        assertEquals(5, getBlock3().size());
        assertEquals(5, getBlock4().size());
        assertEquals(5, getBlock5().size());
    }

    @Test
    public void testConnectWithFivePolyBlocksInBothAStarAndPentagon() {

        verifyPreCondition();

        connectAsStarAndPentagram();

        assertEquals(4, getBlock1().connections());
        assertEquals(4, getBlock2().connections());
        assertEquals(4, getBlock3().connections());
        assertEquals(4, getBlock4().connections());
        assertEquals(4, getBlock5().connections());

        assertEquals(5, getBlock1().size());
        assertEquals(5, getBlock2().size());
        assertEquals(5, getBlock3().size());
        assertEquals(5, getBlock4().size());
        assertEquals(5, getBlock5().size());
    }

    @Test
    public void testDisconnectWithNull() {
        getBlock1().disconnect(null);
        getBlock2().disconnect(null);
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        getBlock1().connect(getBlock2());
        assertEquals(1, getBlock1().connections());
        assertEquals(1, getBlock2().connections());
        assertEquals(2, getBlock1().size());
        assertEquals(2, getBlock2().size());
        getBlock1().disconnect(null);
        getBlock2().disconnect(null);
        assertEquals(1, getBlock1().connections());
        assertEquals(1, getBlock2().connections());
        assertEquals(2, getBlock1().size());
        assertEquals(2, getBlock2().size());
    }

    @Test
    public void testDisconnect() {
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        getBlock1().connect(getBlock2());
        assertEquals(1, getBlock1().connections());
        assertEquals(1, getBlock2().connections());
        assertEquals(2, getBlock1().size());
        assertEquals(2, getBlock2().size());
        getBlock1().disconnect(getBlock2());
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
    }

    @Test
    public void testDisconnectWithThreePolyBlocksInSeries() {
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(0, getBlock3().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        assertEquals(1, getBlock1().connections());
        assertEquals(2, getBlock2().connections());
        assertEquals(1, getBlock3().connections());
        assertEquals(3, getBlock1().size());
        assertEquals(3, getBlock2().size());
        assertEquals(3, getBlock3().size());
        getBlock1().disconnect(getBlock2());
        getBlock2().disconnect(getBlock3());
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(0, getBlock3().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
    }

    @Test
    public void testDisconnectWithThreePolyBlocksInATriangle() {
        verifyPreCondition();
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        assertEquals(1, getBlock1().connections());
        assertEquals(2, getBlock2().connections());
        assertEquals(1, getBlock3().connections());
        assertEquals(3, getBlock1().size());
        assertEquals(3, getBlock2().size());
        assertEquals(3, getBlock3().size());
        getBlock1().disconnect(getBlock2());
        getBlock2().disconnect(getBlock3());
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(0, getBlock3().connections());
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
    }

    @Test
    public void testDisconnectWithFiveFacetedBlocksInAPlus() {
        verifyPreCondition();

        getBlock1().connect(getBlock2());
        getBlock1().connect(getBlock3());
        getBlock1().connect(getBlock4());
        getBlock1().connect(getBlock5());

        assertEquals(4, getBlock1().connections());
        assertEquals(1, getBlock2().connections());
        assertEquals(1, getBlock3().connections());
        assertEquals(1, getBlock4().connections());
        assertEquals(1, getBlock5().connections());

        assertEquals(5, getBlock1().size());
        assertEquals(5, getBlock2().size());
        assertEquals(5, getBlock3().size());
        assertEquals(5, getBlock4().size());
        assertEquals(5, getBlock5().size());

        getBlock1().disconnect(getBlock2());
        getBlock1().disconnect(getBlock3());
        getBlock1().disconnect(getBlock4());
        getBlock1().disconnect(getBlock5());

        verifyPreCondition();
    }

    @Test
    public void testDisconnectWithFiveElementsInBothAStarAndPentagram() {
        verifyPreCondition();

        connectAsStarAndPentagram();

        assertEquals(4, getBlock1().connections());
        assertEquals(4, getBlock2().connections());
        assertEquals(4, getBlock3().connections());
        assertEquals(4, getBlock4().connections());
        assertEquals(4, getBlock5().connections());

        assertEquals(5, getBlock1().size());
        assertEquals(5, getBlock2().size());
        assertEquals(5, getBlock3().size());
        assertEquals(5, getBlock4().size());
        assertEquals(5, getBlock5().size());

        getBlock1().disconnect(getBlock2());
        getBlock1().disconnect(getBlock3());
        getBlock1().disconnect(getBlock4());
        getBlock1().disconnect(getBlock5());

        getBlock2().disconnect(getBlock3());
        getBlock2().disconnect(getBlock4());
        getBlock2().disconnect(getBlock5());

        getBlock3().disconnect(getBlock4());
        getBlock3().disconnect(getBlock5());

        getBlock4().disconnect(getBlock5());

        verifyPreCondition();
    }

    private void verifyPreCondition() {
        assertEquals(0, getBlock1().connections());
        assertEquals(0, getBlock2().connections());
        assertEquals(0, getBlock3().connections());
        assertEquals(0, getBlock4().connections());
        assertEquals(0, getBlock5().connections());

        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        assertEquals(1, getBlock4().size());
        assertEquals(1, getBlock5().size());
    }

    private void connectAsStarAndPentagram() {
        getBlock1().connect(getBlock2());
        getBlock1().connect(getBlock3());
        getBlock1().connect(getBlock4());
        getBlock1().connect(getBlock5());

        getBlock2().connect(getBlock3());
        getBlock2().connect(getBlock4());
        getBlock2().connect(getBlock5());

        getBlock3().connect(getBlock4());
        getBlock3().connect(getBlock5());

        getBlock4().connect(getBlock5());
    }

    @Test
    public void testFindWithNoConnections() {
        assertFalse(getBlock1().contains(getBlock2()));
    }

    @Test
    public void testFindWithOneConnection() {
        assertFalse(getBlock1().contains(getBlock2()));
        assertFalse(getBlock2().contains(getBlock1()));
        getBlock1().connect(getBlock2());
        assertTrue(getBlock1().contains(getBlock2()));
        assertTrue(getBlock2().contains(getBlock1()));
    }

    @Test
    public void testFindWithThreeConnectionsInSeries() {
        assertFalse(getBlock1().contains(getBlock2()));
        assertFalse(getBlock2().contains(getBlock1()));
        assertFalse(getBlock2().contains(getBlock3()));
        assertFalse(getBlock3().contains(getBlock2()));
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        assertTrue(getBlock1().contains(getBlock2()));
        assertTrue(getBlock2().contains(getBlock1()));
        assertTrue(getBlock2().contains(getBlock3()));
        assertTrue(getBlock3().contains(getBlock2()));
    }

    @Test
    public void testFindWithThreeConnectionsInATriangle() {
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        getBlock3().connect(getBlock2());
        assertTrue(getBlock1().contains(getBlock2()));
        assertTrue(getBlock2().contains(getBlock1()));
        assertTrue(getBlock2().contains(getBlock3()));
        assertTrue(getBlock3().contains(getBlock2()));
    }

    @Test
    public void testSize() {
        assertEquals(1, getBlock1().size());
    }

    @Test
    public void testSizeWithOneConnection() {
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        getBlock1().connect(getBlock2());
        assertEquals(2, getBlock1().size());
        assertEquals(2, getBlock2().size());
    }

    @Test
    public void testSizeWithThreeConnectionsInSeries() {
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        assertEquals(3, getBlock1().size());
        assertEquals(3, getBlock2().size());
        assertEquals(3, getBlock3().size());
    }

    @Test
    public void testSizeWithThreeConnectionsInATriangle() {
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        getBlock3().connect(getBlock1());
        assertEquals(3, getBlock1().size());
        assertEquals(3, getBlock2().size());
        assertEquals(3, getBlock3().size());
    }

    @Test
    public void testSizeWithFiveConnectionsInAPlus() {
        assertEquals(1, getBlock1().size());
        assertEquals(1, getBlock2().size());
        assertEquals(1, getBlock3().size());
        assertEquals(1, getBlock4().size());
        assertEquals(1, getBlock5().size());

        getBlock1().connect(getBlock2());
        getBlock1().connect(getBlock3());
        getBlock1().connect(getBlock4());
        getBlock1().connect(getBlock5());

        assertEquals(5, getBlock1().size());
        assertEquals(5, getBlock2().size());
        assertEquals(5, getBlock3().size());
        assertEquals(5, getBlock4().size());
        assertEquals(5, getBlock5().size());
    }

    @Test
    public void testConnectingAnPolyConnectorToItself() {
        assertEquals(1, getBlock1().size());
        assertEquals(0, getBlock1().connections());
        assertFalse(getBlock1().contains(getBlock1()));
        getBlock1().connect(getBlock1());
        assertEquals(1, getBlock1().size());
        assertEquals(0, getBlock1().connections());
        assertFalse(getBlock1().contains(getBlock1()));
    }

    @Test
    public void testConnectingAPolyConnectorTwice() {
        assertEquals(1, getBlock1().size());
        assertEquals(0, getBlock1().connections());
        assertFalse(getBlock1().contains(getBlock2()));
        getBlock1().connect(getBlock2());
        assertEquals(2, getBlock1().size());
        assertEquals(1, getBlock1().connections());
        assertTrue(getBlock1().contains(getBlock2()));
        getBlock1().connect(getBlock2());
        assertEquals(2, getBlock1().size());
        assertEquals(1, getBlock1().connections());
        assertTrue(getBlock1().contains(getBlock2()));
    }

    @Test
    public void testDisconnectingFromAPolyConnectorThatIsNotConnected() {
        assertEquals(1, getBlock1().size());
        assertEquals(0, getBlock1().connections());
        getBlock1().disconnect(getBlock2());
        assertEquals(1, getBlock1().size());
        assertEquals(0, getBlock1().connections());
    }

    @Test
    public void testContainsWhenThisDoesNotContainButOneOfMyConnectionsDoes() {
        getBlock1().connect(getBlock2());
        getBlock2().connect(getBlock3());
        assertFalse(getBlock1().contains(getBlock3()));
        assertTrue(getBlock1().contains(getBlock2()));
    }

    @Test
    public void testCopyOnePolyConnector() {
        PolyBlock newPolyCon = getBlock1().copy();
        assertEquals(getBlock1(), newPolyCon);
        assertNotSame(getBlock1(), newPolyCon);
    }

    /**
     * Copies an entire structure of PolyConnectors.
     */
    @Test
    public void testCopyAFormationOfPolyConnectors() {
        verifyPreCondition();
        connectAsStarAndPentagram();
        PolyBlock newOne = getBlock1().copy();
        assertEquals(getBlock1(), newOne);
        Iterator<PolyBlock> leftIterator = getBlock1().iterator();
        Iterator<PolyBlock> rightIterator = newOne.iterator();
        for(int i = 0; i < getBlock1().size(); i++) {
            PolyBlock left = leftIterator.next();
            PolyBlock right = rightIterator.next();
            assertEquals(left, right);
            assertNotSame(left, right);
        }
    }

    public PolyBlock getBlock1() {
        return block1;
    }

    public void setBlock1(PolyBlock aBlock) {
        this.block1 = aBlock;
    }

    public PolyBlock getBlock2() {
        return block2;
    }

    public void setBlock2(PolyBlock aBlock) {
        this.block2 = aBlock;
    }

    public PolyBlock getBlock3() {
        return block3;
    }

    public void setBlock3(PolyBlock aBlock) {
        this.block3 = aBlock;
    }

    public PolyBlock getBlock4() {
        return block4;
    }

    public void setBlock4(PolyBlock aBlock) {
        this.block4 = aBlock;
    }

    public PolyBlock getBlock5() {
        return block5;
    }

    public void setBlock5(PolyBlock aBlock) {
        this.block5 = aBlock;
    }
}
