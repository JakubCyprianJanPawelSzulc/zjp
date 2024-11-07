package lab5

def freq[A](seq: Seq[A]): Set[(A, Int)] = {
    seq.groupBy(identity).map(x => (x._1, x._2.length)).toSet
}

@main
def zad7: Unit ={
    println(freq(Seq('a','b','a','c','c','a')))
}