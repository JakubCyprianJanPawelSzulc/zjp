error id: actorOf.
file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_2025_zad1_zaoczne/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: actorOf.
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -system/actorOf.
	 -system/actorOf#
	 -system/actorOf().
	 -scala/Predef.system.actorOf.
	 -scala/Predef.system.actorOf#
	 -scala/Predef.system.actorOf().

Document text:

```scala
import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case class Rozpocznij(gracze: Set[AktorRef])
case object Zbij
case object Dostalem

class Sedzia extends Actor with ActorLogging {
  def receive: Receive = {
    case Oskar =>
      
    case msg => log.info(s"Odebrałem wiadomość: ${msg}")
  }
}

class Gracz extends Actor with ActorLogging {
  def receive: Receive ={

  }
}

@main
def mainProg: Unit = {
  val system = ActorSystem("Zbijak")
  val sedzia = system.actorOf(Props[Sedzia](), "sedzia")
  sedzia ! Rozpocznij(gracze)
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: actorOf.