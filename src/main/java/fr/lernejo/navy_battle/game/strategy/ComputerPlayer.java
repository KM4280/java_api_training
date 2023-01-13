package fr.lernejo.navy_battle.game.strategy;

import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.board.opponent.OpponentBoard;
import fr.lernejo.navy_battle.game.board.self.Boat;
import fr.lernejo.navy_battle.game.board.self.SelfBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer implements Player {

    private final Random rand;

    public ComputerPlayer() {
        this.rand = new Random();
    }

    private record CoordinatesDirection(CellCoordinates coordinates, SelfBoard.Direction direction) {
    }

    @Override
    public void placeBoats(final List<Boat> boats, final SelfBoard board) {
        for (final Boat boat : boats) {
            final List<CoordinatesDirection> possiblePlacements = new ArrayList<>();
            for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
                for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
                    final CellCoordinates coordinates = new CellCoordinates(x, y);
                    if (board.canPlaceBoat(coordinates, SelfBoard.Direction.HORIZONTAL, boat)) {
                        possiblePlacements.add(
                            new CoordinatesDirection(coordinates, SelfBoard.Direction.HORIZONTAL)
                        );
                    }
                    if (board.canPlaceBoat(coordinates, SelfBoard.Direction.VERTICAL, boat)) {
                        possiblePlacements.add(
                            new CoordinatesDirection(coordinates, SelfBoard.Direction.VERTICAL)
                        );
                    }
                }
            }
            if (possiblePlacements.isEmpty()) {
                throw new IllegalStateException("No possible placements");
            }
            final int randomIndex = rand.nextInt(possiblePlacements.size());
            final CoordinatesDirection placement = possiblePlacements.get(randomIndex);
            board.addBoat(placement.coordinates(), placement.direction(), boat);
        }
    }

    @Override
    public CellCoordinates giveCoordinates(final OpponentBoard board) {
        final List<CellCoordinates> possibleShots = new ArrayList<>();
        for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
            for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
                final CellCoordinates coordinates = new CellCoordinates(x, y);
                if (board.getCell(coordinates) == null) {
                    possibleShots.add(coordinates);
                }
            }
        }
        if (possibleShots.isEmpty()) {
            throw new IllegalStateException("No possible shots");
        }
        final int randomIndex = rand.nextInt(possibleShots.size());
        return possibleShots.get(randomIndex);
    }
}
