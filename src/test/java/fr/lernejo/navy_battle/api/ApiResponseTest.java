package fr.lernejo.navy_battle.api;

import org.junit.Assert;
import org.junit.Test;

public class ApiResponseTest {

    @Test
    public void test() {
        final ApiResponse response = new ApiResponse(200, "OK");
        Assert.assertEquals(response.getStatus(), 200);
        Assert.assertEquals(response.getBody(), "OK");
    }
}
