package lab4

import scala.annotation.tailrec

def divide[A](list: List[A]): (List[A], List[A]) = {
  @tailrec
  def helper(l: List[A], acc1: List[A], acc2: List[A]): (List[A], List[A]) = l match {
    case Nil => (acc1, acc2)
    case el1 :: Nil => (acc1 :+ el1, acc2)
    case el1 :: el2 :: rest => helper(rest, acc1 :+ el1, acc2 :+ el2)
  }
  helper(list, List(), List())
}

@main
def zad1: Unit = {
  println(divide(List(1, 3, 5, 6, 7)))
  println(divide(List(1, 2, 3, 4, 5, 6)))
}

