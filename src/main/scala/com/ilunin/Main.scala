package com.ilunin

import akka.actor.ActorSystem
import spray.client.pipelining._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Main extends App{

  implicit val system = ActorSystem("client")

  val server = new Server

  println("Test 1")
  server.start()
  println(doRequest())
  server.stop()

//  Thread.sleep(10)
  println("Test 2")
  server.start()
  println(doRequest())
  server.stop()

  println("Test 3")
  server.start()
  println(doRequest())
  server.stop()
  
  def doRequest(): String = {
    val pipeline = sendReceive
    Await.result(pipeline(Post("http://localhost:8080/test")).map(_.entity.asString), 1 second)
  }

}
