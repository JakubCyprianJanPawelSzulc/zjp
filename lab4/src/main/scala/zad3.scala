package lab4

import scala.annotation.tailrec

def compress[A](list: List[A]): List[(A, Int)] = {
    @tailrec
    def helper(l: List[A], acc: List[(A, Int)]): List[(A, Int)] = l match {
        case Nil => acc.reverse
        case el :: rest => acc match {
            case Nil => helper(rest, List((el, 1)))
            case (a, n) :: t if a == el => helper(rest, (a, n + 1) :: t)
            case _ => helper(rest, (el, 1) :: acc)
        }
    }
    helper(list, List())
}

@main
def zad3: Unit = {
  println(compress(List('a','a','b','c','c','c','d','d','c')))
}

