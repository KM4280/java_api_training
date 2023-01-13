package fr.lernejo.navy_battle.game.board;

import java.util.HashMap;
import java.util.Map;

public class CellConverter {

    private final Map<String, CellCoordinates> cellCoordinatesMap;
    private final Map<CellCoordinates, String> coordinatesCellMap;

    public CellConverter() {
        this.cellCoordinatesMap = new HashMap<>();
        this.coordinatesCellMap = new HashMap<>();
        for (int x = 0; x < CellCoordinates.HORIZONTAL_SIZE; x++) {
            for (int y = 0; y < CellCoordinates.VERTICAL_SIZE; y++) {
                final String cell = (char) ('A' + y) + Integer.toString(x + 1);
                final CellCoordinates coordinates = new CellCoordinates(x, y);
                this.cellCoordinatesMap.put(cell, coordinates);
                this.coordinatesCellMap.put(coordinates, cell);
            }
        }
    }

    public CellCoordinates convert(final String cell) {
        return this.cellCoordinatesMap.get(cell);
    }

    public String convert(final int x, final int y) {
        final CellCoordinates coordinates = new CellCoordinates(x, y);
        return this.coordinatesCellMap.get(coordinates);
    }
}
