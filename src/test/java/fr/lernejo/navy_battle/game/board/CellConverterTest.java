package fr.lernejo.navy_battle.game.board;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CellConverterTest {

    private CellConverter converter;

    @Before
    public void setUp() {
        this.converter = new CellConverter();
    }

    @Test
    public void testA1() {
        final CellCoordinates coordinates = this.converter.convert("A1");
        Assert.assertEquals(coordinates.getX(), 0);
        Assert.assertEquals(coordinates.getY(), 0);
    }

    @Test
    public void testB4() {
        final CellCoordinates coordinates = this.converter.convert("B4");
        Assert.assertEquals(coordinates.getX(), 3);
        Assert.assertEquals(coordinates.getY(), 1);
    }

    @Test
    public void testJ10() {
        final CellCoordinates coordinates = this.converter.convert("J10");
        Assert.assertEquals(coordinates.getX(), 9);
        Assert.assertEquals(coordinates.getY(), 9);
    }

    @Test
    public void testUnknownCell() {
        Assert.assertNull(this.converter.convert("X6"));
    }

    @Test
    public void test0_0() {
        Assert.assertEquals(this.converter.convert(0, 0), "A1");
    }

    @Test
    public void test3_1() {
        Assert.assertEquals(this.converter.convert(1, 3), "D2");
    }

    @Test
    public void test9_9() {
        Assert.assertEquals(this.converter.convert(9, 9), "J10");
    }

    @After
    public void tearDown() {
        this.converter = null;
    }
}
