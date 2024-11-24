@main
def zad3: Unit = {
 val linie = io.Source
  .fromResource("ogniem-i-mieczem.txt")
  .getLines.toList
  
 def histogram(maks: Int): String = {
    val onlyLetters = linie.mkString.filter(_.isLetter).toLowerCase
    val grouped = onlyLetters.groupBy(identity).map(x => (x._1, x._2.length)).toList.sortBy(x => (x._1))
    val withStars = grouped.map(x => (x._1, "*" * x._2))
    val limitedByMaks = withStars.map(x => (x._1, x._2.take(maks)))
    val result = limitedByMaks.map(x => x._1 + " " + x._2).mkString("\n")
    return result
 }
    println(histogram(20))
}
