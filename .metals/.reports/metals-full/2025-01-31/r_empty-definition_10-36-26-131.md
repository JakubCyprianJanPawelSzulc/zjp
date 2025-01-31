error id: same.
file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz_2/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: same.
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -Behaviors.same.
	 -Behaviors.same#
	 -Behaviors.same().
	 -scala/Predef.Behaviors.same.
	 -scala/Predef.Behaviors.same#
	 -scala/Predef.Behaviors.same().

Document text:

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
        context.become(zPrawymPoddrzewem(wartosc, prawe))
      } else if (liczba < wartosc){
        val lewe = ctx.spawn(lisc(liczba), s"lisc-$liczba")
        context.become(zPoddrzewami(lewe, wartosc, prawe))
      }else{
        context.become(zPrawymPoddrzewem(wartosc, prawe))
      }

    case Znajdz(liczba) =>
      if (liczba == wartosc) {
        log.info(s"Ja jestem szukaną wartoscia - $liczba")
      }else if(liczba > wartosc){
        prawe ! Znajdz(liczba)
      }else if(liczba < wartosc){
        log.info(s"Nie znaleziono warosci - $liczba")
      }
      context.become(zPrawymPoddrzewem(wartosc, prawe))
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

#### Short summary: 

empty definition using pc, found symbol in pc: same.