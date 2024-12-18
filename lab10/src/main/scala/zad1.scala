import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case object Pilka
case class GrajZ(actor: ActorRef)

class Player extends Actor with ActorLogging {
  def receive: Receive = {
    case GrajZ(actor) => {
      log.info("Gram z " + actor)
      actor ! Pilka
    }
    case Pilka => {
      sender() ! Pilka
      log.info(s"${self.path.name} - odbijam pilke")
    }
  }
}

@main
def zad1: Unit = {
  val system = ActorSystem("Hollywood")
  val wieslaw = system.actorOf(Props[Player](), "Wieslaw")
  val lukasz = system.actorOf(Props[Player](), "Lukasz")

  wieslaw ! GrajZ(lukasz)
}
