package lab4

import scala.annotation.tailrec

def isSub[A](l: List[A], lSub: List[A]): Boolean = {
    @annotation.tailrec
    def loop(sub: List[A], main: List[A]): Boolean = sub match {
        case Nil => true
        case x :: xs => main match {
            case Nil => false
            case y :: ys =>
                if (x == y) loop(xs, l)
                else loop(sub, ys)
        }
    }

    loop(lSub, l)
}


@main
def zad4: Unit = {
  println(isSub(List('b', 'o', 'c', 'i', 'a', 'n'), List('a', 'b', 'c')))
  println(isSub(List('b', 'o', 'c', 'i', 'a', 'n'), List('g', 'a', 'b', 'c')))
}

