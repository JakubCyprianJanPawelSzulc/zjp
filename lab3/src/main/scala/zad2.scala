package lab3

import scala.annotation.tailrec

def isPrime(n: Int): Boolean = {
  @tailrec
  def helper(n: Int, i: Int = 2): Boolean = {
    if (i*i > n) true
    else if (n % i == 0) false
    else helper(n, i + 1)
  }
  helper(n)
}

@main
def zad2: Unit = {
  println(isPrime(7))
}

