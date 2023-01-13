package fr.lernejo.navy_battle.api.routes;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.lernejo.navy_battle.api.ApiResponse;
import fr.lernejo.navy_battle.game.Game;
import fr.lernejo.navy_battle.game.strategy.ComputerPlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ApiGameStartRouteTest {

    private Game game;
    private ApiGameStartRoute route;

    @Before
    public void setUp() {
        this.game = new Game(UUID.randomUUID().toString(), "localhost:8080", new ComputerPlayer(), false);
        this.route = new ApiGameStartRoute(this.game, () -> {});
    }

    @Test
    public void test_404() {
        final ApiResponse response = this.route.handle("GET", new HashMap<>(), null);
        Assert.assertEquals(response.getStatus(), 404);
        Assert.assertEquals(response.getBody(), "Not Found: Method Not Allowed");
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        final JsonObject body = new JsonObject();
        final String id = UUID.randomUUID().toString();
        final String url = "http://localhost:8080";
        Assert.assertFalse(this.game.getOpponentId().isDone());
        Assert.assertFalse(this.game.getOpponentUrl().isDone());
        body.add("id", new JsonPrimitive(id));
        body.add("url", new JsonPrimitive(url));
        body.add("message", new JsonPrimitive("Test"));
        final ApiResponse response = this.route.handle("POST", new HashMap<>(), body);
        Assert.assertEquals(response.getStatus(), 202);
        Assert.assertTrue(this.game.getOpponentId().isDone());
        Assert.assertEquals(this.game.getOpponentId().get(), id);
        Assert.assertTrue(this.game.getOpponentUrl().isDone());
        Assert.assertEquals(this.game.getOpponentUrl().get(), url);
    }

    @After
    public void tearDown() {
        this.game = null;
        this.route = null;
    }
}
