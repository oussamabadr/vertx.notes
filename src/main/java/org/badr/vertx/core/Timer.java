package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.util.concurrent.TimeUnit;

public class Timer extends AbstractVerticle {

  private long periodicTimerId;

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(Timer.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    periodicTimerId = vertx.setPeriodic(TimeUnit.SECONDS.toMillis(5), event -> {
      System.out.println("this is a periodic message using event id " + event);
    });

    long oneShottimerId = vertx.setTimer(TimeUnit.SECONDS.toMillis(3), event -> {
      System.out.println("this is a one-shot message using event id " + event);
    });

    System.out.println("Before periodic messages");
  }

  @Override
  public void stop() throws Exception {
    vertx.cancelTimer(periodicTimerId); // Optional timer will be cleaned up if Verticle is undeployed

    super.stop();
  }
}
