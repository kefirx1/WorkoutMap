package pl.dev.workoutmapcompose.data

data class ExerciseProgress(
    val dateOfWorkout: String,
    val setsList: MutableList<String>
)
