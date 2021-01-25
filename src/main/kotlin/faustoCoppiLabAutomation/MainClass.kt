package faustoCoppiLabAutomation

class MainClass {
    val hello: String
        get() {
            return "Hello world!!"
        }
}

fun main(args: Array<String>) {
    println(MainClass().hello)
}
