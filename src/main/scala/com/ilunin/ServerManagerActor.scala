package com.ilunin

import akka.actor.{Actor, ActorRef, Props}
import akka.io.IO
import com.ilunin.ServerManagerActor.{Stop, Start}
import spray.can.Http

class ServerManagerActor extends Actor {
  val myListener: ActorRef = context.actorOf(Props[ServerActor], "serverActor")
  var httpListener: ActorRef = _
  var server: ActorRef = _
  implicit val system = context.system

  override def receive: Receive = {
    case Start =>
      IO(Http) ! Http.Bind(myListener, interface = "localhost", port = 8080)
      server = sender()
    case Http.Bound(_) =>
      httpListener = sender()
      server ! Http.Bound
    case Stop => {
      httpListener ! Http.Unbind
      server = sender()
    }
    case Http.Unbound => server ! Http.Unbound
  }
}

object ServerManagerActor {
  case object Stop

  case object Start

}

