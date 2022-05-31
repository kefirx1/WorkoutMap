package pl.dev.workoutmapcompose.data

data class WorkoutHistory(
    var dateOfWorkout: MutableList<Int> = mutableListOf(),
    var planName: String = ""
)