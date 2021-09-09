package org.badr.vertx.core;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.stream.Stream;

public class JsonObjectVertx {


  public static void main(String[] args) {
      System.out.println("Empty json --> "  + new JsonObject().encode());
      System.out.println("Json from String --> "  + new JsonObject("{\"toto\":\"value\"}").encodePrettily());


      Stream.of("{\"toto\":\"value\"}", "[]", "[true, \"tata\", 10]")
          .map(JsonObjectVertx::getValidRepresentation)
          .forEach(System.out::println);
  }

  private static String getValidRepresentation(String possibleJson) {
      Object object = Json.decodeValue(possibleJson);
      if (object instanceof JsonObject) {
          return "JsonObject --> " + object;
      } else if (object instanceof JsonArray) {
          return "JsonArray --> " + object;
      }

      throw new IllegalArgumentException("Invalid Json format");
  }

}
