package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class MultipleVerticleInstances extends AbstractVerticle {

  public static void main(String[] args) {
    DeploymentOptions options = new DeploymentOptions().setInstances(3);
    Vertx.vertx().deployVerticle(MultipleVerticleInstances.class.getName(), options);
  }

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer().requestHandler(req -> {
        System.out.println("Thread.currentThread().getName() --> " + Thread.currentThread().getName());
        req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!");
      }
    ).listen(8888, http -> onServerStarted(http, startPromise));
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
