package lab6

def countChars(str: String): Int = {
  str.toSet.size
}

@main
def zad1: Unit = {
  println(countChars("aaabbcc"))
  println(countChars("korzystajac z metod oferowanych przez kolekcje zdefiniuj funkcje"))
}

