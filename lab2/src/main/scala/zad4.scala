package lab2

def isPrime(num: Int): Boolean = {
  if (num < 2) return false
  for (i <- 2 to math.sqrt(num).toInt) {
    if (num % i == 0) return false
  }
  true
}


def isSum(n: Int): (Int, Int) = {
  for (a <- 2 to n if isPrime(a)) {
    val b = n - a
    if (isPrime(b)) {
      return (a, b)
    }
  }
  return(0,0)
}

@main def zad4: Unit = {
  print("Podaj liczbe: ")
  val liczba = io.StdIn.readInt()
  val wynik = isSum(liczba)

  if wynik == (0,0) then println(s"$liczba nie moze byc wyrazona jako suma liczb pierwszych")
  else println(s"$liczba jest suma $wynik")
}
