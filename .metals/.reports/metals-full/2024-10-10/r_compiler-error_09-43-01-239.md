file:///C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad3.scala
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition reverse is defined in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad1.scala
and also in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad4.scala
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.3\scala3-library_3-3.3.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 188
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad3.scala
text:
```scala
package lab2

def triangle(wys: Int): Unit = {
  def pascal(row: Int, col: Int): Int = {
    if (col == 0 || col == row) 1
    else pascal(row - 1, col - 1) + pascal(row - 1, col)
  @@}

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
```



#### Error stacktrace:

```

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition reverse is defined in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad1.scala
and also in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad4.scala
One of these files should be removed from the classpath.