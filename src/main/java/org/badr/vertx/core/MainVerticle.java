package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(MainVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer().requestHandler(req ->
      req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!"))
          .listen(8888, http -> onServerStarted(http, startPromise));
  }

  private void onServerStarted(AsyncResult<HttpServer> result, Promise<Void> startPromise) {
    if (result.succeeded()) {
      startPromise.complete();
      System.out.println("HTTP server started on port 8888");
    } else {
      startPromise.fail(result.cause());
    }
  }


}
