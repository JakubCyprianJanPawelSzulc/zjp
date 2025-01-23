file:///C:/Users/JSadr/Desktop/studia2/Scala/lab11jeszczeraz/lab11/src/main/scala/Zad1.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 432
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/lab11jeszczeraz/lab11/src/main/scala/Zad1.scala
text:
```scala
import org.apache.pekko
import pekko.actor._

case class Init(liczbaPracownikow: Int)

class Boss extends Actor {
  def receive: Receive = {
    case "Start" =>
      println("Rozpoczynam przetwarzanie danych.")
      val nadzorca = context.actorOf(Props[Nadzorca](), "nadzorca")
      nadzorca ! Init(4)
  }
}

class Nadzorca extends Actor {
  def receive: Receive = {
    case Init(liczbaPracownikow) =>
      val workers = for (1@@)
  }
}

class Pracownik extends Actor {
  def receive: Receive = {
    case msg => println(s"Odebrałem wiadomość: $msg")
  }
}

@main 
def zad1: Unit = {

  def dane(): List[String] = {
   scala.io.Source.fromResource("ogniem_i_mieczem.txt").getLines.toList
  }
  val system = ActorSystem("WordCounter")
  val boss = system.actorOf(Props[Boss](), "boss")
  boss ! "Dzien dobry"

  println(dane())
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