package fr.lernejo.navy_battle.game.board.self;

import fr.lernejo.navy_battle.game.board.CellCoordinates;

// j'ai retiré des sauts de lignes et des commentaires comme un gros sale pour pas avoir une classe de + de 90 lignes
public class SelfBoard {
    private final BoatPart[][] grid;
    public SelfBoard() {
        this.grid = new BoatPart[CellCoordinates.HORIZONTAL_SIZE][CellCoordinates.VERTICAL_SIZE];
        for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
            for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
                this.grid[x][y] = null;
            }
        }
    }
    public enum Direction { HORIZONTAL, VERTICAL }
    public boolean canPlaceBoat(final CellCoordinates coordinates, final Direction direction, final Boat boat) {
        if (direction == Direction.HORIZONTAL) {
            if (coordinates.getX() + boat.getLength() > CellCoordinates.HORIZONTAL_SIZE) {
                return false;
            }
            for (int x = coordinates.getX(); x < coordinates.getX() + boat.getLength(); x++) {
                if (this.grid[x][coordinates.getY()] != null) {
                    return false;
                }
            }
            return true;

        } else if (direction == Direction.VERTICAL) {
            if (coordinates.getY() + boat.getLength() > CellCoordinates.VERTICAL_SIZE) {
                return false;
            }
            for (int y = coordinates.getY(); y < coordinates.getY() + boat.getLength(); y++) {
                if (this.grid[coordinates.getX()][y] != null) {
                    return false;
                }
            }
            return true;

        } else {
            throw new IllegalArgumentException("Direction is neither horizontal nor vertical");
        }
    }

    public void addBoat(final CellCoordinates coordinates, final Direction direction, final Boat boat) {
        if (!canPlaceBoat(coordinates, direction, boat)) {
            throw new IllegalArgumentException("Cannot place boat here");
        }
        if (direction == Direction.HORIZONTAL) {
            final BoatPart[] parts = boat.getParts();
            int partIndex = 0;
            for (int x = coordinates.getX(); x < coordinates.getX() + boat.getLength(); x++) {
                this.grid[x][coordinates.getY()] = parts[partIndex++];
            }
        } else {
            final BoatPart[] parts = boat.getParts();
            int partIndex = 0;
            for (int y = coordinates.getY(); y < coordinates.getY() + boat.getLength(); y++) {
                this.grid[coordinates.getX()][y] = parts[partIndex++];
            }
        }
    }
    public enum Fire { MISS, HIT, SUNK }
    public Fire fire(final CellCoordinates coordinates) {
        final BoatPart part = this.grid[coordinates.getX()][coordinates.getY()];
        if (part != null) {
            part.fire();
            if (part.getBoat().isValid()) {
                return Fire.HIT;
            } else {
                return Fire.SUNK;
            }
        } else {
            return Fire.MISS;
        }
    }
    public boolean isValid() {
        for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
            for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
                final BoatPart part = this.grid[x][y];
                if (part != null && part.isValid()) {
                    return true;
                }
            }
        }
        return false;
    }
}
