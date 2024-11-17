package lab6

def score(code: Seq[Int])(move: Seq[Int]): (Int, Int) = {
  val blacks = code.zip(move).count { case (c, m) => c == m }
  val remainingCode = code.zip(move).filterNot { case (c, m) => c == m }.map(_._1)
  val remainingMove = code.zip(move).filterNot { case (c, m) => c == m }.map(_._2)

  val whites = remainingMove.groupBy(identity).map {
    case (color, occurrences) => 
      Math.min(occurrences.size, remainingCode.count(_ == color))
  }.sum

  (blacks, whites)
}

@main
def zad5: Unit = {
  val code = Seq(1, 3, 2, 2, 4, 5)
  val move = Seq(2, 1, 2, 4, 7, 2)
  println(score(code)(move))
}

