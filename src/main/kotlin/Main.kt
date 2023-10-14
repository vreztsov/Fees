package ru.netology

fun main() {
    var sum = 0
    val a1 = 10000
    println(getFee(amount = a1))
    sum += a1
    val a2 = 4000
    println(getFee("Мир", sum, a2))
    sum += a2
    val a3 = 26000
    println(getFee("Мир", sum, a3))
    sum += a3
    val a4 = 50000
    println(getFee("Mastercard", sum, a4))
    sum += a4
}

fun getFee(type: String = "VK Pay", sumPrevious: Int = 0, amount: Int): Int {
    val fee: Int
    when (type) {
        "VK Pay" -> {
            fee = 0
        }
        "Visa", "Мир" -> {
            fee = maxOf(35, amount * 75 / 10000)
        }
        "Mastercard", "Maestro" -> {
            val sum = sumPrevious + amount
            val limit = 75000
            if (sum > limit) {
                val feeBase = minOf(sum - limit, amount)
                fee = feeBase * 6 / 1000 + 20
            } else {
                fee = 0
            }
        }
        else -> fee = 0
    }
    return fee
}