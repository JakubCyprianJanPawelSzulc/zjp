import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case object Pilka2
case object Rozpocznij
case class Kolejny(actor: ActorRef)

class Player2 extends Actor with ActorLogging {
  def receive: Receive = {
    case Kolejny(actor) => {
      log.info("Gram z " + actor)
      context.become(withPlayer2(actor))
    }
  }
  def withPlayer2(actor: ActorRef): Receive ={
    case Rozpocznij =>
        actor ! Pilka2
        log.info(s"${self.path.name} - rozpoczynam, odbijam pilke")
    case Pilka2 =>
        actor ! Pilka2
        log.info(s"${self.path.name} - odbijam pilke")
  }
}

@main
def zad2: Unit = {
  val system = ActorSystem("PingPong2")
  val wieslaw = system.actorOf(Props[Player2](), "Wieslaw")
  val lukasz = system.actorOf(Props[Player2](), "Lukasz")
  val michal = system.actorOf(Props[Player2](), "Michal")

  lukasz ! Kolejny(wieslaw)
  wieslaw ! Kolejny(michal)
  michal ! Kolejny(lukasz)

  lukasz ! Rozpocznij
}
