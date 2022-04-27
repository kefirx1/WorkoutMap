package pl.dev.workoutmapcompose

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