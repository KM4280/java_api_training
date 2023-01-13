package fr.lernejo.navy_battle.game.board.opponent;

import fr.lernejo.navy_battle.game.board.CellCoordinates;

public class OpponentBoard {

    public enum Cell {
        EMPTY,
        HIT,
        SUNK
    }

    private final Cell[][] grid;

    public OpponentBoard() {
        this.grid = new Cell[CellCoordinates.HORIZONTAL_SIZE][CellCoordinates.VERTICAL_SIZE];
        for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
            for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
                this.grid[x][y] = null;
            }
        }
    }

    public Cell getCell(final CellCoordinates coordinates) {
        return grid[coordinates.getX()][coordinates.getY()];
    }

    public void setCell(final CellCoordinates coordinates, final Cell cell) {
        grid[coordinates.getX()][coordinates.getY()] = cell;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Opponent board:\n");

        str.append("/");
        str.append("-".repeat(CellCoordinates.VERTICAL_SIZE));
        str.append("\\\n");

        for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
            str.append("|");
            for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
                if (this.grid[x][y] == Cell.EMPTY) {
                    str.append("-");
                } else if (this.grid[x][y] == Cell.HIT) {
                    str.append("#");
                } else if (this.grid[x][y] == Cell.SUNK) {
                    str.append("S");
                } else {
                    str.append(" ");
                }
            }
            str.append("|\n");
        }

        str.append("\\");
        str.append("-".repeat(CellCoordinates.VERTICAL_SIZE));
        str.append("/");

        return str.toString();
    }
}
