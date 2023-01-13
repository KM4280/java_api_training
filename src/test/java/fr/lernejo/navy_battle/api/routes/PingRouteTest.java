package fr.lernejo.navy_battle.api.routes;

import fr.lernejo.navy_battle.api.ApiResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class PingRouteTest {

    private PingRoute route;

    @Before
    public void setUp() {
        this.route = new PingRoute();
    }

    @Test
    public void test_404() {
        final ApiResponse response = this.route.handle("POST", new HashMap<>(), null);
        Assert.assertEquals(response.getStatus(), 404);
        Assert.assertEquals(response.getBody(), "Not Found: Method Not Allowed");
    }

    @Test
    public void test() {
        final ApiResponse response = this.route.handle("GET", new HashMap<>(), null);
        Assert.assertEquals(response.getStatus(), 200);
        Assert.assertEquals(response.getBody(), "OK");
    }

    @After
    public void tearDown() {
        this.route = null;
    }
}
