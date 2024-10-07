package lab1


def obramuj(napis: String): String = {
  val lines = napis.split('\n')
  val maxLineLength = lines.maxBy(s => s.length).length
  val frame = "*" * (maxLineLength + 4)
  val framedLines = lines.map(s => s"* $s${" " * (maxLineLength - s.length)} *")
  (frame +: framedLines :+ frame).mkString("\n")
}

@main
def zad2: Unit = {

  val napis = "Wejherowo, Wejherowo\nRumia, Reda, Władysławowo\nnawet piękna Kościerzyna\nkibicuje Arce Gdynia"
  println(obramuj(napis))
}
