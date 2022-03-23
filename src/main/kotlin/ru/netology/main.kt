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
    if (rub == 0) return "$rub руб."
    val kop = ((valueDouble * 100.0).toInt()) % (rub * 100)
    return if (kop == 0) "$rub руб." else "$rub руб. $kop коп."
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

fun fee(card: String = "VK Pay", pastExpenses: Int = 0, transfer: Int): Double {
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
        return comission
    } else return 0.0
}

fun main() {
    val pastExpenses = 10_000
    val transfer = 10_100

    println(
        "Комиссия при переводе $transfer руб. (с учетом прошлых переводов $pastExpenses руб.) через \"Mastercard\" составит: ${
            doubleFormat(
                fee(card = "Mastercard", pastExpenses = pastExpenses, transfer = transfer)
            )
        }"
    )

    println(
        "Комиссия при переводе $transfer руб. (с учетом прошлых переводов $pastExpenses руб.) через \"Visa\" составит: ${
            doubleFormat(
                fee(card = "Visa", pastExpenses = pastExpenses, transfer = transfer)
            )
        }"
    )

    println(
        "Комиссия при переводе $transfer руб. (с учетом прошлых переводов $pastExpenses руб.) через \"VK Pay\" составит: ${
            doubleFormat(
                fee(card = "VK Pay", pastExpenses = pastExpenses, transfer = transfer)
            )
        }"
    )
}
