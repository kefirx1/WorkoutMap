package pl.dev.workoutmapcompose.datbase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DataSnapshot

object FirebaseListener {


    var firebaseInfoResult: MutableState<DataSnapshot?> = mutableStateOf(null)
}