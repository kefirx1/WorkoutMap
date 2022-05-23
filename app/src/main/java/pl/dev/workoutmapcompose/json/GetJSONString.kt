package pl.dev.workoutmapcompose.json

import pl.dev.workoutmapcompose.App.Companion.applicationContext
import java.io.IOException

class GetJSONString {

    fun getJsonStringFromAssets(jsonFileName: String): String?{

        return try{
            applicationContext().assets.open(jsonFileName).bufferedReader().use { it.readText() }
        }catch (ioException: IOException){
            ioException.printStackTrace()
            null
        }

    }

}