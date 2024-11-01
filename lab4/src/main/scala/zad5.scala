package lab4

import scala.annotation.tailrec

def compute[A, B](l: List[Option[A]])(op1: A => B)(op2: (A, B) => B): Option[B] = {
  @tailrec
  def helper(lst: List[Option[A]], acc: Option[B]): Option[B] = lst match {
    case Nil => acc
    case Some(el) :: rest => helper(rest, acc match {
      case None => Some(op1(el))
      case Some(a) => Some(op2(el, a))
    })
    case None :: rest => helper(rest, acc)
  }
  helper(l, None)
}

@main
def zad5: Unit = {
  println(compute(List(Some(1), None, Some(2), None, Some(3), Some(4)))(_ + 0)(_ + _))
}