error id: scaladsl.
file:///C:/Users/JSadr/Desktop/studia2/Scala/szablon2/szkielet/src/main/scala/zad2.scala
empty definition using pc, found symbol in pc: scaladsl.
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -org/apache/pekko/actor/typed/org/apache/pekko/actor/typed/scaladsl.
	 -org/apache/pekko/actor/typed/scaladsl.
	 -scala/Predef.org.apache.pekko.actor.typed.scaladsl.

Document text:

```scala
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.*
import scala.util.Random

sealed trait Message
case class Utworz2(n: Int) extends Message
case class Generuj(replyTo: ActorRef) extends Message
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
            zGeneratorami(generatory, no)
         }
   }
 }
}

object Generator {
 def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
   msg.match{
      case Generuj =>

   }
 }
}


@main 
def zad2: Unit = {
   val pacjent: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
   pacjent ! Utworz(10)
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: scaladsl.