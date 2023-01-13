package fr.lernejo.navy_battle.api;

public class ApiResponse {

    private final int status;
    private final Object body;

    public ApiResponse(final int status, final Object body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public Object getBody() {
        return body;
    }
}
