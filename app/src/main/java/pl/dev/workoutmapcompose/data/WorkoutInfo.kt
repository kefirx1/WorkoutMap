package pl.dev.workoutmapcompose.data

data class WorkoutInfo(
    var dateOfWorkout: Int,
    var exercisesProgress: ArrayList<ArrayList<ArrayList<String>>>
)