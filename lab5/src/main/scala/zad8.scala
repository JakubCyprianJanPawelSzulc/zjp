package lab5

def median(seq: Seq[(String, Double)]): Double = {
    val sorted = seq.sortBy(_._2)
    val n = sorted.length
    if(n % 2 == 0){
        (sorted(n/2)._2 + sorted(n/2 - 1)._2) / 2
    }else{
        sorted(n/2)._2
    }
}

@main
def zad8: Unit ={
    println(median(Seq(("Andrzej", 1), ("Jacek",1), ("Dominik", 2), ("Paweł", 4), ("Andrzej",4), ("Michał",4), ("Cyprian", 1), ("Krystian",3))))
}