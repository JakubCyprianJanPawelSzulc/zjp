file:///C:/Users/JSadr/Desktop/studia2/Scala/lab12/src/main/scala/Zad1.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 778
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/lab12/src/main/scala/Zad1.scala
text:
```scala
import org.apache.pekko
import pekko.actor._
import scala.concurrent.duration._

// Przykład wykorzystania Planisty (Scheduler)

object TickActor {
  val Tick = "tick"
  val Shoot = "shoot"
  val End = "end"
}


class TickActor extends Actor with ActorLogging {
  import TickActor._
  def receive = {
    case Tick => {
      log.info("Tick")
      val zamek1 = context.actorOf(Props[Zamek](), "zamek1")
      val zamek2 = context.actorOf(Props[Zamek](), "zamek2")
      context.become(zZamkami)
    }

  }
  def zZamkami = {
    case Tick => {
      log.info("Tick")
      zamek1 ! Shoot
      zamek2 ! Shoot
    }
    case End => {
      log.info("End")
      context.system.terminate()
    }
  }
}
  
class Zamek extends Actor with ActorLogging {
  def receive = bezObroncow(@@)

  def bezObroncow(obroncy: Set[ActorRef], mojaNazwa: String): Receive = {
    case TickActor.Shoot => {
      val przeciwnik = if (self.path.name == "zamek1") context.actorSelection("../zamek2") else context.actorSelection("../zamek1")
      val mojaNazwa = if (przeciwnik == context.actorSelection("../zamek1")) "zamek2" else "zamek1"
      log.info("Strzelać")
      val updatedObroncy = (1 to 100).foldLeft(obroncy) { (acc, i) =>
        acc + context.actorOf(Props(new Obrońca(przeciwnik)), s"${mojaNazwa}_obronca$i")
      }
      context.become(zObroncami(updatedObroncy))
    }
  }

  def zObroncami(obroncy: Set[ActorRef]): Receive ={

  }
}

@main 
def zad1: Unit = {

  val system = ActorSystem("system")
  import system.dispatcher

  val tickActor = system.actorOf(Props[TickActor](), "defender")

  val ticker = system.scheduler.scheduleWithFixedDelay(
    Duration.Zero,
    50.milliseconds,
    tickActor,
    TickActor.Tick
  )

 // system.terminate()

}

```



#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:129)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:244)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:101)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:47)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:422)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1