package org.badr.vertx.core;

import io.vertx.core.Vertx;

public class BlockingEventLoop {


  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.setPeriodic(1000, id -> {
      try {
        System.out.println("I'm sleeping using timer id=[" + id + "]...");
        Thread.sleep(7000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

  }

}
