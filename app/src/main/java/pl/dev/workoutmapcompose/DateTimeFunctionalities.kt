package pl.dev.workoutmapcompose

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateTimeFunctionalities {
    

    fun getCurrentDayToIntValue() =
        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1

    fun convertIntValuesToDateInSec(
        year: Int,
        month: Int,
        day: Int
    ): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val weighingDateInSec = calendar.timeInMillis/1000

        return weighingDateInSec.toInt()
    }

    fun convertTimeInSecToTimeString(
        timeInSec: Int
    ): String {
        val hoursString = checkLengthIsEqualToOne(stringToCheck = (timeInSec / 3600).toString())
        val minutesString = checkLengthIsEqualToOne(stringToCheck = ((timeInSec % 3600 ) / 60).toString())
        val secondsString = checkLengthIsEqualToOne(stringToCheck = (timeInSec % 60).toString())

        return "$hoursString:$minutesString:$secondsString"

    }

    fun convertDateInSecToDateString(
        dateInSec: Int
    ): String {
        val dateOfWeighting = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSec.toLong()), ZoneId.systemDefault())
        val year = dateOfWeighting.year
        val month = dateOfWeighting.monthValue
        val day = checkLengthIsEqualToOne(stringToCheck = dateOfWeighting.dayOfMonth.toString())
        
        return "$day/$month/$year"
    }

    fun convertTimeInSecToDateStringShortened(
        dateInSec: Int
    ): String {
        val dateOfWeighting = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSec.toLong()), ZoneId.systemDefault())
        val month = dateOfWeighting.monthValue
        val day = checkLengthIsEqualToOne(stringToCheck = dateOfWeighting.dayOfMonth.toString())

        return "$day/$month"
    }

    private fun checkLengthIsEqualToOne(
        stringToCheck: String
    ): String {
        return if(stringToCheck.length == 1){
            "0$stringToCheck"
        }else{
            stringToCheck
        }
    }
    
}