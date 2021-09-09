package org.badr.vertx.core;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileProps;

public class FutureVertx {


  public static void main(String[] args) {
    var vertx = Vertx.vertx();

    var fs = vertx.fileSystem();

    Future<FileProps> future = fs.props( "futureVertx.txt");

    future.onComplete((AsyncResult<FileProps> ar) -> {
      if (ar.succeeded()) {
        FileProps props = ar.result();
        System.out.println("File size = " + props.size());
      } else {
        System.out.println("Failure: " + ar.cause().getMessage());
      }
    });

    // Future composition
    var testFile = "testFile.txt";
    fs.createFile(testFile).compose(unused -> {
      System.out.println("File created");
      return Future.succeededFuture("OK");
    }).compose(o -> {
      System.out.println("File deleted");
      return fs.delete(testFile);
    });

    // Some fucntional style using Future
    future.map(fileProps -> {
      System.out.println("mapFuture........");
      return "mapFuture";
    });

  }

}
