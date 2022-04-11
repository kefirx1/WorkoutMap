package pl.dev.workoutmapcompose

import java.time.Instant
import java.time.LocalDateTime
import java.time.Year
import java.time.ZoneId
import java.util.*

object Convert {

    fun convertIntValuesToTimeInSec(
        year: Int,
        month: Int,
        day: Int
    ): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val weighingDateInSec = calendar.timeInMillis/1000

        return weighingDateInSec.toInt()
    }

    fun convertTimeInSecToDateString(
        dateInSec: Int
    ): String {
        val dateOfWeighting = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSec.toLong()), ZoneId.systemDefault())
        val year = dateOfWeighting.year
        val month = dateOfWeighting.monthValue
        var day = dateOfWeighting.dayOfMonth.toString()

        if(day.length == 1){
            day = "0$day"
        }

        return "$day/$month/$year"
    }


}