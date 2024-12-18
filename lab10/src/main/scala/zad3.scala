import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case object Pilka3
case class Kolejny3(actor: ActorRef)

class Player3 extends Actor with ActorLogging {
  def receive: Receive = {
    case Kolejny3(actor) => {
      log.info("Gram z " + actor)
      context.become(withNext(actor))
    }
    case Pilka3 => {
      log.warning(s"${self.path.name} - nie mam z kim grac")
    }
  }
  def withNext(next: ActorRef): Receive = {
    case Pilka3 => {
      log.info(s"${self.path.name} - odbijam pilke")
      next ! Pilka3
    }
  }
}

@main
def zad3: Unit = {

  val system = ActorSystem("system")

  val players = for {
    i <- (1 to 100)
  } yield system.actorOf(Props[Player3](), s"player${i}")

  for {
    i <- (0 until players.length - 1)
  } players(i) ! Kolejny3(players(i+1))

  players(players.length - 1) ! Kolejny3(players(0))

  players.head ! Pilka3
  
}
