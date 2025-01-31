error id: _empty_/Podciag.apply().
file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_202x_zad2/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: _empty_/Podciag.apply().
semanticdb not found
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.*
import scala.util.Random
//zaimplementuj system wyznaczania maksymalnej dlugosci podciagu. na najwyzszym poziiomie infrastruktury poiwnien byc aktor glowny, Nadzorca
// aktor pryzjmuej komunikat poczatkowo case obejct Init,w wyniku ktorego pwinien zmienic stan, wylosowac liczbe dodatnia naturalna K, i utworzyc k aktorow o zachowaniu definiowanym przez obiekt Podciag
// nastepnei "losuje" dwie liczby naturalne n (o  mozliwej wartosci od 1 do 4) oraz m (o mozliwej wartosci od 5 do 10) i wyslac je do otworzonych aktorow podciag stosujac do tego komunikat Generuj
// po otrzymaniu kazdy aktor Podciag powienin utworzyc liste zawierajaca 10 liczb naturalnych od n do m. nastepnie pownein odeslac Nadzorcy info o max ldugosci podacigow w tym ciaigu. sluzy do tego komunkat wynik.
// przykladowo do ciagu "2233312223311" powinien zostac odeslany komuniakt wynik (3) czyli n==3(podcaigi o max dlugosci wynoszacej 3 to 333 oraz 222)
// po odebraniu wynikow od wszystkich aktorow typu Podciag aktor glowny powinien wyswietlic max wartosc otrzymana o komunikatach wynik oraz aktorow ktorzy ja podali(wez pod uwage ze mzoe byc kilku takich aktorow) a nastepnie zakoncycz dzialanie systemu (coxtext system terminate)

sealed trait Message
case object Init extends Message
case class Generuj(n: Int, m: Int, replyTo: ActorRef[Message]) extends Message
case class Wynik(m: Int, sender: ActorRef[Message]) extends Message

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad2(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

def najdluzszyPodciag(lista: List[Int]): Int = {
  lista.foldLeft((0, 0)) { case ((maxLen, currentLen), num) =>
    if (num == lista.head) (maxLen, currentLen + 1)
    else (math.max(maxLen,currentLen), 1)
  }._1
}

object Nadzorca {
  def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case Init =>
        val k = losuj_zad2(1, 10)
        val podciagi = (1 to k).map { i =>
          ctx.spawn(Podciag(), s"podciag-$i")
        }
        val pierwsza = losuj_zad2(1,4)
        val druga = losuj_zad2(5,10)
        podciagi.foreach(_ ! Generuj(pierwsza, druga, ctx.self))
        zPodciagami(podciagi, -1, List.empty, 0)
      case _ =>
        Behaviors.same
    }
  }
  def zPodciagami(podciagi: Seq[ActorRef[Message]], najdluzszyOtrzymanyPodciag: Int, listaZwyciezcow: List[ActorRef[Message]],licznikWynikow: Int): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case Wynik(x, sender) =>
        val nowyPodciag = math.max(x, najdluzszyOtrzymanyPodciag)
        val nowaListaZwyciezcow = if (nowyPodciag > najdluzszyOtrzymanyPodciag) List(sender) else if(nowyPodciag == najdluzszyOtrzymanyPodciag) listaZwyciezcow :+ sender else listaZwyciezcow
        if (licznikWynikow + 1 == podciagi.size) {
          ctx.log.info(s"Najwyzszy wynik: $nowyPodciag, Zwyciezcy: $nowaListaZwyciezcow")
          ctx.system.terminate()
          Behaviors.stopped
        } else {
          zPodciagami(podciagi, nowyPodciag, nowaListaZwyciezcow, licznikWynikow + 1)
        }
      case _ =>
        Behaviors.unhandled
    }
  }
}

object Podciag {
 def apply(): Behavior[Message] = Behaviors.receive {
    def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
      msg match{
        case Generuj(pierwsza, druga, replyTo) =>
          val mojeDziesiecLiczb = List.fill(10)(losuj_zad2(pierwsza, druga))
          val maxPodciag = najdluzszyPodciag(mojeDziesiecLiczb)
          replyTo ! Wynik(maxPodciag, ctx.self)
          Behaviors.same
        case _ =>
          Behaviors.same
      }
    }
 }
}


@main 
def zad2: Unit = {
   val nadzorca: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
   nadzorca ! Message.Init
}
```

#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Podciag.apply().