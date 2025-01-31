import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.*
import scala.util.Random

sealed trait Message
case class Utworz2(n: Int) extends Message
case class Generuj(replyTo: ActorRef[Message]) extends Message
case class Wynik(n: Int, sender: ActorRef[Message]) extends Message

//losowanie liczb calkowitych z przedzialu od n do m
def losuj_zad2(n: Int, m: Int): Int = {
  val random = new Random()
  n + random.nextInt(m - n + 1)
}

def liczbaPodciagow(lista: List[Int]): Int = {
   lista.foldLeft((0, -1, 0)) { case ((count, prevInt, subCount), currentInt) =>
      if (currentInt == prevInt) {
         (count, prevInt, subCount + 1)
      } else {
         (count + (if (subCount > 0) 1 else 0), currentInt, 1)
      }
   }._1
}


object Nadzorca {
 def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
   msg match{
      case Utworz2(n) =>
         val generatory = (1 to n).map { i =>
          ctx.spawn(Generator(), s"generator-$i")
         }.toSet
         val ja = ctx.self
         generatory.foreach(_ ! Generuj(ja))
         zGeneratorami(generatory, List.empty)
      case _ =>
         Behaviors.unhandled
   }
 }

 def zGeneratorami(generatory: Set[ActorRef[Message]], wyniki: List[(Int, ActorRef[Message])]): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
   msg match{
      case Wynik(x, sender) =>
         val noweWyniki = (x, sender) :: wyniki
         if(noweWyniki.length == generatory.size){
            val najwiekszyWynik = wyniki.foldLeft(Int.MinValue) { (max, wynik) =>
              if (wynik._1 > max) wynik._1 else max
            }
            val zwyciezcy = wyniki.filter(_._1 == najwiekszyWynik)
            ctx.log.info(s"Zwyciezcy: $zwyciezcy")
            ctx.system.terminate()
            Behaviors.stopped
         }else{
            zGeneratorami(generatory, noweWyniki)
         }
      case _ =>
         Behaviors.unhandled
   }
 }
}

object Generator {
 def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
   msg.match{
      case Generuj(replyTo) =>
         val len = losuj_zad2(10, 100)
         val podciag = List.fill(len)(Random.nextInt(2))
         val mojaLiczbaPodciagow = liczbaPodciagow(podciag)
         val ja = ctx.self
         // ctx.log.info(s"Moj wynik: $mojaLiczbaPodciagow, moj podciag: $podciag")
         ctx.log.info(s"Moj wynik: $mojaLiczbaPodciagow")
         replyTo ! Wynik(mojaLiczbaPodciagow, ja)
         Behaviors.same

      case _ =>
         Behaviors.unhandled
   }
 }
}


@main 
def zad2: Unit = {
   val pacjent: ActorSystem[Message] = ActorSystem(Nadzorca(), "Zadanie2")
   pacjent ! Utworz2(10)
}
