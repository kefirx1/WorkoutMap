package pl.dev.workoutmapcompose.data

data class ProgressHistory(
    var exercisesProgress: HashMap<String, HashMap<Int, ArrayList<String>>>
)
