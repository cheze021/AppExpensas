package utils

import kotlinx.datetime.LocalDate

fun formatDate(date: LocalDate): String {
    val months = listOf(
        "ene.", "feb.", "mar.", "abr.", "may.", "jun.",
        "jul.", "ago.", "sep.", "oct.", "nov.", "dic."
    )
    val day = date.dayOfMonth
    val month = months[date.monthNumber - 1]
    val year = date.year
    return "$day $month $year"
}