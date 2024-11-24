// def mostDifferentLettersAndShortest(names: List[String]): List[String] = {
def mostDifferentLettersAndShortest(names: List[String]): Any = {
    val sortedList = names.map(x => x.splitAt(x.indexOf(" "))).map(x=>(x._1, x._1.distinct.length, x._2.tail, x._2.tail.length)).sortBy(x=>(-x._2, x._4))
    val max = sortedList.filter(x=> x._2 == sortedList.head._2 && x._4 == sortedList.head._4)
    return max
}


@main
def zad2: Unit = {
 val linie = io.Source
  .fromResource("nazwiska.txt")
  .getLines.toList
  println(mostDifferentLettersAndShortest(linie))
 }
