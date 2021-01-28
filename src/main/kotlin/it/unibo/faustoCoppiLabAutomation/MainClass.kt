package it.unibo.faustoCoppiLabAutomation

class MainClass {
    val hello: String
        get() {
            return "Hello world!!"
        }
}

fun main() {
    println(MainClass().hello)
}
