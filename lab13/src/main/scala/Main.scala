package example_typed

import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem
import scala.util.Random

object Konkurs {

  object Zawodnik {
    sealed trait Command
    case class PrzeprowadzProbe(replyTo: ActorRef[Wynik]) extends Command

    def apply(id: Int): Behavior[Command] = Behaviors.receive { (ctx, msg) =>
      msg match {
        case PrzeprowadzProbe(replyTo) =>
          val wynik = Wynik(id, Random.nextInt(100), Random.nextInt(100), Random.nextInt(100))
          ctx.log.info(s"Zawodnik $id przeprowadza próbę: $wynik")
          replyTo ! wynik
          Behaviors.same
      }
    }
  }

  object Runda {
    sealed trait Command
    case class StartRunda(zawodnicy: scala.collection.immutable.Seq[ActorRef[Zawodnik.Command]], replyTo: ActorRef[Organizator.Command]) extends Command
    case class WynikZawodnika(wynik: Wynik) extends Command

    def apply(): Behavior[Command] = Behaviors.setup { ctx =>
      def aktywna(zawodnicy: scala.collection.immutable.Seq[ActorRef[Zawodnik.Command]], replyTo: ActorRef[Organizator.Command], wyniki: scala.collection.immutable.Seq[Wynik]): Behavior[Command] = {
        Behaviors.receiveMessage {
          case WynikZawodnika(wynik) =>
            val noweWyniki = wyniki :+ wynik
            ctx.log.info(s"Otrzymano wynik: $wynik , liczba wyników: ${noweWyniki.size}")
            if (noweWyniki.size == zawodnicy.size) {
              ctx.log.info("Runda zakończona, przekazywanie wyników do organizatora")
              replyTo ! Organizator.WynikiRundy(noweWyniki)
              Behaviors.stopped
            } else {
              aktywna(zawodnicy, replyTo, noweWyniki)
            }
          case StartRunda(_, _) =>
            Behaviors.same
          // case _ =>
          //   Behaviors.unhandled
        }
      }

      Behaviors.receiveMessage {
        case StartRunda(zawodnicy, replyTo) =>
          zawodnicy.foreach(_ ! Zawodnik.PrzeprowadzProbe(ctx.messageAdapter(Runda.WynikZawodnika.apply)))
          aktywna(zawodnicy, replyTo, scala.collection.immutable.Seq.empty)
        case _ =>
          Behaviors.unhandled
      }
    }
  }

  object Organizator {
    sealed trait Command
    case class WynikiRundy(wyniki: scala.collection.immutable.Seq[Wynik]) extends Command
    case object Start extends Command

    def apply(): Behavior[Command] = Behaviors.setup { ctx =>
      def eliminacje(zawodnicy: scala.collection.immutable.Seq[ActorRef[Zawodnik.Command]]): Behavior[Command] = {
        val runda = ctx.spawn(Runda(), "rundaEliminacyjna")
        runda ! Runda.StartRunda(zawodnicy, ctx.self)
        Behaviors.receiveMessage {
          case WynikiRundy(wyniki) =>
            ctx.log.info("Otrzymano wyniki rundy eliminacyjnej")
            val najlepsi = wyniki.sortBy(w => -(w.wynik1 + w.wynik2 + w.wynik3)).take(20).map(_.id)
            val finalisci = zawodnicy.filter(z => najlepsi.contains(z.path.name.split("-").last.toInt))
            val rundaFinalowa = ctx.spawn(Runda(), "rundaFinalowa")
            rundaFinalowa ! Runda.StartRunda(finalisci, ctx.self)
            finalowa(wyniki)
          case _ =>
            Behaviors.unhandled
        }
      }

      def finalowa(wynikiEliminacji: scala.collection.immutable.Seq[Wynik]): Behavior[Command] = {
        Behaviors.receiveMessage {
          case WynikiRundy(wynikiFinałowe) =>
            ctx.log.info("Otrzymano wyniki rundy finałowej")
            val klasyfikacja = (wynikiEliminacji ++ wynikiFinałowe).sortBy(w => (-(w.wynik1 + w.wynik2 + w.wynik3), -w.wynik1, -w.wynik2, -w.wynik3))
            ctx.log.info("Klasyfikacja końcowa:")
            klasyfikacja.zipWithIndex.foreach { case (wynik, index) =>
              val miejsce = if (index > 0 && klasyfikacja(index - 1).wynik1 + klasyfikacja(index - 1).wynik2 + klasyfikacja(index - 1).wynik3 == wynik.wynik1 + wynik.wynik2 + wynik.wynik3) index else index + 1
              ctx.log.info(s"$miejsce. zawodnik-${wynik.id} – ${wynik.wynik1}-${wynik.wynik2}-${wynik.wynik3} = ${wynik.wynik1 + wynik.wynik2 + wynik.wynik3}")
            }
            Behaviors.stopped
          case _ =>
            Behaviors.unhandled
        }
      }

      Behaviors.receiveMessage {
        case Start =>
          ctx.log.info("Rozpoczynam konkurs")
          val zawodnicy = (1 to 50).map { i =>
            ctx.spawn(Zawodnik(i), s"zawodnik-$i")
          }
          eliminacje(zawodnicy)
        case _ =>
          Behaviors.unhandled
      }
    }
  }

  case class Wynik(id: Int, wynik1: Int, wynik2: Int, wynik3: Int)

  def main(args: Array[String]): Unit = {
    val system = ActorSystem(Organizator(), "KonkursSystem")
    system ! Organizator.Start
  }
}