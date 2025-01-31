file:///C:/Users/JSadr/Desktop/studia2/Scala/szablon2/szkielet/src/main/scala/zad2.scala
### java.lang.IllegalArgumentException: Comparison method violates its general contract!

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1531
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/szablon2/szkielet/src/main/scala/zad2.scala
text:
```scala
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.*
import scala.util.Random

sealed trait Message
case class Utworz2(n: Int) extends Message
case object Generuj extends Message
case class Wynik(n: Int, sender: ActorRef[Message]) extends Message

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad2(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

def liczbaPodciagow(lista: List[Int]): Int = {
   //w liscie typu 100000111111 znajdz ile jest podciagow z jednakowymi znakami
  lista.foldLeft((0, 0, lista.headOption.getOrElse(0))) { case ((maxLen, currentLen, prevNum), num) =>
    if (num == prevNum) (maxLen, currentLen + 1, num)
    else (math.max(maxLen, currentLen), 1, num)
  }._1
}


object Nadzorca {
 def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case Utworz2(n) =>
         val generatory = (1 to n).map { i =>
          ctx.spawn(Generator(), s"generator-$i")
        }
        generatory.foreach(_ ! Generuj)
        zGeneratorami(generatory, List.empty)
      case _ =>
         Behaviors.unhandled
    }
 }

 def zGeneratorami(generatory: Set[ActorRef], wyniki: List[(Int, ActorRef)]): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
   msg match{
      case Wynik(x, sender) =>
         val noweWyniki = (x, sender) :: wyniki
         if(noweWyniki.length == generatory.size){

         }else{
            zGeneratorami(generat@@)
         }
   }
 }
}

object Generator {
 def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
   msg.match{

   }
 }
}


@main 
def zad2: Unit = {
   val pacjent: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
   pacjent ! Utworz(10)
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