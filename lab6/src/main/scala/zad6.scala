package lab6

case class Ocena(
    imie: String,
    nazwisko: String,
    wdziek: Int,
    spryt: Int
) {
    require(
    imie.trim() != "" &&
    nazwisko.trim() != "" &&
    (0 to 20).contains(wdziek) &&
    (0 to 20).contains(spryt)
  )
}

case class Wynik(
  miejsce: Int,
  imie: String,
  nazwisko: String,
  sredniWdziek: Double,
  sredniSpryt: Double,
  suma: Double
) {
  require(
    miejsce >= 0 &&
    imie.trim() != "" &&
    nazwisko.trim() != "" &&
    sredniWdziek >= 0 && sredniWdziek <= 20 &&
    sredniSpryt >= 0 && sredniSpryt <= 20 &&
    suma == sredniWdziek + sredniSpryt
  )
}

def obliczWyniki(oceny: Seq[Ocena]): Seq[Wynik] = {
    val grouped = oceny.groupBy(ocena => (ocena.imie, ocena.nazwisko))
    val scoresWithAvg = grouped.map({
        case ((imie, nazwisko), oceny) => {
            val sredniWdziek = oceny.map(_.wdziek).sum.toDouble / oceny.size
            val sredniSpryt = oceny.map(_.spryt).sum.toDouble / oceny.size
            val suma = sredniWdziek + sredniSpryt
            Wynik(0, imie, nazwisko, sredniWdziek, sredniSpryt, suma)
        }
    })
    val groupedByScoresAndInitiallySorted = scoresWithAvg.groupBy(_.suma).toList.sortBy(_._1).reverse
    val sortedScores = groupedByScoresAndInitiallySorted.flatMap({
        case (suma, wyniki) => {
            wyniki.toList.sortBy(wynik => (-wynik.sredniWdziek))
        }
    }).toList
    val sortedWithPlaces = sortedScores.zipWithIndex.map({
        case (wynik, index) => wynik.copy(miejsce = index + 1)
    })
    sortedWithPlaces
}

@main
def zad6: Unit = {
    println(obliczWyniki(Seq(Ocena("Jakub", "Zygarlowski", 2, 1), Ocena("Zbigniew", "Huczko", 20, 20), Ocena("Zbigniew", "Huczko", 20, 20), Ocena("Michal", "Woznicki", 1, 2), Ocena("Michal", "Woznicki", 1, 2))))
}