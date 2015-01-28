package com.ilunin

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.ilunin.ServerManagerActor.{Start, Stop}
import spray.routing.SimpleRoutingApp

import scala.concurrent.Await
import scala.concurrent.duration._

class Server extends SimpleRoutingApp {
  implicit val system = ActorSystem("server")
  implicit val timeout = new Timeout(1 second)

  val serverManager: ActorRef = system.actorOf(Props(classOf[ServerManagerActor]), "serverManager")

  def start(): Unit = Await.result(serverManager ? Start, 1 second)

  def stop(): Unit = Await.result(serverManager ? Stop, 1 second)
}

