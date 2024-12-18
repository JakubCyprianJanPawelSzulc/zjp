file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_1_2020/src/main/scala/zad2kolos2020.scala
### dotty.tools.dotc.ast.Trees$UnAssignedTypeException: type of Number(2,Whole(10)) is not assigned

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1687
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/kolos_1_2020/src/main/scala/zad2kolos2020.scala
text:
```scala
/*
  Zadanie 2. Korzystając z mechanizmów kolekcji języka Scala zdefiniuj funkcje

  def stwórzIndeks(): Map[String, List[Int]]

  def słowa(indeks: Map[String, List[Int]], nrLinii: Int): Set[String]

  Pierwsza z nich powinna utworzyć indeks wszystkich słów występujących w pliku
  „tekst.txt”. Każdemu słowu należy przypisać listę wszystkich numerów linii, w których
  ono występuje. W ramach takiej listy numery linii powinny być uporządkowane rosnąco
  nie mogą się powtarzać. Budując indeks traktuj wielkie i małe litery identycznie!

  Druga z funkcji, na bazie zbudowanego przez stwórzIndeks() indeksu (parametr indeks),
  powinna dla podanego numeru linii (parametr nrLinii) zwrócić zbiór wszystkich słów
  występujących w tej linii. UWAGA! Funkcja „słowa” musi korzystać z indeksu, a nie
  z pliku „tekst.txt”!

  Rozwiązując zadanie wykorzystaj JEDYNIE mechanizmy kolekcji – w szczególności nie
  definiuj żadnej funkcji rekurencyjnie!!!

  WARTOŚĆ ZADANIA: 3 pkt
*/

package zad2kolos2020
import scala.io.Source

// def stwórzIndeks(): Map[String, List[Int]] = {
def stwórzIndeks(): Any = {
  val data = io.Source.fromFile("C:/Users/JSadr/Desktop/studia/semestr4/scala/kolokwium2020/src/main/scala/tekst.txt").getLines().toList
  val splitUp = data.map(el => el.split(" ").toList).zipWithIndex
  val cleanedWordsWithIndexes = splitUp.map(el=> (el(0).map(x => (x.toLowerCase().replaceAll("[^a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]", ""), el(1)))))
  val cleanedCleaned = cleanedWordsWithIndexes.flatten.filter(el=> el(0)!="")
  val distinctByWord = cleanedCleaned.groupBy(_._1).map(el=> (el(0), el(1).map(x => x(1))))

  distinctByWord.map(el=> (el(1), el(2@@)))
}

def słowa(indeks: Map[String, List[Int]], nrLinii: Int): Set[String] = {
  ???
}


@main
def zad2kolos2020()={
  println(stwórzIndeks())
  // println(słowa(stwórzIndeks(), 0))
}







```



#### Error stacktrace:

```
dotty.tools.dotc.ast.Trees$Tree.tpe(Trees.scala:74)
	dotty.tools.dotc.util.Signatures$.$anonfun$29(Signatures.scala:686)
	scala.collection.immutable.List.map(List.scala:247)
	dotty.tools.dotc.util.Signatures$.bestAlternative(Signatures.scala:686)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:225)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:101)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:47)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:422)
```
#### Short summary: 

dotty.tools.dotc.ast.Trees$UnAssignedTypeException: type of Number(2,Whole(10)) is not assigned