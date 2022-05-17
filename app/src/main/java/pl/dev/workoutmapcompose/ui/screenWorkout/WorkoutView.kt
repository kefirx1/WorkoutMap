package pl.dev.workoutmapcompose.ui.screenWorkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pl.dev.workoutmapcompose.Convert
import pl.dev.workoutmapcompose.WorkoutActivity
import pl.dev.workoutmapcompose.data.ProgressHistory
import pl.dev.workoutmapcompose.data.WorkoutHistory
import pl.dev.workoutmapcompose.ui.components.HeaderComponent
import pl.dev.workoutmapcompose.ui.components.WorkoutHeader
import pl.dev.workoutmapcompose.ui.theme.Purple500
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@Composable
fun MainWorkout(
    instance: WorkoutActivity,
    viewModel: WorkoutViewModel,
    trainingPlanIndex: Int
){

    viewModel.getTrainingPlansList()
    viewModel.getWorkoutHistory()

    val trainingPlan = viewModel.trainingPlansListResult.value!![trainingPlanIndex]
    val workoutHistory = viewModel.workoutHistoryResult.value!![trainingPlanIndex]
    viewModel.getExercisesProgressHistory(trainingPlan = trainingPlan)
    val progressHistory = viewModel.progressHistoryResult.value

    val timestampInt by remember {
        mutableStateOf((Calendar.getInstance().timeInMillis/1000).toInt())
    }

    val exerciseNewProgress: MutableMap<String, HashMap<String, ArrayList<String>>> by remember {
        mutableStateOf(HashMap())
    }


    var currentExerciseIndex by remember {
        mutableStateOf(0)
    }
    var currentExerciseSet by remember {
        mutableStateOf(1)
    }
    var timerTicks by remember {
        mutableStateOf(0)
    }
    var weightTextState by remember {
        mutableStateOf(TextFieldValue("0"))
    }
    var stepButtonText by remember {
        mutableStateOf("")
    }
    var repsTextState by remember {
        mutableStateOf(TextFieldValue("0"))
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            timerTicks++
        }
    }

    stepButtonText = if(currentExerciseSet==trainingPlan.exercise[currentExerciseIndex].numberOfSets){
        if(currentExerciseIndex==trainingPlan.exercise.size-1){
            "Zapisz ostatnią serie"
        }else{
            "Następne ćwiczenie"
        }
    } else {
        "Następna seria"
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WorkoutHeader(
            screenName = "TRENING - ${trainingPlan.planName}",
            instance = instance,
            viewModel = viewModel
        )

        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = "Czas treningu: ${Convert.convertTimeInSecToTimeString(timeInSec = timerTicks)}",
            style = MaterialTheme.typography.caption,
            fontSize = 20.sp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(top = 10.dp)
                    .border(width = 1.dp, color = Color.Black)
                ) {

                Text(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    text = Convert.convertExerciseNameToBetterView(trainingPlan.exercise[currentExerciseIndex].name),
                    style = MaterialTheme.typography.caption,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = trainingPlan.exercise[currentExerciseIndex].type,
                    style = MaterialTheme.typography.caption,
                    fontSize = 15.sp
                )
            }


            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "$currentExerciseSet seria z ${trainingPlan.exercise[currentExerciseIndex].numberOfSets}",
                style = MaterialTheme.typography.caption,
                fontSize = 15.sp
            )

            //TODO

            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                OutlinedTextField(
                    modifier = Modifier
                        .width(120.dp),
                    value = repsTextState,
                    onValueChange = { repsTextState = it },
                    textStyle = TextStyle(
                        color = MaterialTheme.typography.caption.color,
                        fontFamily = mainFamily,
                        fontSize = 20.sp
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Powtorzenia",
                            color = MaterialTheme.typography.caption.color,
                            fontFamily = mainFamily,
                            fontSize = 15.sp
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        focusedIndicatorColor = Purple500,
                        cursorColor = Purple500
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .width(120.dp),
                    value = weightTextState,
                    onValueChange = { weightTextState = it },
                    textStyle = TextStyle(
                        color = MaterialTheme.typography.caption.color,
                        fontFamily = mainFamily,
                        fontSize = 20.sp
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            text = "Obciazenie",
                            color = MaterialTheme.typography.caption.color,
                            fontFamily = mainFamily,
                            fontSize = 15.sp
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        focusedIndicatorColor = Purple500,
                        cursorColor = Purple500
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    )
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {

                    if(weightTextState.text.isNotBlank() && repsTextState.text.isNotBlank()){

                        if(exerciseNewProgress[trainingPlan.exercise[currentExerciseIndex].name]==null){
                            exerciseNewProgress[trainingPlan.exercise[currentExerciseIndex].name] = hashMapOf(timestampInt.toString() to arrayListOf("${repsTextState.text}x${weightTextState.text}"))
                        }else {
                            val oldMap = exerciseNewProgress[trainingPlan.exercise[currentExerciseIndex].name]!!
                            val oldArrayList = oldMap[timestampInt.toString()]!!
                            oldArrayList.add("${repsTextState.text}x${weightTextState.text}")
                            oldMap[timestampInt.toString()] = oldArrayList
                            exerciseNewProgress[trainingPlan.exercise[currentExerciseIndex].name] = oldMap
                        }

                        if(currentExerciseSet==trainingPlan.exercise[currentExerciseIndex].numberOfSets){
                            if (currentExerciseIndex<trainingPlan.exercise.size-1){
                                //Next exercise
                                currentExerciseIndex++
                                currentExerciseSet = 1
                            }else {
                                //Sum

                                endWorkout(
                                    workoutHistory = workoutHistory,
                                    viewModel = viewModel,
                                    exerciseNewProgress = exerciseNewProgress,
                                    trainingPlanIndex = trainingPlanIndex,
                                    timestampInt = timestampInt
                                )

                                //TODO
                                instance.finish()

                            }
                        }else{
                            //Next set
                            currentExerciseSet++
                        }

                        weightTextState = TextFieldValue("0")
                        repsTextState = TextFieldValue("0")

                    }else{
                        Toast.makeText(
                            instance,
                            "Podaj właściwe wartości",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                          },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
            ) {
                Text(
                    text = stepButtonText,
                    color = MaterialTheme.typography.caption.color,
                    fontSize = 30.sp
                )
            }

        }

    }

}

fun endWorkout(workoutHistory: WorkoutHistory, viewModel: WorkoutViewModel, trainingPlanIndex: Int, exerciseNewProgress: MutableMap<String, HashMap<String, ArrayList<String>>>, timestampInt: Int){

    workoutHistory.dateOfWorkout.add(timestampInt)

    val newProgressHistory = ProgressHistory(
        exercisesProgress = exerciseNewProgress
    )

    viewModel.insertNewTrainingInfo(
        workoutHistory = workoutHistory,
        workoutIndex = trainingPlanIndex,
        newProgressHistory = newProgressHistory,
        timestampInt = timestampInt
    )

}