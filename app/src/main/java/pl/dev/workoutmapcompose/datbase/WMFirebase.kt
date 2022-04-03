package pl.dev.workoutmapcompose.datbase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object WMFirebase {

    fun getReference(userName: String): DatabaseReference {
        val database = FirebaseDatabase.getInstance()
        return database.getReference(userName)
    }

}