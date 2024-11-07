package lab5

def remElems[A](seq: Seq[A], k: Int): Seq[A] = {
    seq.zipWithIndex.filter(_._2 != k).map(_._1)
}

@main
def zad2: Unit = {
    val seq = Seq(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    println(remElems(seq, 3))
}
