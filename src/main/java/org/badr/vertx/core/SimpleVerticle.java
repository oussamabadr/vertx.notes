package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;


class SimpleVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(SimpleVerticle.class.getName());
  }

  @Override
  public void start() throws Exception {
    System.out.println("I'm starting...");
    super.start();

    System.out.println("I'm now in started state");
  }

  @Override
  public void stop() throws Exception {
    System.out.println("Stopping in progress...");
    super.stop();
    System.out.println("I'm now in stopped state");
  }
}
