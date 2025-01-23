@main def zad2: Unit = {
 val domki = io.Source
  .fromResource("domki.txt")
  .getLines.toList

 val rezerwacje = io.Source 
  .fromResource("rezerwacje.txt")
  .getLines.toList

  val rezerwacjeGrouppedByDomek = rezerwacje.map(el=> el.split(";").toList).groupBy(el=>el(1))
  val rezerwacjeDomkiDobyLista = rezerwacjeGrouppedByDomek.map(el=>(el._1, el._2.map(x=>x(2))))
  val rezerwacjeDomkiDobySuma = rezerwacjeDomkiDobyLista.map(el=>(el._1, el._2.map(el=>el.toInt).foldLeft(0)(_+_))).toList

  val domkiListInt = domki.map(el=>el.split(";").toList.map(el=>el.toInt))

  val calculations = rezerwacjeDomkiDobySuma.map { case (domekNr, sumaNocy) =>
    val domek = domkiListInt.find(_(0) == domekNr.toInt).get
    val liczbaPokoi = domek(1)
    val cenaZaDobe = domek(2)
    val zysk = sumaNocy * cenaZaDobe
    (liczbaPokoi, zysk)
  }

  val groupedByLiczbaPokoi = calculations.groupBy(el=> el._1)
  val groupedByLiczbaPokoiSum = groupedByLiczbaPokoi.toList.map { case (liczbaPokoi, listaZyskow) =>
    (liczbaPokoi, listaZyskow.map(_._2).sum)
  }

  val sorted = groupedByLiczbaPokoiSum.sortBy(el => el._2).reverse
  val withIndexes = sorted.zipWithIndex
  val cleaned = withIndexes.map(el=> (el._2+1, el._1)).map(el=> (el._1, el._2(0), el._2(1)))

  // println(cleanedExAequoByCenaZaDobe)
  println(cleaned)
}
