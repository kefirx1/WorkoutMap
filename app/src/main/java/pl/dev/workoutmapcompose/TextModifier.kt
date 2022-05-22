package pl.dev.workoutmapcompose

object TextModifier {

    fun convertExerciseNameToBetterText(
        planName: String
    ): String {

        val planNameList =  planName.toMutableList()
        val convertedStringBuilder = StringBuilder("${planNameList[0].uppercaseChar()}")

        for(i in 1 until planNameList.size){
            if(planNameList[i].isUpperCase()){
                convertedStringBuilder.append(" ${planNameList[i].lowercaseChar()}")
                continue
            }
            convertedStringBuilder.append(planNameList[i])
        }
        return convertedStringBuilder.toString()
    }




}