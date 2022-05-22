package pl.dev.workoutmapcompose.json

import android.content.Context
import java.io.IOException

class GetJSONString {

    fun getJsonStringFromAssets(context: Context, jsonFileName: String): String?{

        return try{
            context.assets.open(jsonFileName).bufferedReader().use { it.readText() }
        }catch (ioException: IOException){
            ioException.printStackTrace()
            null
        }

    }

}