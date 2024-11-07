package lab5

def sumOption(seq: Seq[Option[Double]]): Double = {
    seq.foldLeft(0.0)((acc, x) => acc + x.getOrElse(0.0))
}

@main
def zad4: Unit = {
    val seq = Seq(Some(5.4), Some(-2.0), Some(1.0), None, Some(2.6))
    println(sumOption(seq))
}