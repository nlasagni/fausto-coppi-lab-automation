package it.unibo.lss.fcla

class MainClass {
    val hello: String
        get() {
            return "Hello world!!"
        }
}

fun main() {
    println(MainClass().hello)
}
