package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.ui.theme.Purple500
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import pl.dev.workoutmapcompose.ui.utils.HeaderComponent
import pl.dev.workoutmapcompose.ui.utils.TextModifier
import pl.dev.workoutmapcompose.ui.utils.addExerciseForTrainingPlanDialogAlert
import pl.dev.workoutmapcompose.ui.utils.showShortToast
import kotlin.streams.toList

@Suppress("FunctionName")
@Composable
fun MainNewTrainingView(
    instance: AddNewTrainingPlanActivity,
    viewModel: AddNewTrainingPlanViewModel
) {

    viewModel.getExercisesJSON()
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
    val daysList = listOf("-", "poniedzia??ek", "wtorek", "??roda", "czwartek", "pi??tek", "sobota", "niedziela")
    var daySelected by remember {
        mutableStateOf("-")
    }

    val focusManager = LocalFocusManager.current

    if(openAddExerciseDialog) {
        openAddExerciseDialog = addExerciseForTrainingPlanDialogAlert(
            viewModel = viewModel,
            selectedExercisesList = selectedExercisesList
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() } // This is mandatory
            ) {
                focusManager.clearFocus()
            }
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

            Column(
                modifier = Modifier
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "Dzie?? treningu:",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.caption
                )

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = daySelected,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.caption
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown",
                        tint = MaterialTheme.typography.caption.color
                    )

                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxHeight(0.3f),
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
                            Divider()
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
                                    .clickable {
                                        selectedExercisesList.removeAt(it)
                                    }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(0.7f),
                                    text = "${selectedExercisesList[it].type} - ${TextModifier.convertExerciseNameToBetterText(selectedExercisesList[it].name)} (${selectedExercisesList[it].numberOfSets} serii)",
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
                        showShortToast(
                            text = "Wprowad?? odpowiednie dane"
                        )
                    }
                }else {
                    showShortToast(
                        text = "Nazwa treningu jest ju?? zaj??ta"
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
        ) {
            Text(
                text = "Dodaj nowy plan",
                style = MaterialTheme.typography.caption,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }


    }
}

