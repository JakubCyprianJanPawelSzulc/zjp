package lab3

import scala.annotation.tailrec

def value(n: Int): Int = {
  @tailrec
  def helper(n: Int, a: Int, b: Int): Int = {
    if (n == 0) a
    else if (n == 1) b
    else helper(n - 1, b, a + b)
  }

  helper(n, 2, 1)
}

@main
def zad4: Unit = {
  println(value(9))
  println(value(8))
}