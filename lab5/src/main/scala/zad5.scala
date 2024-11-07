package lab5

def deStutter[A](seq: Seq[A]): Seq[A] = {
    seq.foldLeft(Seq[A]())((acc, x)=>{
        if(acc.isEmpty || acc(acc.size - 1) != x) acc :+ x
        else acc
    })
}

@main
def zad5: Unit = {
    val seq = Seq(1, 1, 2, 4, 4, 4, 1, 3)
    println(deStutter(seq))
}