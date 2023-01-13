package fr.lernejo.navy_battle.game;

import fr.lernejo.navy_battle.game.board.opponent.OpponentBoard;
import fr.lernejo.navy_battle.game.board.self.Boat;
import fr.lernejo.navy_battle.game.board.self.SelfBoard;
import fr.lernejo.navy_battle.game.strategy.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Game {

    private final String selfId;
    private final String selfUrl;
    private final CompletableFuture<String> opponentId;
    private final CompletableFuture<String> opponentUrl;

    private final SelfBoard selfBoard;
    private final OpponentBoard opponentBoard;

    public Game(final String selfId, final String selfUrl, final Player player, final boolean fill) {
        this.selfId = selfId;
        this.selfUrl = selfUrl;

        this.opponentId = new CompletableFuture<>();
        this.opponentUrl = new CompletableFuture<>();

        this.selfBoard = new SelfBoard();
        this.opponentBoard = new OpponentBoard();

        if (fill) {
            final List<Boat> boats = new ArrayList<>();
            boats.add(new Boat(2));
            boats.add(new Boat(3));
            boats.add(new Boat(3));
            boats.add(new Boat(4));
            boats.add(new Boat(5));
            player.placeBoats(boats, this.selfBoard);
        }
    }

    public String getSelfId() {
        return selfId;
    }

    public String getSelfUrl() {
        return selfUrl;
    }

    public Future<String> getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(final String opponentId) {
        this.opponentId.complete(opponentId);
    }

    public Future<String> getOpponentUrl() {
        return opponentUrl;
    }

    public void setOpponentUrl(final String opponentUrl) {
        this.opponentUrl.complete(opponentUrl);
    }

    public SelfBoard getSelfBoard() {
        return selfBoard;
    }

    public OpponentBoard getOpponentBoard() {
        return opponentBoard;
    }
}
