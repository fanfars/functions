package ru.netology

import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

    @Test
    fun doubleFormatTypeRubOnly() {
        val valueDouble = 10.0
        val expectedFormat = "10 руб."

        val actualFormat = doubleFormat(valueDouble)

        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun doubleFormatTypeWithKopeyki() {
        val valueDouble = 10.10
        val expectedFormat = "10 руб. 10 коп."

        val actualFormat = doubleFormat(valueDouble)

        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun doubleFormatWhenZero() {
        val valueDouble = 0.0
        val expectedFormat = "0 руб."

        val actualFormat = doubleFormat(valueDouble)

        assertEquals(expectedFormat, actualFormat)
    }

    @Test
    fun limitCheckWithDayCardLimit() {
        val card = "Mastercard"
        val pastExpenses = 10_000
        val transfer = 160_000
        val expectedValue = false

        val actualValue = limitCheck(card, pastExpenses, transfer)

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun limitCheckWithMonthCardLimit() {
        val card = "Mastercard"
        val pastExpenses = 610_000
        val transfer = 10_000
        val expectedValue = false

        val actualValue = limitCheck(card, pastExpenses, transfer)

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun limitCheckVkWithMonthCardLimit() {
        val card = "VK Pay"
        val pastExpenses = 50_000
        val transfer = 10_000
        val expectedValue = false

        val actualValue = limitCheck(card, pastExpenses, transfer)

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun limitCheckVkWithDayCardLimit() {
        val card = "VK Pay"
        val pastExpenses = 20_000
        val transfer = 20_000
        val expectedValue = false

        val actualValue = limitCheck(card, pastExpenses, transfer)

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun limitCheckOk() {
        val card = "VK Pay"
        val pastExpenses = 20_000
        val transfer = 10_000
        val expectedValue = true

        val actualValue = limitCheck(card, pastExpenses, transfer)

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun feeWithMaestroSmallPastExpenses() {
        val card = "Maestro"
        val pastExpenses = 20_000
        val transfer = 10_000
        val expectedFee = 0.0

        val actualFee = fee(card, pastExpenses, transfer)

        assertEquals(expectedFee, actualFee, 0.0)
    }

    @Test
    fun feeWithMaestroBigPastExpenses() {
        val card = "Maestro"
        val pastExpenses = 80_000
        val transfer = 10_000
        val expectedFee = 80.0

        val actualFee = fee(card, pastExpenses, transfer)

        assertEquals(expectedFee, actualFee, 0.0)
    }

    @Test
    fun feeWithVisaMinComission() {
        val card = "Visa"
        val pastExpenses = 80_000
        val transfer = 1_000
        val expectedFee = 35.0

        val actualFee = fee(card, pastExpenses, transfer)

        assertEquals(expectedFee, actualFee, 0.0)
    }

    @Test
    fun feeWithVisaComission() {
        val card = "Visa"
        val pastExpenses = 80_000
        val transfer = 10_000
        val expectedFee = 75.0

        val actualFee = fee(card, pastExpenses, transfer)

        assertEquals(expectedFee, actualFee, 0.0)
    }

    @Test
    fun feeWithVkPayNoComission() {
        val card = "VK Pay"
        val pastExpenses = 80_000
        val transfer = 10_000
        val expectedFee = 0.0

        val actualFee = fee(card, pastExpenses, transfer)

        assertEquals(expectedFee, actualFee, 0.0)
    }

    @Test
    fun feeWithDefault() {
        val transfer = 10_000
        val expectedFee = 0.0

        val actualFee = fee(transfer = transfer)

        assertEquals(expectedFee, actualFee, 0.0)
    }

    @Test
    fun mainPrintlnVisa() {
        val card = "Visa"
        val pastExpenses = 10_000
        val transfer = 10_100
        val expectedMsgString = "Комиссия при переводе $transfer руб. (с учетом прошлых переводов $pastExpenses руб.) через \"Visa\" составит: 75 руб. 75 коп."

        val actualMsgString =
            "Комиссия при переводе $transfer руб. (с учетом прошлых переводов $pastExpenses руб.) через \"Visa\" составит: ${
                doubleFormat(
                    fee(card = card, pastExpenses = pastExpenses, transfer = transfer)
                )
            }"


        assertEquals(expectedMsgString, actualMsgString)
    }

}