class C(val re: Double, val im: Double){

  def this(re: Double) = this(re, 0)

  override def toString(): String = im match{
    case 0 => s"$re"
    case _ if im > 0 => s"$re + ${im}i"
    case _ => s"$re + ${-im}i"
  }

  def +(that: C): C = new C(this.re + that.re, this.im + that.im)
  def +(that: Double): C = new C(this.re + that, this.im)

  def -(that: C): C = new C(this.re - that.re, this.im - that.im)
  def -(that: Double): C = new C(this.re - that, this.im)

  def *(that: C): C = new C(this.re * that.re - this.im * that.im, this.re * that.im + this.im * that.re)
  def *(that: Double): C = new C(this.re * that, this.im * that)

  def /(that: C): C = {
    val denominator = that.re * that.re + that.im * that.im
    if (denominator == 0) throw new IllegalArgumentException("Division by zero")
    new C((this.re * that.re + this.im * that.im) / denominator, (this.im * that.re - this.re * that.im) / denominator)
  }
  def /(that: Double): C = new C(this.re / that, this.im / that)

  def >(that: C): Boolean = this.re > that.re && this.im > that.im
  def <(that: C): Boolean = this.re < that.re && this.im < that.im
  def ==(that: C): Boolean = this.re == that.re && this.im == that.im
}

extension (x: Double) {
  def +(that: C): C = new C(x + that.re, that.im)
  def -(that: C): C = new C(x - that.re, -that.im)
  def *(that: C): C = new C(x * that.re, x * that.im)
  def /(that: C): C = new C(x / that.re, x / that.im)
}



@main
def zad1: Unit = {
  val z1 = new C(3, 4)
  val z2 = new C(1, -2)
  // val z1 = new C(0, 0)
  // val z2 = new C(0, 0)
  val z3 = new C(5)

  println(s"z1: $z1")
  println(s"z2: $z2")
  println(s"z3: $z3")

  println(s"z1 + z2: ${z1 + z2}")
  println(s"z1 - z2: ${z1 - z2}")
  println(s"z1 * z2: ${z1 * z2}")
  println(s"z1 / z2: ${z1 / z2}")

  println(s"z1 > z2: ${z1>z2}")
  println(s"z1 < z2: ${z1<z2}")
  println(s"z1 == z2: ${z1==z2}")

  println(s"z1 + 7: ${z1 + 7}")
  println(s"z1 - 7: ${z1 - 7}")
  println(s"z1 * 7: ${z1 * 7}")
  println(s"z1 / 7: ${z1 / 7}")
}

