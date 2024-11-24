def nonDecreasingSeriesAndSumIsOdd (list: List[String]): List[String] = {
  list.filter {
    x => {
      val numbers = x.toArray.map(_.toInt)
      numbers.sliding(2).forall {
        case Array(a, b) => a <= b
      } && numbers.sum % 2 == 1
    }
  }
}

@main
def zad1: Unit = {
 val linie = io.Source
  .fromResource("liczby.txt")
  .getLines.toList

  println(nonDecreasingSeriesAndSumIsOdd(linie))
}
