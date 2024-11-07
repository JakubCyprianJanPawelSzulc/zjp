package lab5

def diff[A](seq1: Seq[A], seq2: Seq[A]): Seq[A] = {
    seq1.zip(seq2).filter(x => x._1 != x._2).map(_._1)
}

@main
def zad3: Unit = {
    val seq1 = Seq(1, 2, 3)
    val seq2 = Seq(2, 2, 1, 3)
    println(diff(seq1, seq2))
}