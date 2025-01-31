error id: _empty_/Szef#przyjmowanieOdpowiedzi().(kandydaci)
file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_202x_zad1/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: _empty_/Szef#przyjmowanieOdpowiedzi().(kandydaci)
semanticdb not found
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import org.apache.pekko
import pekko.actor.*
import scala.util.Random
import scala.collection.immutable.Map

case class Przyjmij(kand: Set[ActorRef])
case class Przepytaj(kandydaci: Set[ActorRef])
case class Odpowiedz(odp: Boolean)
case class PoprawnieOdp(kandydaci: Set[ActorRef])
case class Pytanie()

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad1(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

class Szef extends Actor with ActorLogging {
  def receive: Receive = {
    case Przyjmij(kandydaci) =>
      val pracownicy = (1 to (kandydaci.size)*2).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
        val pracownik = context.actorOf(Props(new Pracownik()), s"pracownik-$i")
        acc + pracownik
      }
      val pracownicyList = pracownicy.toList
      val (pierwszaGrupa, drugaGrupa) = pracownicyList.splitAt(pracownicyList.size / 2)
      pracownicy.foreach(_ ! Przepytaj(kandydaci))
      context.become(przyjmowanieOdpowiedzi(kandydaci, pierwszaGrupa.toSet, drugaGrupa.toSet, Map.empty, 0))
  }

  def przyjmowanieOdpowiedzi(
      kandydaci: Set[ActorRef],
      pierwszaGrupa: Set[ActorRef],
      drugaGrupa: Set[ActorRef],
      bazaDanychOdpowiedzi: Map[ActorRef, (Int, Int)],
      licznik: Int
  ): Receive = {
    case PoprawnieOdp(odp) =>
      // Aktualizowanie bazy danych odpowiedzi
      val updatedBazaDanychOdpowiedzi = odp.foldLeft(bazaDanychOdpowiedzi) { (acc, kandydat) =>
        val (g1, g2) = acc.getOrElse(kandydat, (0, 0))
        if (pierwszaGrupa.contains(kandydat)) acc + (kandydat -> (g1 + 1, g2))
        else acc + (kandydat -> (g1, g2 + 1))
      }

      if (licznik + 1 == pierwszaGrupa.size + drugaGrupa.size) {
        // Wyłonienie najlepszych kandydatów
        val najlepszyPierwszaGrupa = updatedBazaDanychOdpowiedzi
          .filter { case (kandydat, _) => pierwszaGrupa.contains(kandydat) }
          .maxBy { case (_, (g1, _)) => g1 }._1

        val najlepszyDrugaGrupa = updatedBazaDanychOdpowiedzi
          .filter { case (kandydat, _) => drugaGrupa.contains(kandydat) }
          .maxBy { case (_, (_, g2)) => g2 }._1

        log.info(s"Wybrałem $najlepszyPierwszaGrupa oraz $najlepszyDrugaGrupa")
        pierwszaGrupa.foreach(_ ! PoisonPill)
        drugaGrupa.foreach(_ ! PoisonPill)
        context.system.terminate()
      } else {
        context.become(przyjmowanieOdpowiedzi(kandydaci, pierwszaGrupa, drugaGrupa, updatedBazaDanychOdpowiedzi, licznik + 1))
      }
  }

    
}

class Kandydat extends Actor  with ActorLogging {
  def receive: Receive = {
    case Pytanie() =>
      val odpowiedz = Random.nextBoolean()
      sender() ! Odpowiedz(odpowiedz)
  }
}

class Pracownik extends Actor  with ActorLogging {
  def receive: Receive = {
    case Przepytaj(kandydaci) =>
      kandydaci.foreach(_ ! Pytanie())
      context.become(przyjmowanieOdpowiedzi(Set.empty[ActorRef], kandydaci.size, 0))
  }
  def przyjmowanieOdpowiedzi(poprawnieOdpowiedzieli: Set[ActorRef], liczbaKandydatow: Int, liczbaOdpowiedzi: Int): Receive ={
    case Odpowiedz(odp) =>
      val updatedPoprawnieOdpowiedzieli = if (odp) poprawnieOdpowiedzieli + sender() else poprawnieOdpowiedzieli
      if (liczbaOdpowiedzi + 1 == liczbaKandydatow) {
        context.parent ! PoprawnieOdp(updatedPoprawnieOdpowiedzieli)
      } else {
        context.become(przyjmowanieOdpowiedzi(updatedPoprawnieOdpowiedzieli, liczbaKandydatow, liczbaOdpowiedzi + 1))
      }
  }
}

@main 
def zad1: Unit = {
  val system = ActorSystem("Zadanie1")
  val szef = system.actorOf(Props[Szef](), "szef")
  val kandydaci = (1 to 30).foldLeft(Set.empty[ActorRef]) { (acc, i) =>
        val kandydat = system.actorOf(Props[Kandydat](), s"kandydat-$i")
        acc + kandydat
  }
  szef ! Przyjmij(kandydaci)
}

//rozwiąż bez zmiennych, kolekcji mutowalnych, bez petli while
//zaimplementuj system rozmowy kwalifikacyjnej. aktor glownego typu - szef powininen przyjac jedynie komunikat przyjmij, 
// w efekcie ktorego przyjmuje zbior aktorow typu kandydat. oraz utworzy aktorow typu pracownik by bylo ich dwa razy wiecej niz 
// otrzymanych kandydatow. potem szef powinein podzielic pracownikow na dwie rowne grupy oraz wyslac do kazdego z nich zbior 
// wszystkikch kandydatow przez komunikat przepytaj. po otrzymaniu kazdy pracownik powinien wyslac do kazdego kandydata komunikat 
// typu pytanie. w efekcie kandydat wysyla do praconwika komunikat typu odpoiwedz gdzie odp jets losowo wygenerowana wartoscia 
// lgogiczna informujaca czy kandydat zna odpiwedz czy nie. kazdy pracownik powinine po otrzymaniu odp od wszystckich kandydatow wysalc 
// do szefa zbior kandydatow ktorzy odpowiedzieli poprawnie: Poprawnie Odp. po otrzymaniu od wszystkich Pracownikow, 
// szef powinien zakonczyc ich prace(kominikat poisonPill) oraz wybrac dwoch kandydatow: jednego z tych ktorzy poprawnie odpowiedzial 
// na najweicej pytan pracownikow nalezacych do pierwszej grupy , drugiego z tych co odpowiedzial poprawnie  z tych z drugiej grupy.
// nastepnie powinien zakonczyc system aktorow(context.system.terminate())


```

#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Szef#przyjmowanieOdpowiedzi().(kandydaci)