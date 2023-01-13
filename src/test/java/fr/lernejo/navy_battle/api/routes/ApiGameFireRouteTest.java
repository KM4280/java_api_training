package fr.lernejo.navy_battle.api.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.lernejo.navy_battle.api.ApiResponse;
import fr.lernejo.navy_battle.game.Game;
import fr.lernejo.navy_battle.game.board.CellConverter;
import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.board.self.Boat;
import fr.lernejo.navy_battle.game.board.self.SelfBoard;
import fr.lernejo.navy_battle.game.strategy.ComputerPlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApiGameFireRouteTest {

    private Game game;
    private ApiGameFireRoute route;

    @Before
    public void setUp() {
        this.game = new Game(UUID.randomUUID().toString(), "localhost:8080", new ComputerPlayer(), false);
        this.route = new ApiGameFireRoute(this.game, () -> {});
    }

    @Test
    public void test_404() {
        final ApiResponse response = this.route.handle("POST", new HashMap<>(), null);
        Assert.assertEquals(response.getStatus(), 404);
        Assert.assertEquals(response.getBody(), "Not Found: Method Not Allowed");
    }

    @Test
    public void test() {
        this.game.getSelfBoard().addBoat(new CellCoordinates(1, 2), SelfBoard.Direction.VERTICAL, new Boat(3));
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("cell", new CellConverter().convert(1, 3));
        final ApiResponse response = this.route.handle("GET", queryParams, null);
        Assert.assertEquals(response.getStatus(), 200);
        final Map<?, ?> body = (Map<?, ?>) response.getBody();
        Assert.assertEquals(body.get("consequence"), "hit");
        Assert.assertEquals(body.get("shipLeft"), true);
    }

    @After
    public void tearDown() {
        this.game = null;
        this.route = null;
    }
}
