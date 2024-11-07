package lab5

def threeNumbers(n: Int): Set[(Int, Int, Int)] = {
    (for {
        a <- 1 to n
        b <- 1 to n
        c <- 1 to n
        if a * a + b * b == c * c && a <= b
    } yield (a, b, c)).toSet
}

@main
def zad10: Unit = {
    println(threeNumbers(10))
}