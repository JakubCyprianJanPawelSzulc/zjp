def leastVal[A, B](l1: List[A], l2: List[B])(op: (A, B) => Double): Double = {
	if (l1.isEmpty || l2.isEmpty) throw new IllegalArgumentException
	@annotation.tailrec
	def helper[A, B](l1: List[A], l2: List[B], acc: Double)(op: (A, B) => Double): Double = (l1, l2) match {
		case (Nil, _) => acc
		case (_, Nil) => acc
		case (h1 :: t1, h2 :: t2) =>
			val newAcc = acc match {
				case -100000 => op(h1, h2)
				case _ => Math.min(acc, op(h1, h2))
			}
			helper(t1, t2, newAcc)(op)
	}
	helper(l1, l2, -100000)(op)
}

@main def zad1: Unit = {
	val l1 = List(1,2,3)
	val l2 = List(5,4,3,4,7,2,4,6)
	println(leastVal(l1, l2)(_ - _))
	
}
