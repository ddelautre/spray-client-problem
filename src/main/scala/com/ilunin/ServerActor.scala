package com.ilunin

import akka.actor.Actor
import spray.can.Http
import spray.http.{HttpEntity, HttpRequest, HttpResponse}

class ServerActor extends Actor {

  override def receive: Receive = {
    case _: Http.Connected => sender ! Http.Register(self)
    case _: HttpRequest => sender() ! HttpResponse(entity = HttpEntity("test"))
  }

}