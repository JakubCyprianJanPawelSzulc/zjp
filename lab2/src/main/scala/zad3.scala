package lab2

def triangle(wys: Int): Unit = {
  def pascal(row: Int, col: Int): Int = {
    if (col == 0 || col == row) 1
    else pascal(row - 1, col - 1) + pascal(row - 1, col)
  }

  def printRow(row: Int): Unit = {
    print(" " * (wys - row))
    for (col <- 0 to row) {
      print(s"${pascal(row, col)} ")
    }
    println()
  }

  def loop(row: Int): Unit = {
    if (row < wys) {
      printRow(row)
      loop(row + 1)
    }
  }

  loop(0)
}

@main
def zad3: Unit = {
  triangle(5)
}