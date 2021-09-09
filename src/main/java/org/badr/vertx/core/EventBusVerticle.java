package org.badr.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageConsumer;

import java.util.stream.IntStream;

public class EventBusVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(EventBusVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) {
    var eventBus = vertx.eventBus();

    var address_1 = "toto.titi.lolo";

    eventBus.consumer(address_1, event -> System.out.println("Hey, you've got a message --> " + event.body()));

    MessageConsumer<Object> messageConsumer = eventBus.consumer(address_1);
    messageConsumer.handler(event -> {
      System.out.println("Hoy, another message here --> " + event.body());
      if (!event.headers().isEmpty()) {
        System.out.println("With header --> " + event.headers());
        event.reply("I don't trust you!!!");
      }
    });

    eventBus.send(address_1, "I want just to talk to one of the consumers");

    // after no answer, I will talk to everyone
    eventBus.publish(address_1, "is there anyone here ???");

    var deliveryOptions = new DeliveryOptions().addHeader("token", "I'm toto, plz trust me!!!");

    eventBus.send(address_1, "Why no one is answering my call ????", deliveryOptions);

    eventBus.send(address_1, "Why no one is answering my call ????", deliveryOptions);

    IntStream.range(0, 10).forEach( value -> eventBus.publish(address_1, "And now I will spam you with ordered messages - spam No: " + value) );
//
//    messageConsumer.unregister(event -> {
//      if (event.failed()) {
//        System.out.println("Something wrong happen....so I'm stuck here!!!!!");
//      }
//    });

    vertx.close();
  }

}
