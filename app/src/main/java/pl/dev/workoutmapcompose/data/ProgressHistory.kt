package pl.dev.workoutmapcompose.data

data class ProgressHistory(
    var exercisesProgress: Map<String, Map<String, ArrayList<String>>>
)
