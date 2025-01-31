error id: scala/collection/IterableOnceOps#foreach().
file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_2025_zad1_zaoczne/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: scala/collection/IterableOnceOps#foreach().
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -druzyna2/foreach.
	 -druzyna2/foreach#
	 -druzyna2/foreach().
	 -scala/Predef.druzyna2.foreach.
	 -scala/Predef.druzyna2.foreach#
	 -scala/Predef.druzyna2.foreach().

Document text:

```scala
import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case class Rozpocznij(zawodnicy: Set[ActorRef])
case object Zbij
case object Dostalem
case class ZmienDruzyne(przeciwnicy: Set[ActorRef])
case class AktualizacjaDruzyn(przeciwnicy: Set[ActorRef])

class Sedzia extends Actor with ActorLogging {
  def receive: Receive = {
    case Rozpocznij(zawodnicy) =>
      val zawodnicyList = zawodnicy.toList
      val (druzyna1, druzyna2) = zawodnicyList.splitAt(zawodnicyList.size / 2)
      val druzyna1Set = druzyna1.toSet
      val druzyna2Set = druzyna2.toSet

      druzyna1Set.foreach(_ ! AktualizacjaDruzyn(druzyna2Set))
      druzyna2Set.foreach(_ ! AktualizacjaDruzyn(druzyna1Set))

      val losowoWybranaDruzyna = if (scala.util.Random.nextBoolean()) druzyna1Set else druzyna2Set
      val losoWybranyZawodnik = losowoWybranaDruzyna.toList(scala.util.Random.nextInt(losowoWybranaDruzyna.size))
      losoWybranyZawodnik ! Zbij
      context.become(zDruzynami(druzyna1Set, druzyna2Set))
  }

  def zDruzynami(druzyna1: Set[ActorRef], druzyna2: Set[ActorRef]): Receive ={
    case Dostalem =>
      if (druzyna1.contains(sender())){
        val nowaDruzyna2 = druzyna2 + sender()
        val nowaDruzyna1 = druzyna1 - sender()
        if (nowaDruzyna1.size == 0) {
          log.info("Druzyna 1 przegrala")
          context.stop(self)
        } else if (nowaDruzyna2.size == 0) {
          log.info("Druzyna 2 przegrala")
          context.stop(self)
        }
        druzyna1.foreach(_ ! AktualizacjaDruzyn(nowaDruzyna2))
        druzyna2.foreach(_ ! AktualizacjaDruzyn(nowaDruzyna1))
        sender() ! ZmienDruzyne(nowaDruzyna2)
        context.become(zDruzynami(nowaDruzyna1, nowaDruzyna2))
      }else{
        val nowaDruzyna1 = druzyna1 + sender()
        val nowaDruzyna2 = druzyna2 - sender()
        if (nowaDruzyna1.size == 0) {
          log.info("Druzyna 1 przegrala")
          context.stop(self)
        } else if (nowaDruzyna2.size == 0) {
          log.info("Druzyna 2 przegrala")
          context.stop(self)
        }
        sender() ! ZmienDruzyne(nowaDruzyna1)
        context.become(zDruzynami(nowaDruzyna1, nowaDruzyna2))
      }
  }
}

class Zawodnik(sedzia: ActorRef) extends Actor with ActorLogging {
  def receive: Receive ={
    case AktualizacjaDruzyn(przeciwnicy) =>
      context.become(zPrzeciwnikami(przeciwnicy))
  }
  def zPrzeciwnikami(przeciwnicy: Set[ActorRef]): Receive = {
    case Zbij =>
      val mojCel = przeciwnicy.toList(scala.util.Random.nextInt(przeciwnicy.size))
      if(sender() == sedzia) {
        log.info("pierwszy dostalem pilke")
        mojCel ! Zbij
      }else{
        val szansaNaTrafienie = scala.util.Random.nextBoolean()
        if (szansaNaTrafienie){
          log.info("Dostalem!")
          sedzia ! Dostalem
        }else{
          log.info("Unik, odrzucam pilke")
          mojCel ! Zbij
        }
      }
    case ZmienDruzyne(przeciwnicy) =>
      val mojCel = przeciwnicy.toList(scala.util.Random.nextInt(przeciwnicy.size))
      mojCel ! Zbij
      context.become(zPrzeciwnikami(przeciwnicy))
    case AktualizacjaDruzyn(przeciwnicy) =>
      context.become(zPrzeciwnikami(przeciwnicy))
  }
}

@main
def mainProg: Unit = {
  val system = ActorSystem("Zbijak")
  val sedzia = system.actorOf(Props[Sedzia](), "sedzia")
  val zawodnicy = (1 to 20).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
    val zawodnik = system.actorOf(Props(new Zawodnik(sedzia)), s"zawodnik-$i")
    acc + zawodnik
  }
  sedzia ! Rozpocznij(zawodnicy)
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: scala/collection/IterableOnceOps#foreach().