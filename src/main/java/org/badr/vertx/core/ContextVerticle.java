package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class ContextVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(ContextVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    var context = vertx.getOrCreateContext();
    context.put("data", "hello");
    context.runOnContext( action -> System.out.println("Shared data is --> " + context.get("data")) );
  }

}
