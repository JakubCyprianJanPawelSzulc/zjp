package lab6

def getEuropeAndSort(strefy: Seq[String]): Seq[String] = {
    (for{
        strefa <- strefy
        if strefa.startsWith("Europe")
    } yield strefa.stripPrefix("Europe/")).groupBy(_.length()).toSeq.sortBy(_._1).flatMap(_._2)
}

@main
def zad4: Unit = {
    val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
    println(getEuropeAndSort(strefy))
}