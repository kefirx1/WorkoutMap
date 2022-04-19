package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.AddNewTrainingPlanActivity
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily


fun exitSettings(
    instance: AddNewTrainingPlanActivity
){
    //TODO
    instance.finish()
}

@Composable
fun MainNewTrainingView(
    instance: AddNewTrainingPlanActivity,
    viewModel: AddNewTrainingPlanViewModel
) {

    viewModel.getExercisesJSON(instance.applicationContext)


    val selectedExercisesList = remember {
        mutableStateListOf(Exercise("", "",0))
    }
    var openAddExerciseDialog by remember {
        mutableStateOf(false)
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
            .background(BlueGray900)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    exitSettings(instance)
                }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back button",
                    tint = Color.White
                )
            }
            Text(
                text = "NOWY PLAN",
                color = Color.White,
                fontFamily = mainFamily,
                fontSize = 30.sp
            )
        }

        Spacer(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .background(color = Color.Black)
                .height(2.dp)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {

            if(selectedExercisesList.size>1){

                LazyColumn {
                    items(count = selectedExercisesList.size) {
                        if(it!=0){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black,
                                        shape = CircleShape
                                    )
                                    .background(
                                        color = BlueGray800,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedExercisesList.removeAt(it)
                                    }
                                    .padding(5.dp)
                            ){

                                Text(
                                    text = selectedExercisesList[it].type
                                )

                                Text(
                                    text = selectedExercisesList[it].name
                                )

                                Text(
                                    text = selectedExercisesList[it].numberOfSets.toString()
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .height(2.dp)
                                    .fillMaxWidth()
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
                                tint = Color.White,
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
                        tint = Color.White,
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
                //TODO
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BlueGray800
            ),
        ) {
            Text(
                text = "DODAJ",
                color = BlueGray50,
                fontFamily = mainFamily,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }









    }
}

