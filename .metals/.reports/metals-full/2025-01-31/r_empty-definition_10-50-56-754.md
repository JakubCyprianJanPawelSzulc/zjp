error id: info.
file:///C:/Users/JSadr/Desktop/studia2/Scala/lab14jeszczeraz_2/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: info.
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -log/info.
	 -log/info#
	 -log/info().
	 -scala/Predef.log.info.
	 -scala/Predef.log.info#
	 -scala/Predef.log.info().

Document text:

```scala
import org.apache.pekko.actor.{ActorSystem, Actor, ActorLogging, ActorRef, Props}

case class Wstaw(n: Int)
case class Znajdz(n: Int)

class Wezel(wartosc: Int) extends Actor with ActorLogging {
  def receive: Receive = lisc(wartosc)
  def lisc(wartosc: Int): Receive =  {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        val prawe = context.actorOf(Props(new Wezel(liczba)), s"lisc-$liczba")
        context.become(zPrawymPoddrzewem(wartosc, prawe))
      } else if (liczba < wartosc){
        val lewe = context.actorOf(Props(new Wezel(liczba)), s"lisc-$liczba")
        context.become(zLewymPoddrzewem(lewe, wartosc))
      } else{
        context.become(lisc(wartosc))
      }
    case Znajdz(n) =>
      if (n == wartosc) {
        log.info(s"Znaleziono $n")
      } else {
        log.info(s"Nie znaleziono $n")
      }
  }
  def zLewymPoddrzewem(lewe: ActorRef, wartosc: Int): Receive = {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        val prawe = context.actorOf(Props(new Wezel(liczba)), s"lisc-$liczba")
        zPoddrzewami(lewe, wartosc, prawe)
      } else if (liczba < wartosc){
        lewe ! Wstaw(liczba)
        context.become(zLewymPoddrzewem(lewe, wartosc))
      }else{
        context.become(zLewymPoddrzewem(lewe, wartosc))
      }
    case Znajdz(liczba) =>
      if (liczba == wartosc) {
        log.info(s"Ja jestem szukana wartoscia - $liczba")
      }else if(liczba > wartosc){
        log.info(s"Nie znaleziono warosci - $liczba")
      }else if(liczba < wartosc){
        lewe ! Znajdz(liczba)
      }
      context.become(zLewymPoddrzewem(lewe,wartosc))
  }
  def zPrawymPoddrzewem(wartosc: Int, prawe: ActorRef): Receive = {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        prawe ! Wstaw(liczba)
        context.become(zPrawymPoddrzewem(wartosc, prawe))
      } else if (liczba < wartosc){
        val lewe = context.actorOf(Props(new Wezel(liczba)), s"lisc-$liczba")
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
  def zPoddrzewami(lewe: ActorRef, wartosc: Int, prawe: ActorRef): Receive = {
    case Wstaw(liczba) =>
      if (liczba > wartosc) {
        prawe ! Wstaw(liczba)
        context.become(zPoddrzewami(lewe, wartosc, prawe))
      } else if (liczba < wartosc){
        lewe ! Wstaw(liczba)
        context.become(zPoddrzewami(lewe, wartosc, prawe))
      } else if (liczba == wartosc){
        lewe ! Wstaw(liczba)
        context.become(zPoddrzewami(lewe, wartosc, prawe))
      }else{
        context.become(zPoddrzewami(lewe, wartosc, prawe))
      }

    case Znajdz(liczba) =>
      if (liczba == wartosc) {
        log.info(s"Ja jestem szukaną wartoscia - $liczba")
      }else if(liczba > wartosc){
        prawe ! Znajdz(liczba)
      }else if(liczba < wartosc){
        lewe ! Znajdz(liczba)
      }
      context.become(zPoddrzewami(lewe, wartosc, prawe))
  }
}

@main
def mainProg: Unit = {
  val system = ActorSystem("drzewo")
  val root = system.actorOf(Props(new Wezel(1)), "root")
  root ! Wstaw(3)
  root ! Wstaw(7)
  root ! Wstaw(5)
  root ! Wstaw(2)
  root ! Wstaw(9)
  root ! Wstaw(3)
  root ! Znajdz(3)
  root ! Znajdz(7)
  root ! Znajdz(9)
}
```

#### Short summary: 

empty definition using pc, found symbol in pc: info.