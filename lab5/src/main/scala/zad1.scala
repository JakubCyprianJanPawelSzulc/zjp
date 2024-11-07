package lab5

def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = {
  seq.drop(begIdx).take(endIdx - begIdx)
}

@main
def zad1: Unit = {
  val seq = Seq(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  println(subSeq(seq, 3, 7))
}

