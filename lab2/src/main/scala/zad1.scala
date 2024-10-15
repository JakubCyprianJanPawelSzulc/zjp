package lab2

def reverse(str: String): String ={
  if (str.length == 0) return ""
  else return reverse(str.tail) + str.head
}

@main
def zad1: Unit = {
  println(reverse("Arka Gdynia"))
}

