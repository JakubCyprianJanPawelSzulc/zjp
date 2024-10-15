file:///C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad2.scala
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition zad1 is defined in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/Main.scala
and also in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad1.scala
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.3\scala3-library_3-3.3.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar [exists ]
Options:



action parameters:
offset: 154
uri: file:///C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad2.scala
text:
```scala
package lab2

def reverse(str: String): String ={
  if (str.length == 0) return ""
  else return reverse(str.substring(1)) + str(0)
}

@main
def z@@: Unit = {
  println(reverse("Arka Gdynia"))
}


```



#### Error stacktrace:

```

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition zad1 is defined in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/Main.scala
and also in
  C:/Users/JSadr/Desktop/studia2/Scala/lab2/src/main/scala/zad1.scala
One of these files should be removed from the classpath.