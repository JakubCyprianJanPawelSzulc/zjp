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
      context.become(zMagazynierami(Set.empty, magazynierzy, List.empty))
  }

  def zMagazynierami(zajeci: Set[ActorRef], wolni: Set[ActorRef], towary: List[(List[Int], Int)]): Receive ={
    case Dostawa =>
      if(wolni.nonEmpty){
        val wybranyMagazynier = wolni.toList(scala.util.Random.nextInt(wolni.size))
        val nowiZajeci = zajeci + wybranyMagazynier
        val nowiWolni = wolni - wybranyMagazynier
        sender() ! Przyjmujacy(wybranyMagazynier)
        context.become(zMagazynierami(nowiZajeci, nowiWolni, towary))
      }else{
        log.info("Brak dostepnych magazynierow")
        sender() ! Brak
      }

    case Towar(lista) =>
      val nowiZajeci = zajeci - sender()
      val nowiWolni = wolni + sender()
      val sumaTowarow = lista.foldLeft(0)(_ + _)
      val zaktualizowaneTowary = (lista, sumaTowarow) :: towary
      log.info(s"Zaktualizowane towary: $zaktualizowaneTowary")
      context.become(zMagazynierami(nowiZajeci, nowiWolni, zaktualizowaneTowary))
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
      self ! PoisonPill
    case Przyjmujacy(magazynier)=>
      val n = losuj_zad1(1, 10)
      val mojaLista = List.fill(n)(Random.nextInt(9))
      magazynier ! Przyjmij(mojaLista)
      self ! PoisonPill
  }
}

class Magazynier extends Actor with ActorLogging {
  def receive: Receive = {
    case Przyjmij(lista) =>
      context.parent ! Towar(lista)
  }
}

@main 
def zad1: Unit = {
  val system = ActorSystem("Zadanie1")
  val firma = system.actorOf(Props[Firma](), "firma")
  val dostawcy = (1 to 20).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
    val dostawca = system.actorOf(Props(new Dostawca()), s"uczestnik-$i")
    acc + dostawca
  }
  firma ! Utworz(10)
  dostawcy.foreach(_ ! Dostarcz(firma))
}
