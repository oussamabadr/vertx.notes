package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.concurrent.TimeUnit;

public class AcknowledgeEventBusVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(AcknowledgeEventBusVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    var eventBus = vertx.eventBus();

    var address_1 = "toto.titi.lolo";

    MessageConsumer<String> consumer = eventBus.consumer(address_1);

    consumer.handler(message -> {
      System.out.println("Consumer: Who wake up me ????");
      message.reply("Who are you to wake up me");
    });

    eventBus.request(address_1, "Is there anyone here ???", result -> {
      System.out.println("Sender: And I got an answer --> " + result.result().body());
    });


    vertx.setTimer(1000, unused -> {
      consumer.handler(message -> {
        System.out.println("Consumer: Who wake up me again???? no reply from now on....I will make him wait");
        vertx.setTimer(TimeUnit.SECONDS.toMillis(3), event -> {
        });
      });

      var options = new DeliveryOptions().setSendTimeout(TimeUnit.SECONDS.toMillis(1));
      eventBus.request(address_1, "Are you here ???", options, result -> {
        if (result.failed()) {
          System.out.println("Why he is not responding.....?");
        }
      });

      vertx.setTimer(1000, u -> {
        vertx.close();
      });
    });
  }

}
