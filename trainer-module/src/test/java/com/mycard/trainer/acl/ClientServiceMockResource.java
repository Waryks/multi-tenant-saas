package com.mycard.trainer.acl;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import java.util.Map;

public class ClientServiceMockResource implements QuarkusTestResourceLifecycleManager {

    private HttpServer server;

    @Override
    public Map<String, String> start() {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        router.get("/clients/by-trainer/:trainerId").handler(ctx -> {
            String json = """
                [{
                  "id": "11111111-1111-1111-1111-111111111111",
                  "fullName": "Client One",
                  "email": "client1@example.com",
                  "trainerId": "%s",
                  "profile": {
                    "age": 30,
                    "heightCm": 180.5,
                    "weightKg": 75.0,
                    "goalDescription": "Lose weight"
                  }
                }]
            """.formatted(ctx.pathParam("trainerId"));
            ctx.response().putHeader("Content-Type", "application/json").end(json);
        });

        server = vertx.createHttpServer()
                .requestHandler(router)
                .listen(8089) // mock port
                .result();

        // override the URL in config for the test
        return Map.of("client-service/mp-rest/url", "http://localhost:8089");
    }

    @Override
    public void stop() {
        if (server != null) server.close();
    }
}
