error id: _empty_/Nadzorca#zUczestnikami().(uczestnicy)
file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_2024_zad1/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: _empty_/Nadzorca#zUczestnikami().(uczestnicy)
semanticdb not found
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import org.apache.pekko
import pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}
import scala.util.Random

case class Init(n: Int)
case object Zacznij
case class Odpowiedzi(l: List[Int])

def losuj_zad1(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

class Nadzorca extends Actor with ActorLogging {
  def receive: Receive = {
    case Init(n) =>
      val uczestnicy = (1 to n).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
        val uczestnik = context.actorOf(Props(new Uczestnik()), s"uczestnik-$i")
        acc + uczestnik
      }
      context.become(zUczestnikami(uczestnicy, List.empty))
    case msg => log.info(s"Odebrałem wiadomość: ${msg}")
  }
  def zUczestnikami(uczestnicy: Set[ActorRef], odpowiedzi: List[(ActorRef, List[Int])]): Receive = {
    uczestnicy.foreach(_ ! Zacznij)
    {
      case Odpowiedzi(l) =>
        val noweOdpowiedzi = (sender(), l) :: odpowiedzi
        log.info(s"$noweOdpowiedzi")
        if (noweOdpowiedzi.size == uczestnicy.size){
          val ranking = noweOdpowiedzi.sortBy(_._2.sum).reverse
          val najlepszyWynik = ranking.head._2.sum
          val zwyciezcy = ranking.takeWhile(_._2.sum == najlepszyWynik).map(_._1).toSet
          if (zwyciezcy.size > 1){
            log.info(s"Remis, zaczynam druga runda")
            context.become(zUczestnikami(zwyciezcy, List.empty))
          }else{
            log.info(s"Zwyciezca - $zwyciezcy Wynik - $najlepszyWynik")
            context.system.terminate()
          }
        }else{
          context.become(zUczestnikami(uczestnicy, noweOdpowiedzi))
        }
      case msg => log.info(s"Odebrałem wiadomość: ${msg}")
    }
  }
}

class Uczestnik extends Actor with ActorLogging {
  def receive: Receive = {
    case Zacznij =>
      val mojaLista = List.fill(10)(losuj_zad1(0, 5))
      sender() ! Odpowiedzi(mojaLista)
  }
}

@main
def mainProg: Unit = {
  val system = ActorSystem("Konkurs")
  val szef = system.actorOf(Props[Nadzorca](), "szef")
  szef ! Init(5)
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Nadzorca#zUczestnikami().(uczestnicy)