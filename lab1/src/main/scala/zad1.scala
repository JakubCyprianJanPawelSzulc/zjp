package lab1

def isPrime(num: Int): Boolean = {
  if (num < 2) return false
  for (i <- 2 to math.sqrt(num).toInt) {
    if (num % i == 0) return false
  }
  true
}


def isSum(n: Int): Option[(Int, Int)] = {
  for (a <- 2 to n if isPrime(a)) {
    val b = n - a
    if (isPrime(b)) {
      return Some(a, b)
    }
  }
  None
}

@main def zad1: Unit = {
  print("Podaj liczbe: ")
  val liczba = io.StdIn.readInt()
  val wynik = isSum(liczba)

  wynik match {
    case None => println(s"$liczba nie może być wyrażone jako suma dwóch liczb pierwszych")
    case Some((a, b)) => println(s"$liczba jest sumą $a i $b")
  }
}
