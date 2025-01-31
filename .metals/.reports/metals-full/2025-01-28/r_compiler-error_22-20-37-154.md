file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_202x_zad2/src/main/scala/Main.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1816
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_202x_zad2/src/main/scala/Main.scala
text:
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
case class Generuj(n: Int, m: Int) extends Message
case class Wynik(m: Int) extends Message

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad2(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

def najdluzszyPodciag(lista: List[Int]): Int = {
  lista.foldLeft((0, 0)) { case ((maxLen, currentLen), num) =>
    if (num == lista.head) (maxLen, currentLen + 1)
    else (max(maxLen,@@currentLen), 1)
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
        podciagi.foreach(_ ! Generuj(pierwsza, druga))
        zPodciagami(podciagi)
      case _ =>
        Behaviors.same
    }
  }
  def zPodciagami(podciagi: ActorRef[Message]): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case Wynik =>
        def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
            msg match{
              case Generuj =>
                val mojeDziesiecLiczb = List.fill(10)(losuj_zad2(pierwsza, druga))

            }
        }
    }
  }
}

object Podciag {
 def apply(): Behavior[Message] = Behaviors.receive {
    ???
 }
}


@main 
def zad2: Unit = {
   val nadzorca: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
   nadzorca ! Message.Init
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