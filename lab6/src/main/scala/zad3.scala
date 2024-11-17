package lab6

def swap[A](seq: Seq[A]): Seq[A] = {
    seq.sliding(2, 2).flatMap(_.reverse).toSeq
}

@main
def zad3: Unit = {
    println(swap(Seq(1, 2, 3, 4, 5)))
}

