file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_1_2022/src/main/scala/zad4.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1306
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_1_2022/src/main/scala/zad4.scala
text:
```scala
/*
    Korzystając wyłącznie z mechanizmów kolekcji języka Scala znajdź kraj o najdłużej rosnącym wskaźniku LadderScore.
    Innymi słowy, korzystając z załączonych danych szukamy kraju, dla którego wskaźnik LadderScore najdłużej
    utrzymał „dobrą passę” (z roku na rok się zwiększał).
    Zwróć uwagę na to, że w danych mogą wystąpić „linie” z brakującymi danymi. Takie linie powinny zostać
    POMINIĘTE. Brakujące dane oznaczają, że w linii występują sekwencje postaci: ,,, przykładowo:
        Kosovo,2020,6.294,,0.792,,0.880,,0.910,0.726,0.201
    Linie takie, jako „niewiarygodne” należy pominąć (oczywiście nie zmieniając samego pliku danych)
    zanim program rozpocznie analizę.
    W rozwiązaniu nie wolno używać zmiennych, ani konstrukcji imperatywnych, takich jak pętle
*/

// def findCountryWithLongestGrowingLadderScore(data: List[String]): String = {
def findCountryWithLongestGrowingLadderScore(data: List[String]): Any= {
    val splitByComma = data.map(_.split(",").toList)
    val filtered = splitByComma.filter(el => el.foldLeft(true)((acc, x) => acc && x != ""))
    val groupedByCountry = filtered.groupBy(_(0))
    val withSortedYears = groupedByCountry.map(x => (x._1, x._2.sortBy(_(1).toInt)))
    val ladderScores = groupedByCountry.map(x => (x._1, x._2.map(_(@@),_(2).toDouble)))
    return withSortedYears
}

@main
def zad4(): Unit ={
    val data = io.Source.fromFile("C:/Users/JSadr/Desktop/studia/semestr4/scala/kolokwium/src/main/resources/world-happiness-report.csv").getLines().toList
    println(findCountryWithLongestGrowingLadderScore(data))
}
```



#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:129)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:244)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:101)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:47)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:422)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1