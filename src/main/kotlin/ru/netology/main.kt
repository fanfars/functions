package ru.netology

const val dayCardLimit = 150_000
const val monthCardLimit = 600_000
const val vkDayCardLimit = 15_000
const val vkMonthCardLimit = 40_000
const val maestroMonthCardLimit = 75_000
const val maestroCommissionPercent = 0.006
const val maestroMinCommission = 20
const val visaCommissionPercent = 0.0075
const val visaMinCommission = 35

fun doubleFormat(valueDouble: Double): String {
    val rub = valueDouble.toInt()
    val kopeyki = (valueDouble % rub * 100).toInt()
    return if (kopeyki == 0) "$rub руб." else "$rub руб. $kopeyki коп."
}

fun limitCheck(card: String, pastExpenses: Int, transfer: Int): Boolean {
    return when {
        transfer > dayCardLimit -> {
            print("Превышен допустимый лимит переводов в сутки. ")
            false
        }
        pastExpenses > monthCardLimit -> {
            print("Превышен допустимый лимит переводов в месяц. ")
            false
        }
        transfer > vkDayCardLimit && card == "VK Pay" -> {
            print("Превышен допустимый лимит переводов для VK Pay в сутки. ")
            false
        }
        pastExpenses > vkMonthCardLimit && card == "VK Pay" -> {
            print("Превышен допустимый лимит переводов для VK Pay в месяц. ")
            false
        }
        else -> return true
    }
}

fun fee(card: String = "VK Pay", pastExpenses: Int = 0, transfer: Int): String {
    val isAvailable = limitCheck(card, pastExpenses, transfer)
    var comission = 0.0
    if (isAvailable) {
        when (card) {
            "Mastercard", "Maestro" -> {
                comission =
                    if (pastExpenses < maestroMonthCardLimit) 0.0 else transfer.toDouble() * maestroCommissionPercent + maestroMinCommission.toDouble()
            }
            "Visa", "Мир" -> {
                comission =
                    if (transfer.toDouble() * visaCommissionPercent < visaMinCommission.toDouble()) visaMinCommission.toDouble() else transfer.toDouble() * visaCommissionPercent
            }
            "VK Pay" -> {
                comission = 0.0
            }
        }
        return "Комиссия при переводе $transfer руб. (с учетом прошлых переводов $pastExpenses руб.) через $card составит: ${
            doubleFormat(
                comission
            )
        }"
    } else return "Перевод не может быть осуществлен"

}

fun main() {
    println(fee(card = "Mastercard", pastExpenses = 80_000, transfer = 10_100))
    println(fee(card = "Visa", pastExpenses = 10_000, transfer = 10_100))
    println(fee(card = "VK Pay", pastExpenses = 100_000_000, transfer = 10_100))
}