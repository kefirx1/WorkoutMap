package pl.dev.workoutmapcompose.data

import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan

data class WorkoutInfo(
    var trainingPlan: TrainingPlan,
    var dateOfWorkout: Long,
    var exercises: ArrayList<Exercise>,
    var exercisesProgress: HashMap<String, ArrayList<Float>>
)