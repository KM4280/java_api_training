package fr.lernejo.navy_battle.api.routes;

import fr.lernejo.navy_battle.api.ApiResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class NotFoundRouteTest {

    private NotFoundRoute route;

    @Before
    public void setUp() {
        this.route = new NotFoundRoute();
    }

    @Test
    public void test() {
        final ApiResponse response = this.route.handle("GET", new HashMap<>(), null);
        Assert.assertEquals(response.getStatus(), 404);
        Assert.assertEquals(response.getBody(), "Not Found");
    }

    @After
    public void tearDown() {
        this.route = null;
    }
}
