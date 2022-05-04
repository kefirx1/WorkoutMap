package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.AddNewTrainingPlanActivity
import pl.dev.workoutmapcompose.Convert
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.components.HeaderComponent
import pl.dev.workoutmapcompose.ui.theme.Purple500
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import kotlin.streams.toList


@Composable
fun MainNewTrainingView(
    instance: AddNewTrainingPlanActivity,
    viewModel: AddNewTrainingPlanViewModel
) {

    viewModel.getExercisesJSON(instance.applicationContext)
    viewModel.getTrainingPlansList()

    val listOfTrainingPlansNames = viewModel.trainingPlansListResult.value!!.stream().map {
        it.planName
    }.toList()


    var planNameTextState by remember {
        mutableStateOf(TextFieldValue())
    }

    val selectedExercisesList = remember {
        mutableStateListOf(Exercise("", "",0))
    }
    var openAddExerciseDialog by remember {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }
    val daysList = listOf("-", "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela")
    var daySelected by remember {
        mutableStateOf("-")
    }

    if(openAddExerciseDialog) {
        openAddExerciseDialog = DialogAlerts.addExerciseForTrainingPlanDialogAlert(
            instance = instance,
            viewModel = viewModel,
            selectedExercisesList
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderComponent(
            screenName = "NOWY PLAN",
            instance = instance
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = planNameTextState,
                onValueChange = { planNameTextState = it },
                textStyle = TextStyle(
                    color = MaterialTheme.typography.caption.color,
                    fontFamily = mainFamily,
                    fontSize = 20.sp
                ),
                singleLine = true,
                label = {
                    Text(
                        text = "Nazwa planu",
                        color = MaterialTheme.typography.caption.color,
                        fontFamily = mainFamily,
                        fontSize = 15.sp
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    focusedIndicatorColor = Purple500,
                    cursorColor = Purple500
                )
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(top = 10.dp)
            ) {


                Text(
                    text = "Dzień treningu: $daySelected",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.caption
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = MaterialTheme.typography.caption.color
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    daysList.forEach {
                        DropdownMenuItem(
                            onClick = {
                                daySelected = it
                                expanded = false
                            }
                        ) {
                            Text(
                                text = it
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
            )

            if(selectedExercisesList.size>1){

                LazyColumn {
                    items(count = selectedExercisesList.size) {
                        if(it!=0){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(ambientColor = Color.Black, elevation = 4.dp)
                                    .background(
                                        color = MaterialTheme.colors.surface
                                    )
                                    .padding(10.dp)
                                    .clickable {
                                        selectedExercisesList.removeAt(it)
                                    },
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f),
                                    text = "${selectedExercisesList[it].type} - ${Convert.convertExerciseNameToBetterView(selectedExercisesList[it].name)} (${selectedExercisesList[it].numberOfSets} serii)",
                                    style = MaterialTheme.typography.caption
                                )

                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "delete",
                                    tint = MaterialTheme.typography.caption.color
                                )

                            }

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                            )
                        }
                    }
                    item{
                        IconButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                openAddExerciseDialog = true
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Training",
                                tint = MaterialTheme.typography.caption.color,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    }
                }

            }else {
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        openAddExerciseDialog = true
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Training",
                        tint = MaterialTheme.typography.caption.color,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }


        }

        Button(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth(0.5f)
                .padding(end = 4.dp)
                .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
            onClick = {

                if(!listOfTrainingPlansNames.contains(planNameTextState.text)){

                    if(planNameTextState.text.isNotBlank() && selectedExercisesList.size>1 ){

                        selectedExercisesList.removeAt(0)

                        val newTrainingPlan = TrainingPlan(
                            planName = planNameTextState.text,
                            exercise = selectedExercisesList,
                            assignedDay = daysList.indexOf(daySelected)
                        )
                        viewModel.addNewTrainingPlan(trainingPlan = newTrainingPlan)
                        instance.finish()
                    }else{
                        Toast.makeText(
                            instance,
                            "Wprowadź odpowiednie dane",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else {
                    Toast.makeText(
                        instance,
                        "Nazwa treningu jest już zajęta",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
        ) {
            Text(
                text = "DODAJ NOWY PLAN",
                style = MaterialTheme.typography.caption,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }


    }
}

