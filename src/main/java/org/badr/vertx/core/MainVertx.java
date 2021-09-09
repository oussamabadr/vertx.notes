package org.badr.vertx.core;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

public class MainVertx {


  public static void main(String[] args) {
//    Vertx vertx = Vertx.vertx();
    var vertxOptions = new VertxOptions()
                                            .setWorkerPoolSize(15) // default VertxOptions.DEFAULT_WORKER_POOL_SIZE
                                            .setEventLoopPoolSize(5); // default VertxOptions.DEFAULT_EVENT_LOOP_POOL_SIZE
    var vertx = Vertx.vertx(vertxOptions);

    vertx.createHttpServer().requestHandler(req -> {
      try {
        System.out.println("I'm sleeping...");
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        System.out.println("HTTP server started on port 8888");
      }
    });
  }

}
