package pl.dev.workoutmapcompose.data

data class ProgressHistory(
    var exercisesProgress: MutableMap<String, HashMap<String, ArrayList<String>>>
)
