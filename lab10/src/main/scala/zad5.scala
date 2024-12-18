import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, PoisonPill, Props}

case object Pilka5
case class Kolejny5(actor: ActorRef, mojaMaxLiczbaOdbic: Int)

class Player5 extends Actor with ActorLogging {
  def receive: Receive = {
    case Kolejny5(actor, max) =>
      log.info(s"${self.path.name} - Gram z ${actor.path.name}, liczba odbić: $max")
      context.become(withNext(actor, max, 0))
      
    case Pilka5 =>
      log.warning(s"${self.path.name} - nie mam z kim grać!")
  }

  def withNext(next: ActorRef, mojaMaxLiczbaOdbic: Int, acc: Int = 0): Receive = {
    case Pilka5 =>
      if (acc < mojaMaxLiczbaOdbic) {
        log.info(s"${self.path.name} - odbijam piłkę (${acc + 1})")
        next ! Pilka5
        context.become(withNext(next, mojaMaxLiczbaOdbic, acc + 1))
      } else {
        log.info(s"${self.path.name} - koniec gry, dziękuję!")
        sender() ! Kolejny5(next, mojaMaxLiczbaOdbic)
        self ! PoisonPill
      }
  }
}

@main
def zad5: Unit = {
  val system = ActorSystem("PingPongSystem")

  val players = for (i <- 1 to 10) yield {
    system.actorOf(Props[Player5](), s"player$i")
  }

  for (i <- players.indices) {
    val nextPlayer = players((i + 1) % players.size)
    val maxHits = scala.util.Random.between(3, 8)
    players(i) ! Kolejny5(nextPlayer, maxHits)
  }

  players.head ! Pilka5
}
