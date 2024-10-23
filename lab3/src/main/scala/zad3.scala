package lab3

import scala.annotation.tailrec

def binToDec(bin: Int): Int = {
    @tailrec
    def helper(bin: Int, acc: Int = 0): Int = {
        if (bin == 0) acc
        else helper(bin / 10, acc * 2 + bin % 10)
    }
    helper(bin)
}

@main
def zad3: Unit = {
  println(binToDec(101))
  println(binToDec(1011))
}

