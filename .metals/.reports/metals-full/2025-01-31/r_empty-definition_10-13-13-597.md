error id: scala/Int#
file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: scala/Int#
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -Int#
	 -scala/Predef.Int#

Document text:

```scala
package example_typed

import org.apache.pekko.actor.typed.{ActorRef, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.ActorSystem
import scala.util.Random

object Drzewo {

  object Wezel {
    sealed trait Command
    case class Wstaw(n: Int) extends Command
    case class Znajdz(n: Int) extends Command

    def apply(wartosc: Int): Behavior[Command] = lisc(wartosc)

    def lisc(wartosc: Int): Behavior[Command] = Behaviors.receive{(ctx, msg)=>
      msg match {
        case Wstaw(liczba) =>
          if (liczba > wartosc) {
            val prawe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
            zPrawymPoddrzewem(wartosc, prawe)
          } else if (liczba < wartosc){
            val lewe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
            zLewymPoddrzewem(lewe, wartosc)
          } else if (liczba == wartosc){
            val lewe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
            zLewymPoddrzewem(lewe, wartosc)
          }else{
            Behaviors.same
          }

        case Znajdz(liczba) =>
          if (liczba == wartosc) {
            ctx.log.info(s"Ja jestem szukaną wartoscia - $liczba")
          }else{
            ctx.log.info(s"Nie znaleziono warosci - $liczba")
          }
          Behaviors.same
      }
    }
    
    def zLewymPoddrzewem(lewe: ActorRef[Command], wartosc: Int): Behavior[Command] = Behaviors.receive{(ctx, msg)=>
      msg match {
        case Wstaw(liczba) =>
          if (liczba > wartosc) {
            val prawe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
            zPoddrzewami(lewe, wartosc, prawe)
          } else if (liczba < wartosc){
            lewe ! Wstaw(liczba)
            Behaviors.same
          } else if (liczba == wartosc){
            lewe ! Wstaw(liczba)
            Behaviors.same
          }else{
            Behaviors.same
          }

        case Znajdz(liczba) =>
          if (liczba == wartosc) {
            ctx.log.info(s"Ja jestem szukaną wartoscia - $liczba")
          }else if(liczba > wartosc){
            ctx.log.info(s"Nie znaleziono warosci - $liczba")
          }else if(liczba < wartosc){
            lewe ! Znajdz(liczba)
          }
          Behaviors.same
      }
    }
    def zPrawymPoddrzewem(wartosc: Int, prawe: ActorRef[Command]): Behavior[Command] = Behaviors.receive{(ctx, msg)=>
      msg match {
        case Wstaw(liczba) =>
          if (liczba > wartosc) {
            prawe ! Wstaw(liczba)
            Behaviors.same
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
    }
    def zPoddrzewami(lewe: ActorRef[Command], wartosc: Int, prawe: ActorRef[Command]): Behavior[Command] = Behaviors.receive{(ctx, msg)=>
      msg match {
        case Wstaw(liczba) =>
          if (liczba > wartosc) {
            prawe ! Wstaw(liczba)
            Behaviors.same
          } else if (liczba < wartosc){
            lewe ! Wstaw(liczba)
            Behaviors.same
          } else if (liczba == wartosc){
            lewe ! Wstaw(liczba)
            Behaviors.same
          }else{
            Behaviors.same
          }

        case Znajdz(liczba) =>
          if (liczba == wartosc) {
            ctx.log.info(s"Ja jestem szukaną wartoscia - $liczba")
          }else if(liczba > wartosc){
            prawe ! Znajdz(liczba)
          }else if(liczba < wartosc){
            lewe ! Znajdz(liczba)
          }
          Behaviors.same
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
```

#### Short summary: 

empty definition using pc, found symbol in pc: scala/Int#