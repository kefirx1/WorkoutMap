package pl.dev.workoutmapcompose.data.tempData

import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan

class WorkoutInfoTemp {

    lateinit var trainingPlan: TrainingPlan
    var dateOfWorkout: Int = 0
    lateinit var exercises: ArrayList<Exercise>
    lateinit var exercisesProgress: HashMap<String, ArrayList<Float>>


}