package lab3

import scala.annotation.tailrec

def worth(tab1: Array[Int], tab2: Array[Int])(pred: (Int, Int) => Boolean)(op: (Int, Int) => Int): Option[Int] = {
  @tailrec
  def helper(index: Int): Option[Int] = {
    if (index >= tab1.length || index >= tab2.length) None
    else if (pred(tab1(index), tab2(index))) Some(op(tab1(index), tab2(index)))
    else helper(index+1)
  }

  helper(0)
}

@main
def zad6: Unit = {
  println(worth(Array(-1, 3, 2, -8, 5), Array(-3, 3, 3, 0, -4, 5))(_ < _)(_ + _))
}