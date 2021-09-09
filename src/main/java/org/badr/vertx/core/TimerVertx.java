package org.badr.vertx.core;

import io.vertx.core.Vertx;

public class TimerVertx {


  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.setPeriodic(1000, id -> {
      // This handler will get called every second
      System.out.println("timer fired!");
    });

  }

}
