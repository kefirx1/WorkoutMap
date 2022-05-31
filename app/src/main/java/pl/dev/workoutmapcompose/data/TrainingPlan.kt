package pl.dev.workoutmapcompose.data

data class TrainingPlan (
    var planName: String = "",
    var exercise: MutableList<Exercise> = mutableListOf(),
    var assignedDay: Int = 0
    )