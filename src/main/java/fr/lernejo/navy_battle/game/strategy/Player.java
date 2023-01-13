package fr.lernejo.navy_battle.game.strategy;

import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.board.opponent.OpponentBoard;
import fr.lernejo.navy_battle.game.board.self.Boat;
import fr.lernejo.navy_battle.game.board.self.SelfBoard;

import java.util.List;

public interface Player {

    void placeBoats(final List<Boat> boats, final SelfBoard board);
    CellCoordinates giveCoordinates(final OpponentBoard board);
}
