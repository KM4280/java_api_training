package fr.lernejo.navy_battle.game.board.opponent;

import fr.lernejo.navy_battle.game.board.CellCoordinates;
import org.junit.Assert;
import org.junit.Test;

public class OpponentBoardTest {

    @Test
    public void test() {
        final OpponentBoard board = new OpponentBoard();
        System.out.println(board);
        Assert.assertNull(board.getCell(new CellCoordinates(5, 5)));
        board.setCell(new CellCoordinates(5, 5), OpponentBoard.Cell.HIT);
        System.out.println(board);
        Assert.assertEquals(board.getCell(new CellCoordinates(5, 5)), OpponentBoard.Cell.HIT);
        board.setCell(new CellCoordinates(5, 6), OpponentBoard.Cell.EMPTY);
        board.setCell(new CellCoordinates(4, 5), OpponentBoard.Cell.SUNK);
        System.out.println(board);
    }
}
