package lab4

import scala.annotation.tailrec

def merge[A](a: List[A], b: List[A])(leq: (A, A) => Boolean): List[A] = {

    def myReverse(l: List[A]): List[A] = {
        @tailrec
        def helper2(l: List[A], acc: List[A]): List[A] = l match {
            case Nil => acc
            case x :: xs => helper2(xs, x :: acc)
        }
        helper2(l, Nil)
    }

    @tailrec
    def helper(a: List[A], b: List[A], acc: List[A])(leq: (A,A) => Boolean): List[A] = (a, b) match{
        case (Nil, Nil) => myReverse(acc)
        case (Nil, y :: ys) => helper(Nil, ys, y :: acc)(leq)
        case (x :: xs, Nil) => helper(xs, Nil, x :: acc)(leq)
        case (x :: xs, y :: ys) =>
            if (leq(x, y)) helper(xs, b, x :: acc)(leq)
            else helper(a, ys, y :: acc)(leq)
    }

    helper(a, b, Nil)(leq)
}

@main
def zad2: Unit = {
    println(merge(List(1 ,3, 5, 8), List(2, 4, 6, 8, 10, 12))(_ < _)) //List(1, 2, 3, 4, 5, 6, 8, 8, 10, 12)
}