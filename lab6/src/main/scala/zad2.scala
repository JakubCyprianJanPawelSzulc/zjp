package lab6

def minNotCon(set: Set[Int]): Int = {
    (for{
        i <- 0 to set.size
        if !set.contains(i)
    } yield i)(0)
}

@main
def zad2: Unit = {
  print(minNotCon(Set(-3, 0, 1, 2, 5, 6)))
}

