package lab5

def isOrdered[A](seq: Seq[A])(leq:(A, A) => Boolean): Boolean = {
    seq.sliding(2).forall(x => leq(x(0), x(1)))
}

@main
def zad6: Unit ={
    println(isOrdered(Seq(1, 2, 2, 4))(_ < _))
    println(isOrdered(Seq(1, 2, 2, 4))(_ <= _))
}