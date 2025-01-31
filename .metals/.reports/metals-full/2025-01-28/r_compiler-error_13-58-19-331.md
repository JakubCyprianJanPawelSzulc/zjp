file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_202x_zad1/src/main/scala/Main.scala
### java.lang.IllegalArgumentException: Comparison method violates its general contract!

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1945
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_202x_zad1/src/main/scala/Main.scala
text:
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
        val kandydat = context.actorOf(Props(new Kandydat()), s"kandydat-$i")
        acc + kandydat
      }
      val pracownicyList = pracownicy.toList
      val (pierwszaGrupa, drugaGrupa) = pracownicyList.splitAt(pracownicyList.size / 2)
      pierwszaGrupa.foreach(_ ! Przepytaj(kandydaci))
      drugaGrupa.foreach(_ ! Przepytaj(kandydaci))
      context.become(przyjmowanieOdpowiedzi(kandydaci, pierwszaGrupa.toSet, drugaGrupa.toSet, Map.empty))
  }

  def przyjmowanieOdpowiedzi(kandydaci: Set[ActorRef], pierwszaGrupa: Set[ActorRef], drugaGrupa: Set[ActorRef], bazaDanychOdpowiedzi: Map[(Int, ActorRef)], licznik: Int): Receive = {
    case Odpowiedz(odp) =>
      val updatedBazaDanychOdpowiedzi = bazaDanychOdpowiedzi + ((if (pierwszaGrupa.contains(sender())) 1 else 2, sender()) -> odp)      
      if (licznik+1 == pierwszaGrupa.size+drugaGrupa.size) {
        //najlepsi w sensie że najczęściej występują w 
        val najlepszyPierwszaGrupa =
        val najlepszyDrugaGrupa =
        pierwszaGrupa.foreach(_ ! PoisonPill)
        drugaGrupa.foreach(_ ! PoisonPill)
        context.system.terminate()
      } else {
        context.become(przyjmowanieOdpowiedzi(kandydaci, pierwszaGrupa, drugaGrupa, updatedBazaDanychOdpowiedzi, l@@))
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
      kandydaci.foreach(Pytanie())
      context.become(przyjmowanieOdpowiedzi(Set.empty[ActorRef]), kandydaci.size, 0)
  }
  def przyjmowanieOdpowiedzi(poprawnieOdpowiedzieli: Set[ActorRef],liczbaKandydatow: Int, liczbaOdpowiedzi: Int): Receive ={
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
        val kandydat = context.actorOf(Props(new Kandydat()), s"kandydat-$i")
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



#### Error stacktrace:

```
java.base/java.util.TimSort.mergeLo(TimSort.java:781)
	java.base/java.util.TimSort.mergeAt(TimSort.java:518)
	java.base/java.util.TimSort.mergeForceCollapse(TimSort.java:461)
	java.base/java.util.TimSort.sort(TimSort.java:254)
	java.base/java.util.Arrays.sort(Arrays.java:1233)
	scala.collection.SeqOps.sorted(Seq.scala:728)
	scala.collection.SeqOps.sorted$(Seq.scala:719)
	scala.collection.immutable.List.scala$collection$immutable$StrictOptimizedSeqOps$$super$sorted(List.scala:79)
	scala.collection.immutable.StrictOptimizedSeqOps.sorted(StrictOptimizedSeqOps.scala:75)
	scala.collection.immutable.StrictOptimizedSeqOps.sorted$(StrictOptimizedSeqOps.scala:75)
	scala.collection.immutable.List.sorted(List.scala:79)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:143)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:90)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

java.lang.IllegalArgumentException: Comparison method violates its general contract!