import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case class Pilka4(liczbaOdbic: Int)
case class Kolejny4(actor: ActorRef, maxLiczbaOdbic: Int)

class Player4 extends Actor with ActorLogging {
  def receive: Receive = {
    case Kolejny4(actor, max) => {
      log.info("Gram z " + actor)
      context.become(withNext(actor, max))
    }
    case Pilka4 => {
      log.warning(s"${self.path.name} - nie mam z kim grac")
    }
  }
  def withNext(next: ActorRef, maxLiczbaOdbic: Int): Receive = {
    case Pilka4(liczbaOdbic) => {
      if (liczbaOdbic < maxLiczbaOdbic) {
        log.info(s"${self.path.name} - odbijam pilke")
        next ! Pilka4(liczbaOdbic + 1)
      } else {
        log.info(s"${self.path.name} - koniec gry")
      }
    }
  }
}

@main
def zad4: Unit = {

  val system = ActorSystem("system")

  val players = for {
    i <- (1 to 100)
  } yield system.actorOf(Props[Player4](), s"player${i}")

  for {
    i <- (0 until players.length - 1)
  } players(i) ! Kolejny4(players(i+1), 123)

  players(players.length - 1) ! Kolejny4(players(0), 123)

  players.head ! Pilka4(0)
  
}
