import org.apache.pekko
import pekko.actor._

case class Init(liczbaPracownikow: Int)
case class Zlecenie(tekst: List[String])
case class Wykonaj(tekst: String)
case class Wynik(listaSlow: Map[String, Int])

class Boss(dane: List[String]) extends Actor {
  def receive: Receive = {
    case "Start" =>
      println("Rozpoczynam przetwarzanie danych.")
      val nadzorca = context.actorOf(Props[Nadzorca](), "nadzorca")
      nadzorca ! Init(4)
      nadzorca ! Zlecenie(dane)
  }
}

class Nadzorca extends Actor {
  def receive: Receive = {
    case Init(liczbaPracownikow) =>
      val workers = for (i <- 1 to liczbaPracownikow) yield context.actorOf(Props[Pracownik](), s"pracownik$i")
      context.become(ready(workers, 0, 0, Map.empty, 0))
  }

  def ready(
      workers: scala.collection.immutable.Seq[ActorRef],
      done: Int,
      total: Int,
      aggregatedResults: Map[String, Int],
      pending: Int
  ): Receive = {
    case Zlecenie(tekst) =>
      val totalTasks = tekst.length
      tekst.zipWithIndex.foreach { case (line, index) =>
        val chosenWorker = workers(index % workers.length)
        chosenWorker ! Wykonaj(line)
      }
      context.become(ready(workers, done, totalTasks, aggregatedResults, totalTasks))

    case Wynik(listaSlow) =>
      val updatedResults = listaSlow.foldLeft(aggregatedResults) { case (acc, (word, count)) =>
        acc.updated(word, acc.getOrElse(word, 0) + count)
      }
      val remaining = pending - 1

      if (remaining == 0) {
        println(s"Wynik końcowy: $updatedResults")
        context.become(ready(workers, 0, 0, Map.empty, 0))
      } else {
        context.become(ready(workers, done + 1, total, updatedResults, remaining))
      }
  }
}

class Pracownik extends Actor {
  def receive: Receive = {
    case Wykonaj(tekst) =>
      val result = tekst
        .split("\\W+")
        .filter(_.nonEmpty)
        .map(_.toLowerCase)
        .groupBy(identity)
        .view.mapValues(_.size)
        .toMap
      sender() ! Wynik(result)
  }
}

@main
def zad1(): Unit = {
  val dane: List[String] = scala.io.Source.fromResource("ogniem_i_mieczem.txt").getLines().toList
  val system = ActorSystem("WordCounter")
  val boss = system.actorOf(Props(new Boss(dane)), "boss")
  boss ! "Start"
}
