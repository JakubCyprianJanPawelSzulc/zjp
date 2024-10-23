package lab3

import scala.annotation.tailrec

def isOrdered(tab: Array[Int], mlr: (Int, Int) => Boolean): Boolean = {
  @tailrec
  def helper(index: Int): Boolean = {
    if (index >= tab.length - 2) true
    else if (!mlr(tab(index), tab(index + 1))) false
    else helper(index + 1)
  }

  helper(0)
}

@main
def zad5: Unit = {
  println(isOrdered(Array(1, 3, 3, 6, 8), _ <= _))
  println(isOrdered(Array(1, 3, 3, 6, 8), _ < _))
}