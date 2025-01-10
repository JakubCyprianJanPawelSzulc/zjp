file:///C:/Users/JSadr/Desktop/studia2/Scala/lab12/src/main/scala/Zad1.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1190
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/lab12/src/main/scala/Zad1.scala
text:
```scala
import org.apache.pekko
import pekko.actor._
import scala.concurrent.duration._
import scala.util.Random

object TickActor {
  val Tick = "tick"
  val Shoot = "shoot"
  val End = "end"
}

class TickActor extends Actor with ActorLogging {
  import TickActor._
  

  def receive: Receive = {
    case Tick =>
      val zamek1 = context.actorOf(Props[Zamek](), "zamek1")
      val zamek2 = context.actorOf(Props[Zamek](), "zamek2")
      log.info("Tick")
      context.become(zZamkami(zamek1, zamek2))
  }

  def zZamkami(zamek1: ActorRef, zamek2: ActorRef): Receive = {
    case Tick =>
      zamek1 ! Shoot
      zamek2 ! Shoot

    case End =>
      log.info("End of battle!")
      context.system.terminate()
  }
}

object Zamek {
  case class Shoot(activeDefenders: Int)
}

class Zamek extends Actor with ActorLogging {
  import Zamek._
  import context.dispatcher

  def receive: Receive = {
    case TickActor.Shoot =>
      val myName = self.path.name
      val enemy = if (myName == "zamek1") context.actorSelection("../zamek2") else context.actorSelection("../zamek1")
      val obroncy = (1 to 100).foldLeft(Set.empty[ActorRef]) { (acc, _) =>
        val obronca = context.actorOf(P@@(new Obronca(przeciwnik)), s"${mojaNazwa}_obronca$i")
        context.watch(obronca)
        acc + obronca
      }
      // val obronca1 = context.actorOf(Props(classOf[Obronca], enemy))
      // val obronca2 = context.actorOf(Props(classOf[Obronca], enemy))
      // context.watch(obronca1)
      // context.watch(obronca2)
      // val obroncy = Set(obronca1, obronca2)
      context.become(active(obroncy))
  }

  def active(obroncy: Set[ActorRef]): Receive = {
    case TickActor.Shoot =>
      if (obroncy.nonEmpty) {
        obroncy.foreach(_ ! Obronca.Shoot)
      } else {
        log.info(s"${self.path.name} has lost the battle!")
        context.parent ! TickActor.End
      }
    
    case Obronca.Strzala =>
      val randomIndex = Random.nextInt(obroncy.size)
      val obronca = obroncy.toVector(randomIndex)
      obronca ! Obronca.Trafiony(obroncy.size)
      

    case Terminated(defender) =>
      val updatedDefenders = obroncy - defender
      log.info(s"${self.path.name}: Defender ${defender.path.name} has died. Remaining defenders: ${updatedDefenders.size}")
      context.become(active(updatedDefenders))
  }
}

object Obronca {
  case object Shoot
  case class Trafiony(activeDefenders: Int)
  val Strzala = "strzala"
}

class Obronca(przeciwnik: ActorSelection) extends Actor with ActorLogging {
  import Obronca._
  import scala.util.Random

  def receive: Receive = {
    case Shoot =>
      przeciwnik ! Strzala

    case Trafiony(activeDefenders) =>
      val szansaNaSmierc = Random.nextInt(activeDefenders)
      if (szansaNaSmierc == 0 && activeDefenders > 1) {
        log.info(s"${self.path.name} has died!")
        context.stop(self)
      }
  }
}

@main
def zad1: Unit = {
  val system = ActorSystem("BattleSystem")
  import system.dispatcher

  val tickActor = system.actorOf(Props[TickActor](), "tickActor")

  val ticker = system.scheduler.scheduleWithFixedDelay(
    Duration.Zero,
    1.second,
    tickActor,
    TickActor.Tick
  )
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