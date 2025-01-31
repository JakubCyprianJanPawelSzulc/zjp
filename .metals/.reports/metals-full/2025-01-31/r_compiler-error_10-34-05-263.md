file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz_2/src/main/scala/Main.scala
### java.lang.IllegalArgumentException: Comparison method violates its general contract!

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1710
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz_2/src/main/scala/Main.scala
text:
```scala
import org.apache.pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case class Wstaw(n: Int)
case class Znajdz(n: Int)

class Wezel extends Actor with ActorLogging {
  def lisc(wartosc: Int): Receive =  {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        val prawe = context.actorOf(Props(new Wezel), s"lisc-$liczba")
        context.become(zPrawymPoddrzewem(wartosc, prawe))
      } else if (liczba < wartosc){
        val lewe = context.actorOf(Props(new Wezel), s"lisc-$liczba")
        context.become(zLewymPoddrzewem(lewe, wartosc))
      } else{
        context.become(lisc(wartosc))
      }
    case Znajdz(n) =>
      log.info(s"Nie znaleziono $n")
  }
  def zLewymPoddrzewem(lewe: ActorRef, wartość: Int): Receive = {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        val prawe = context.actorOf(Props(new Wezel), s"lisc-$liczba")
        zPoddrzewami(lewe, wartosc, prawe)
      } else if (liczba < wartosc){
        lewe ! Wstaw(liczba)
        context.become(zLewymPoddrzewem(lewe, wartosc))
      }else{
        context.become(zLewymPoddrzewem(lewe, wartosc))
      }
    case Znajdz(liczba) =>
      if (liczba == wartosc) {
        log.info(s"Ja jestem szukaną wartoscia - $liczba")
      }else if(liczba > wartosc){
        log.info(s"Nie znaleziono warosci - $liczba")
      }else if(liczba < wartosc){
        lewe ! Znajdz(liczba)
      }
      context.become(zLewymPoddrzewem(lewe,wartosc))
  }
  def zPrawymPoddrzewem(wartość: Int, prawe: ActorRef): Receive = {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        prawe ! Wstaw(liczba)
        context.become(zPrawymPoddrzewem(wartosc, pra@@))
      } else if (liczba < wartosc){
        val lewe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
        zPoddrzewami(lewe, wartosc, prawe)
      } else if (liczba == wartosc){
        val lewe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
        zPoddrzewami(lewe, wartosc, prawe)
      }else{
        Behaviors.same
      }

    case Znajdz(liczba) =>
      if (liczba == wartosc) {
        ctx.log.info(s"Ja jestem szukaną wartoscia - $liczba")
      }else if(liczba > wartosc){
        prawe ! Znajdz(liczba)
      }else if(liczba < wartosc){
        ctx.log.info(s"Nie znaleziono warosci - $liczba")
      }
      Behaviors.same
  }
  def zPoddrzewami(lewe: ActorRef, wartość: Int, prawe: ActorRef): Receive = {
    
  }
}

@main
def mainProg: Unit = {
  val system = ActorSystem("drzewo")
  val root = system.actorOf(Props[Wezel](), "root")
  root ! Wstaw(3)
  root ! Wstaw(7)
  root ! Wstaw(5)
  root ! Wstaw(2)
  root ! Wstaw(9)
  root ! Wstaw(3)
  root ! Znajdź(3)
  root ! Znajdź(7)
  root ! Znajdź(9)
}
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