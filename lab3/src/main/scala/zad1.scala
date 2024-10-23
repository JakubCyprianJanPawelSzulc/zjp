package lab3

import scala.annotation.tailrec

def reverse (str: String): String = {

  @tailrec
  def helper(str: String, acc: String = ""): String = {
    if (str=="") acc
    else helper(str.tail, str.head +: acc)
  }
  helper(str)
}

@main
def zad1: Unit = {
  println(reverse("Arka Gdynia"))
}

