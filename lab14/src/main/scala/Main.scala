package example_typed

import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem
import scala.util.Random

object Drzewo {

  object Wezel {
    sealed trait Cmd
    case class Wstaw(n: Int) extends Cmd
    case class Znajdz(n: Int) extends Cmd

    def apply(wartosc: Int): Behavior[Cmd] = lisc(wartosc)

    def lisc(wartosc: Int): Behavior[Cmd] = Behaviors.receive { (ctx, msg) =>
      msg match {
        case Wstaw(n) =>
          if (n < wartosc) {
            val lewe = ctx.spawn(lisc(n), s"lisc-$n")
            zLewymPoddrzewem(lewe, wartosc)
          } else if (n > wartosc) {
            val prawe = ctx.spawn(lisc(n), s"lisc-$n")
            zPrawymPoddrzewem(wartosc, prawe)
          } else {
            Behaviors.same
          }
        case Znajdz(n) =>
          if (n == wartosc) {
            ctx.log.info(s"Znaleziono wartość $n")
          } else if (n < wartosc) {
            ctx.log.info(s"Nie mam lewego poddrzewa!")
          } else {
            ctx.log.info(s"Nie mam prawego poddrzewa!")
          }
          Behaviors.same
      }
    }

    def zLewymPoddrzewem(lewe: ActorRef[Cmd], wartosc: Int): Behavior[Cmd] = {
      Behaviors.receive { (ctx, msg) =>
        msg match {
          case Wstaw(n) =>
            if (n < wartosc) {
              lewe ! Wstaw(n)
              Behaviors.same
            } else if (n > wartosc) {
              val prawe = ctx.spawn(lisc(n), s"lisc-$n")
              zPoddrzewami(lewe, wartosc, prawe)
            } else {
              Behaviors.same
            }
          case Znajdz(n) =>
            if (n == wartosc) {
              ctx.log.info(s"Znaleziono wartość $n")
            } else if (n < wartosc) {
              lewe ! Znajdz(n)
            } else {
              ctx.log.info(s"Nie mam prawego poddrzewa!")
            }
            Behaviors.same
        }
      }
    }

    def zPrawymPoddrzewem(wartosc: Int, prawe: ActorRef[Cmd]): Behavior[Cmd] = {
      Behaviors.receive { (ctx, msg) =>
        msg match {
          case Wstaw(n) =>
            if (n < wartosc) {
              val lewe = ctx.spawn(lisc(n), s"lisc-$n")
              zPoddrzewami(lewe, wartosc, prawe)
            } else if (n > wartosc) {
              prawe ! Wstaw(n)
              Behaviors.same
            } else {
              Behaviors.same
            }
          case Znajdz(n) =>
            if (n == wartosc) {
              ctx.log.info(s"Znaleziono wartość $n")
            } else if (n > wartosc) {
              prawe ! Znajdz(n)
            } else {
              ctx.log.info(s"Nie mam lewego poddrzewa!")
            }
            Behaviors.same
        }
      }
    }

    def zPoddrzewami(lewe: ActorRef[Cmd], wartosc: Int, prawe: ActorRef[Cmd]): Behavior[Cmd] = {
      Behaviors.receive { (ctx, msg) =>
        msg match {
          case Wstaw(n) =>
            if (n < wartosc) {
              lewe ! Wstaw(n)
            } else if (n > wartosc) {
              prawe ! Wstaw(n)
            }
            Behaviors.same
          case Znajdz(n) =>
            if (n == wartosc) {
              ctx.log.info(s"Znaleziono wartość $n")
            } else if (n < wartosc) {
              lewe ! Znajdz(n)
            } else {
              prawe ! Znajdz(n)
            }
            Behaviors.same
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem(Wezel(5), "DrzewoSystem")
    val root = system
    root ! Wezel.Wstaw(3)
    root ! Wezel.Wstaw(7)
    root ! Wezel.Wstaw(5)
    root ! Wezel.Wstaw(2)
    root ! Wezel.Wstaw(9)
    root ! Wezel.Wstaw(3)
    root ! Wezel.Znajdz(3)
    root ! Wezel.Znajdz(7)
    root ! Wezel.Znajdz(9)
  }
}