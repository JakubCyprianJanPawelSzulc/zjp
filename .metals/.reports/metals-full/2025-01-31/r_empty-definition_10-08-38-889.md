error id: terminate.
file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz_2/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: terminate.
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -context/system/terminate.
	 -context/system/terminate#
	 -context/system/terminate().
	 -scala/Predef.context.system.terminate.
	 -scala/Predef.context.system.terminate#
	 -scala/Predef.context.system.terminate().

Document text:

```scala
import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case class Wstaw(n: Int)
case class Znajdź(n: Int)

class Węzeł extends Actor with ActorLogging {
  def receive: Receive = {
    case Oskar =>
      log.info("Oskar? No i świetnie! Idę na emeryturę!")
      context.system.terminate()
    case msg => log.info(s"Odebrałem wiadomość: ${msg}")
  }
}

@main
def mainProg: Unit = {
  val system = ActorSystem("Hollywood")
  val leonardo = system.actorOf(Props[MovieStar](), "leonardo")
  leonardo ! Oskar
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: terminate.