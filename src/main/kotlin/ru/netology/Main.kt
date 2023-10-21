package ru.netology

fun main(){
    subMain()
}

fun subMain(a1: Int = 10000, a2: Int = 4000, a3: Int = 26000,
         a4: Int = 6666, a5: Int = 50000, a6: Int = 14000,
            a7: Int = 14000, type: String = "Какая-то система") {
    var sumVKPayDaily = 0
    var sumVKPayMonthly = 0
    var sumMir = 0
    var sumMasterCard = 0
    var sumReceived = 0
//    val a1 = 10000
    if (send(amount = a1)) {
        sumVKPayDaily += a1
        sumVKPayMonthly += a1
        sumReceived += a1
    }
//    val a2 = 4000
    if (send("Мир", sumMir, sumMir, sumReceived, a2)) {
        sumMir += a2
        sumReceived += a2
    }
//    val a3 = 26000
    if (send("Мир", sumMir, sumMir, sumReceived, a3)) {
        sumMir += a3
        sumReceived += a3
    }
//    val a4 = 6666
    if (send(type, sumMir, sumMir, sumReceived, a4)) {
        sumMir += a4
        sumReceived += a4
    }
//    val a5 = 50000
    if (send("Mastercard", sumMasterCard, sumMasterCard, sumReceived, a5)) {
        sumMasterCard += a5
        sumReceived += a5
    }
//    val a6 = 14000
    if (send("VK Pay", sumVKPayDaily, sumVKPayMonthly, sumReceived, a6)) {
        sumVKPayDaily += a6
        sumVKPayMonthly += a6
        sumReceived += a6
    }
    sumVKPayDaily = 0
    if (send("VK Pay", sumVKPayDaily, sumVKPayMonthly, sumReceived, a7)) {
        sumVKPayDaily += a7
        sumVKPayMonthly += a7
        sumReceived += a7
    }
}

fun getFee(type: String = "VK Pay", sumPrevious: Int = 0, amount: Int): Int {
    val fee = when (type) {
        "VK Pay" -> 0
        "Visa", "Мир" -> maxOf(35, amount * 75 / 10000)
        "Mastercard", "Maestro" -> {
            val sum = sumPrevious + amount
            val limit = 75000
            if (sum > limit) {
                val feeBase = minOf(sum - limit, amount)
                feeBase * 6 / 1000 + 20
            } else {
                0
            }
        }
        else -> -1
    }
    return fee
}

fun isNotOutOfLimit(
    type: String = "VK Pay",
    sumDailySent: Int = 0,
    sumDailyReceived: Int = 0,
    sumMonthlySent: Int = 0,
    sumMonthlyReceived: Int = 0,
    amount: Int
): Boolean {
    return when (type) {
        "VK Pay" -> {
            val limitDaily = 15000
            val limitMonthly = 40000
            amount <= limitDaily - sumDailySent &&
                    amount <= limitMonthly - sumMonthlySent
        }
        "Visa", "Mastercard", "Мир", "Maestro" -> {
            val limitDaily = 150000
            val limitMonthly = 600000
            amount <= limitDaily - sumDailySent && amount <= limitMonthly - sumMonthlySent &&
                    amount <= limitDaily - sumDailyReceived && amount <= limitMonthly - sumMonthlyReceived
        }
        else -> false
    }
}

fun send(type: String = "VK Pay", sumDaily: Int = 0, sumMonthly: Int = 0, sumReceived: Int = 0, amount: Int): Boolean {
    return if (isNotOutOfLimit(type, sumDailySent = sumDaily, sumMonthlySent = sumMonthly, amount = amount)) {
        val fee = getFee(type, sumReceived, amount)
        println("Перевод на сумму $amount успешно отправлен. Комиссия $fee руб.")
        true
    } else {
        println("Перевод на сумму $amount не отправлен.")
        false
    }
}

