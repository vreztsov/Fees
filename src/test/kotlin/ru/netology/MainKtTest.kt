package ru.netology

import org.junit.Assert.*
import org.junit.Test

class MainKtTest {

    val vkPayLimitDaily = 15000
    val vkPayLimitMonthly = 40000
    val amount = 140000
    val limitDaily = 150000
    val limitMonthly = 600000


    @Test
    fun isSendingOK() {
        for (type in arrayOf("Visa", "Mastercard", "Мир", "Maestro")) {
            var sumMonthly = 0
            var sumDaily = 0
            while (true) {
                when {
                    sumDaily + amount > limitDaily -> {
                        assertFalse(send(type, sumDaily, sumMonthly, sumMonthly, amount))
                        sumDaily = 0
                    }
                    sumMonthly + amount > limitMonthly -> {
                        assertFalse(send(type, sumDaily, sumMonthly, sumMonthly, amount))
                        break
                    }
                    else -> {
                        assertTrue(send(type, sumDaily, sumMonthly, sumMonthly, amount))
                        assertTrue(send(type, sumDaily = sumDaily, amount = amount))
                        assertTrue(send(type, sumMonthly = sumMonthly, amount = amount))
                        assertTrue(send(type, sumReceived = sumMonthly, amount = amount))
                        sumDaily += amount
                        sumMonthly += amount
                    }
                }
            }
        }
        assertTrue(send(amount = vkPayLimitDaily))
        assertFalse(send(amount = vkPayLimitMonthly))
    }

    @Test
    fun isFeeOK() {
        val a1 = 1000
        val a2 = 10000
        val sumPrevious = 64000
        assertEquals(0, getFee("VK Pay", 0, a1))
        assertEquals(35, getFee("Visa", 0, a1))
        assertEquals(35, getFee("Мир", 0, a1))
        assertEquals(0, getFee("Mastercard", sumPrevious, a2))
        assertEquals(0, getFee("Maestro", sumPrevious, a2))
        assertEquals(74, getFee("Mastercard", sumPrevious + a2, a2))
        assertEquals(-1, getFee("!Maestro", 0, a2))
        assertEquals(0, getFee(amount = a2))
        assertEquals(0, getFee(sumPrevious = sumPrevious, amount = a2))
        assertEquals(0, getFee("Maestro", amount = a2))
    }

    @Test
    fun testMain() {
        subMain()
        subMain(17000, 200000, 200000, 5000, 200000, 14000, 17000, "Мир")
    }

    @Test
    fun isDefaultLimitOK() {
        val a1 = 10000
        val a2 = 20000
        val l1 = 15000
        val l2 = 35000
        assertTrue(isNotOutOfLimit(amount = a1))
        assertFalse(isNotOutOfLimit(amount = a2))
        assertFalse(isNotOutOfLimit(sumDailySent = l1, amount = a1))
        assertFalse(isNotOutOfLimit(sumMonthlySent = l2, amount = a1))
    }

    @Test
    fun isLimitOK() {
        val a1 = 50000
        val a2 = 120000
        val l1 = 50000
        val l2 = 550000
        for (type in arrayOf("Visa", "Mastercard", "Мир", "Maestro")) {
            assertTrue(isNotOutOfLimit(type, sumDailySent = l1, sumDailyReceived = l1, amount = a1))
            assertTrue(isNotOutOfLimit(type, sumMonthlySent = l2, sumMonthlyReceived = l2, amount = a1))
            assertFalse(isNotOutOfLimit(type, sumDailySent = l1, amount = a2))
            assertFalse(isNotOutOfLimit(type, sumMonthlySent = l2, amount = a2))
            assertFalse(isNotOutOfLimit(type, sumDailyReceived = l1, amount = a2))
            assertFalse(isNotOutOfLimit(type, sumMonthlyReceived = l2, amount = a2))
        }
        assertFalse(isNotOutOfLimit("", amount = a1))
    }

}