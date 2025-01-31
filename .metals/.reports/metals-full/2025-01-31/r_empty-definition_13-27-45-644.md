error id: _empty_/Przyjmujacy.
file:///C:/Users/JSadr/Desktop/studia2/Scala/szablon2/szkielet/src/main/scala/zad1.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -pekko/actor/Przyjmujacy.
	 -pekko/actor/Przyjmujacy#
	 -pekko/actor/Przyjmujacy().
	 -Przyjmujacy.
	 -Przyjmujacy#
	 -Przyjmujacy().
	 -scala/Predef.Przyjmujacy.
	 -scala/Predef.Przyjmujacy#
	 -scala/Predef.Przyjmujacy().

Document text:

```scala
import org.apache.pekko
import pekko.actor.*
import scala.util.Random

case class Utworz(lMagazynierow: Int)
case object Dostawa
case class Dostarcz(firma: ActorRef)
case class Przyjmujacy(magazynier: ActorRef)
case object Brak
case class Przyjmij(list: List[Int])
case class Towar(list: List[Int])

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad1(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

class Firma extends Actor with ActorLogging {
  def receive: Receive = {
    case Utworz(lMagazynierow) =>
      val magazynierzy = (1 to lMagazynierow).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
        val magazynier = context.actorOf(Props(new Magazynier()), s"magazynier-$i")
        acc + magazynier
      }
      context.become(zMagazynierami(Set.empty, magazynierzy))
  }

  def zMagazynierami(zajeci: Set[ActorRef], wolni: Set[ActorRef]): Receive ={
    case Dostawa =>
      if(wolni.nonEmpty){
        val wybranyMagazynier = wolni.toList(scala.util.Random.nextInt(wolni.size))
        sender() ! Przyjmujacy(wybranyMagazynier)
      }else{
        sender() ! Brak
      }
  }
}

class Dostawca extends Actor with ActorLogging {
  def receive: Receive = {
    case Dostarcz(firma) =>
      firma ! Dostawa
      context.become(oczekujeNaOdpowiedz)
  }
  def oczekujeNaOdpowiedz: Receive = {
    case Brak =>
      context.stop.self()
    case Przyjmujacy(magazynier)=>
      
  }
}

class Magazynier extends Actor with ActorLogging {
  def receive: Receive = {
    ???
  }
}

@main 
def zad1: Unit = {
  val system = ActorSystem("Zadanie1")
  val firma = system.actorOf(Props[Firma](), "firma")
  val dostawcy = (1 to 10).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
    val dostawca = context.actorOf(Props(new Dostawca()), s"uczestnik-$i")
    acc + dostawca
  }
  firma ! Utworz(10)
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: 