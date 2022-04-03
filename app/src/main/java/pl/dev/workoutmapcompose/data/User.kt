package pl.dev.workoutmapcompose.data

data class User (
    var name: String,
    var surName: String,
    var height: String,
    var gender: String,
    var age: String,
    var monday: String,
    var tuesday: String,
    var wednesday: String,
    var thursday: String,
    var friday: String,
    var saturday: String,
    var sunday: String,
    var trainingPlans: ArrayList<TrainingPlan>,
    var weightHistory: ArrayList<Float>,
    var workoutHistory: ArrayList<WorkoutInfo>)