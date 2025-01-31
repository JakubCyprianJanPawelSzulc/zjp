error id: _empty_/
file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz_2/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: _empty_/
semanticdb not found
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case object Oskar

class MovieStar extends Actor with ActorLogging {
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

empty definition using pc, found symbol in pc: _empty_/