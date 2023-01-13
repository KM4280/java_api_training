package fr.lernejo.navy_battle.game.strategy;

import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.board.opponent.OpponentBoard;
import fr.lernejo.navy_battle.game.board.self.Boat;
import fr.lernejo.navy_battle.game.board.self.SelfBoard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayerTest {

    @Test
    public void test_place() {
        final Player player = new ComputerPlayer();
        final List<Boat> boats = new ArrayList<>();
        boats.add(new Boat(2));
        boats.add(new Boat(5));
        final SelfBoard board = new SelfBoard();
        player.placeBoats(boats, board);
    }

    @Test
    public void test_place_full() {
        final Player player = new ComputerPlayer();
        final List<Boat> boats = new ArrayList<>();
        boats.add(new Boat(3));
        final SelfBoard board = new SelfBoard();
        for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
            for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y += 2) {
                board.addBoat(new CellCoordinates(x, y), SelfBoard.Direction.VERTICAL, new Boat(2));
            }
        }
        Assert.assertThrows(
            IllegalStateException.class,
            () -> player.placeBoats(boats, board)
        );
    }

    @Test
    public void test_give() {
        final Player player = new ComputerPlayer();
        final OpponentBoard board = new OpponentBoard();
        player.giveCoordinates(board);
    }

}
