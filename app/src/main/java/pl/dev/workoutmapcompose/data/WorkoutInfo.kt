package pl.dev.workoutmapcompose.data

data class WorkoutInfo(
    var trainingPlan: TrainingPlan,
    var dateOfWorkout: Int,
    var exercises: ArrayList<Exercise>,
    var exercisesProgress: HashMap<String, ArrayList<Float>>
)