package pl.dev.workoutmapcompose.data

data class ProgressHistory(
    var exercisesProgress: MutableMap<String, MutableMap<String, MutableList<String>>> = mutableMapOf()
)
