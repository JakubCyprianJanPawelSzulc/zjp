package lab4

import scala.annotation.tailrec

def compose[A, B, C](f: A => B)(g: B => C): A => C = {
    a => g(f(a))
}

def prod[A, B, C, D](f: A => C, g: B => D): (A, B) => (C, D) = {
    (a, b) => (f(a), g(b))
}

def lift[A, B, T](op: (T,T) => T)(f: A => T, g: B => T): (A,B) => T = {
    (a, b) => op(f(a), g(b))
}

type MSet = A => Int

@main
def zad6: Unit = {
}
