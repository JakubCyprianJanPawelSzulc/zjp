error id: example_typed/Message#
file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_2_2024_zad2/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: example_typed/Message#
semanticdb not found
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
package example_typed

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.ActorRef

sealed trait Message
case class KupBulki(n: Int)
case class Zamowienie(n: Int)
case class Bulki(n: Int)
case object ZamknijSklep

object Klient {
  def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case KupBulki(n) =>
        val sklep = ctx.spawn(Sklep(), s"sklep-1")
        sklep ! Zamowienie(n)
        wSklepie(sklep, 10, 0)
    }
  }
  def wSklepie(sklep: ActorRef[Message], licznikBulekDoKupienia: Int, licznikSklepow: Int): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case Bulki(n: Int) =>
        if (licznikBulekDoKupienia - n == 0 || licznikBulekDoKupienia - n < 0){
          ctx.log(s"Udało się kupić wszystkie/reszte bulek w sklepie $sklep")
          Behaviors.stopped
        }else{
          if (liczbaSklepow+1==10){
            ctx.log(s"Odwiedzilem juz 10 sklepow, koniec tego")
            Behaviors.stopped
          }else{
            sklep ! ZamknijSklep
            val nowySklep = ctx.spawn(Sklep(), s"sklep-$licznikSklepow")
            wSklepie(nowySklep, licznikBulekDoKupienia -1, licznikSklepow +1)
          }
        }
      case _ =>
        Behaviors.same
    }
  }
}

object Sklep {
  def apply(): Behavior[Message] = Behaviors.receive { (ctx, msg) =>
    msg match{
      case Zamowienie(n) =>
        val mojaLiczbaBulek = scala.util.Random.nextInt(6)
        ctx.parent() ! Bulki(mojaLiczbaBulek)

      case ZamknijSklep =>
        Behaviors.stopped

      case _ =>
        Behaviors.same
    }
  }
}

@main
def demo: Unit = {
  val klient: ActorSystem[Message] = ActorSystem(Klient(), "klient")
  klient ! KupBulki(20)
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: example_typed/Message#