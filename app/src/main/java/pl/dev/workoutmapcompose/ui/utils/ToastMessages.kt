package pl.dev.workoutmapcompose.ui.utils

import android.widget.Toast
import pl.dev.workoutmapcompose.App

fun showShortToast(text: String) {
    Toast.makeText(
        App.applicationContext(),
        text,
        Toast.LENGTH_SHORT
    ).show()
}