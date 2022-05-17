package pl.dev.workoutmapcompose

import android.icu.text.StringPrepParseException
import androidx.core.util.rangeTo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object Convert {

    fun convertExerciseNameToBetterView(
        planName: String
    ): String {

        val planNameList =  planName.toMutableList()
        val convertedStringBuilder = StringBuilder("")

        for(i in 0 until planNameList.size){

            if(i==0){
                convertedStringBuilder.append(planNameList[i].uppercaseChar())
                continue
            }
            if(planNameList[i].isUpperCase()){
                convertedStringBuilder.append(" ${planNameList[i].lowercaseChar()}")
                continue
            }
            convertedStringBuilder.append(planNameList[i])

        }

        return convertedStringBuilder.toString()

    }

    fun convertCurrentDayToIntValue(): Int {

        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_WEEK)-1
    }


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
        var hoursString = (timeInSec / 3600).toString()
        var minutesString = ((timeInSec % 3600 ) / 60).toString()
        var secondsString = (timeInSec % 60).toString()

        if(hoursString.length == 1){
            hoursString = "0$hoursString"
        }
        if(minutesString.length == 1){
            minutesString = "0$minutesString"
        }
        if(secondsString.length == 1){
            secondsString = "0$secondsString"
        }

        return "$hoursString:$minutesString:$secondsString"

    }

    fun convertDateInSecToDateString(
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

    fun convertTimeInSecToDateStringShortened(
        dateInSec: Int
    ): String {
        val dateOfWeighting = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSec.toLong()), ZoneId.systemDefault())
        val month = dateOfWeighting.monthValue
        var day = dateOfWeighting.dayOfMonth.toString()

        if(day.length == 1){
            day = "0$day"
        }

        return "$day/$month"
    }


}