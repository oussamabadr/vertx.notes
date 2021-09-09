package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class VerticleJsonObjectConf extends AbstractVerticle {

  public static void main(String[] args) {
    JsonObject config = new JsonObject().put("port", 8888).put("start", LocalDateTime.now().toInstant(ZoneOffset.UTC));
    DeploymentOptions options = new DeploymentOptions().setConfig(config);
    Vertx.vertx().deployVerticle(VerticleJsonObjectConf.class.getName(), options);
  }

  @Override
  public void start(Promise<Void> startPromise) {
    var port = config().getInteger("port");
    System.out.println("Deployment start at " +  config().getValue("start"));
    vertx.createHttpServer().requestHandler(req ->
      req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!"))
          .listen(port, http -> onServerStarted(http, startPromise));
  }

  private void onServerStarted(AsyncResult<HttpServer> result, Promise<Void> startPromise) {
    if (result.succeeded()) {
      startPromise.complete();
      System.out.println("HTTP server started on port " +  config().getInteger("port") );
    } else {
      startPromise.fail(result.cause());
    }
  }


}
