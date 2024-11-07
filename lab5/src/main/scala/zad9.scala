package lab5

def minMax(seq: Seq[(String, Double)]): Option[(String, String)] = {
    val min = seq.minBy(_._2)
    val max = seq.maxBy(_._2)
    Some((min._1, max._1))
}

@main
def zad9: Unit = {
    println(minMax(Seq(("Andrzej", 1), ("Jacek",1), ("Dominik", 2), ("Paweł", 4), ("Andrzej",4), ("Michał",4), ("Cyprian", 1), ("Krystian",3))))
}