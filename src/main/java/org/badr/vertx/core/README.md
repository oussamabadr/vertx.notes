#Vertx.core

// TODO thing to avoid

+ Fluent and fluent API

`request.response().putHeader("Content-Type", "text/plain").end("some text");`

same as
```
HttpServerResponse response = request.response();
response.putHeader("Content-Type", "text/plain");
response.write("some text");
response.end();
```

+ Event driven

+ Handler will be called (by the event loop) when event occurred.

+ Vertx. implement Multi-Reactor Pattern to handle request with event loop unlike Node.js that use single threaded reactor pattern.

+ By default, Vert.x set several event loops based on number of cores (customizable ).

+ handler (except worker verticles) won't be executed concurrently (~ always called by same event loop).

+ A Warning will be logged if an event loop is block more than `VertxOptions.getMaxEventLoopExecuteTime()`

+ Vert.x use `io.vertx.core.Future` to work with asynchronous results.

+ `io.vertx.core.Future` can be composed (one after another), or coordinated (run multiple in parallel) using `CompositeFuture`.

+ `io.vertx.core.Future` can be converted from/to `java.util.concurrent.CompletionStage` (use vert.x context to convert to `Future`).


###Verticles:

+ Verticle is actor-model implementation (not strict)

+ Verticle classes (implementing `io.vertx.core.AbstractVerticle`) are actors.

+ Verticle communicate using `io.vertx.core.eventbus`

+ Use the async methods `AbstractVerticle.start(Promise<Void>)/.stop(Promise<Void>)` for long task: Signal DONE to vert.x using `Promise.complete/fail...`.

+ Use the sync methods `AbstractVerticle.start()/.stop()` for short task.

+ Vert.x stop any running server automatically when verticle is undeployed.
  VerticleFactory
+ Two type of verticle:
  - **Standard Verticles:** Executed using event loop, always thread safe (unless you start creating your own threads).
  - **Worker Verticles:** Executed using thread from worker pool, insatance is always executed by one thread at a time.

+ Standard verticle intance is assigned to one event loop at creation
+ Worker verticles can be used for blocking task, you can use `new DeploymentOptions().setWorker(true)` to set verticle as worker

+ You can deploy verticle using `vertx.deployVerticle(...)` and undeploy using `Vertx.undeploy(String deploymentID)`, both methods run asynchronously and have a callback version.

+ `Vertx.deployVerticle(...)`:
  - Can deploy verticle written in any language.
  - `name` parameter can specify prefix e.g:`js:myVerticle.js`, or file extension `myVerticle.js` to lookup factory to instantiate the verticle,
  - If no prefix and no extension than Java factory is used.
  - You can deploy multiple instance of a verticle `new DeploymentOptions().setInstances(int instances)`, like if you want to use more than one core of your machine
  - Configuration can be passed using `io.vertx.core.json.JsonObject`

+ `Vertx.registerVerticleFactory(VerticleFactory)` can be used to register a factory, to unregister use `Vertx.unregisterVerticleFactory(VerticleFactory)`.

+ Environment variables are accessed using `System.getProperty(String)` and `System.getenv(String)`

+ Vert.x app can be controller via `vertx` command line, you need to install `Vert.x` CLI  first, e.g: check https://vertx.io/download/#from-sdkman for SDKMan
 - You can run Verticle with just: `vertx run MainVerticle.java`, No need for Vert.x dependencies.

+ Vert.x handler always run in a `io.vertx.core.Context` or also start/stop verticle run in a context.
  - Handlers that run in the same context can share data using: `Context.get(String)`/`Context.put(String, Object)`.

+ Don't use thread sleep so you don't block the event loop thread.
 - Instead use **One-shot Timer**: `vertx.setTimer(...)`
 - Or Periodic Timers: `vertx.setPeriodic(...)`
 - You can cancel timer using: `vertx.cancelTimer(timerID)`
 - Timers in verticle will be cleaned up if undeployed.

+ `io.vertx.core.eventbus.EventBus`:
 - Only one eventBus per Vert.x instance
 - Used to exchange messages btw Verticles (even in different languages or from other Vert.x application)
 - Topic/Address is just as simple `String`, preferable to use dots between words e.g: `europe.news.feed1`
 - consumer Handler <(0,n)-------(0,n)> Address
 - Message queue (point-to-point) use `EventBus.send(...)`
 - For publish/subscriber use `EventBus.publish(...)`.
 - request-response (request-reply-request-reply....infinite) is supported.
 - Define `io.vertx.core.eventbus.MessageCodec` if you don't want to use JSON/`io.vertx.core.buffer.Buffer` format for Ser/Deserialization.
 - Header can be defined using `io.vertx.core.eventbus.DeliveryOptions`
 - Messages are delivered in order of every consumer.
 - Consumer can send back a message using `io.vertx.core.eventbus.Message.reply(java.lang.Object)` but Sender should send message with `io.vertx.core.eventbus.EventBus.request(java.lang.String, java.lang.Object, io.vertx.core.Handler<io.vertx.core.AsyncResult<io.vertx.core.eventbus.Message<T>>>)`
 - `io.vertx.core.eventbus.MessageCodec` can be used to serialize/deserialize Object sent.
 - Register Codec using `DeliveryOptions` in you message or permanently with `EventBus.registerDefaultCoded(Class, MessageCodec)`


+ Json is supported using `io.vertx.core.json.JsonObject`
 - To map to an object using `io.vertx.core.json.JsonObject.mapTo(Class)`
 - To create Json from an object using `io.vertx.core.json.JsonObject.mapFrom(Object)`



