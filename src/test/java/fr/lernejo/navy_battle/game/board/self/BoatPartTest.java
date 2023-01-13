package fr.lernejo.navy_battle.game.board.self;

import org.junit.Assert;
import org.junit.Test;

public class BoatPartTest {

    @Test
    public void test() {
        final BoatPart part = new BoatPart(null);
        Assert.assertTrue(part.isValid());
        part.fire();
        Assert.assertFalse(part.isValid());
    }
}
