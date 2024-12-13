package com.example.sehatyou.utils

import com.example.sehatyou.model.DiaryEntity
import com.example.sehatyou.model.SuggestEntity
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun DiaryEntity.getLocalDate(): LocalDate {
    return LocalDate.parse(this.date, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID")))
}

fun DiaryEntity.getLocalTime(): LocalTime {
    return LocalTime.parse(this.time, DateTimeFormatter.ofPattern("HH:mm", Locale("id", "ID")))
}

fun SuggestEntity.getLocalDate(): LocalDate {
    return LocalDate.parse(this.date, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID")))
}

fun SuggestEntity.getLocalTime(): LocalTime {
    return LocalTime.parse(this.time, DateTimeFormatter.ofPattern("HH:mm", Locale("id", "ID")))
}