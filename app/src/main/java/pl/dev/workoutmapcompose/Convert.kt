package pl.dev.workoutmapcompose

import java.time.Year
import java.util.*

object Convert {

    fun convertIntValuesToTimeInMillis(
        year: Int,
        month: Int,
        day: Int
    ): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val weighingDateInSec = calendar.timeInMillis/1000

        return weighingDateInSec.toInt()
    }


}