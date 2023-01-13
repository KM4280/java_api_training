package fr.lernejo.navy_battle.game.board;

import org.junit.Assert;
import org.junit.Test;

public class CellCoordinatesTest {

    @Test
    public void test0_0() {
        final CellCoordinates coordinates = new CellCoordinates(0, 0);
        Assert.assertEquals(coordinates.getX(), 0);
        Assert.assertEquals(coordinates.getY(), 0);
    }

    @Test
    public void test5_4() {
        final CellCoordinates coordinates = new CellCoordinates(5, 4);
        Assert.assertEquals(coordinates.getX(), 5);
        Assert.assertEquals(coordinates.getY(), 4);
    }

    @Test
    public void test9_9() {
        final CellCoordinates coordinates = new CellCoordinates(9, 9);
        Assert.assertEquals(coordinates.getX(), 9);
        Assert.assertEquals(coordinates.getY(), 9);
    }

    @Test
    public void test0_minus1() {
        Assert.assertThrows(
            IllegalArgumentException.class,
            () -> new CellCoordinates(0, -1)
        );
    }

    @Test
    public void test_minus2_1() {
        Assert.assertThrows(
            IllegalArgumentException.class,
            () -> new CellCoordinates(-2, 1)
        );
    }

    @Test
    public void test12_2() {
        Assert.assertThrows(
            IllegalArgumentException.class,
            () -> new CellCoordinates(12, 2)
        );
    }

    @Test
    public void test2_24() {
        Assert.assertThrows(
            IllegalArgumentException.class,
            () -> new CellCoordinates(2, 24)
        );
    }

    @Test
    public void test_equals() {
        Assert.assertEquals(
            new CellCoordinates(2, 5),
            new CellCoordinates(2, 5)
        );
    }

    @Test
    public void test_notEquals1() {
        Assert.assertNotEquals(
            new CellCoordinates(2, 6),
            new Object()
        );
    }

    @Test
    public void test_notEquals2() {
        Assert.assertNotEquals(
            new CellCoordinates(2, 5),
            new CellCoordinates(2, 6)
        );
    }
}
