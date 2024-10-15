package lab2

def palindrome(tab: Array[Int]): Boolean = {
    if (tab.length <= 1) return true
    else if (tab(0) == tab(tab.length-1)) return palindrome(tab.slice(1, tab.length-1))
    else return false
}

@main
def zad2: Unit = {
  println(palindrome(Array(1, 2, 3, 2, 1)))
  println(palindrome(Array(1, 2, 3, 4, 5)))
}

