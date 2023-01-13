package fr.lernejo.navy_battle.game.board;

public class CellCoordinates {

    public static final int HORIZONTAL_SIZE = 10;
    public static final int VERTICAL_SIZE = 10;

    private final int x;
    private final int y;

    public CellCoordinates(final int x, final int y) {
        if (x < 0) {
            throw new IllegalArgumentException("X is too low");
        }
        if (x >= CellCoordinates.HORIZONTAL_SIZE) {
            throw new IllegalArgumentException("X is too high");
        }
        this.x = x;

        if (y < 0) {
            throw new IllegalArgumentException("Y is too low");
        }
        if (y >= CellCoordinates.VERTICAL_SIZE) {
            throw new IllegalArgumentException("Y is too high");
        }
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof CellCoordinates otherCoordinates) {
            return (this.x == otherCoordinates.x) && (this.y == otherCoordinates.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.x + this.y;
    }
}
