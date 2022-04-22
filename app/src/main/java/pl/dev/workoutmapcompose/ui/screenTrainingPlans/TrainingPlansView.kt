package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import pl.dev.workoutmapcompose.TrainingPlansActivity
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily


fun exitSettings(
    instance: TrainingPlansActivity
){
    //TODO
    instance.finish()
}


@Composable
fun MainTrainingPlansView(
    instance: TrainingPlansActivity,
    viewModel: TrainingPlansViewModel
) {

    viewModel.getTrainingPlansList()

    val trainingPlanClicked by remember {
        mutableStateOf(TrainingPlan("", listOf()))
    }

    var openTrainingPlansInfoDialog by remember {
        mutableStateOf(false)
    }

    if(openTrainingPlansInfoDialog) {
        openTrainingPlansInfoDialog = DialogAlerts.trainingPlansInfoDialogAlert(
            instance = instance,
            viewModel = viewModel,
            trainingPlan = trainingPlanClicked
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
                text = "PLANY TRENINGOWE",
                color = Color.White,
                fontFamily = mainFamily,
                fontSize = 30.sp
            )
        }

        Spacer(
            modifier = Modifier
                .background(color = Color.Black)
                .height(2.dp)
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )


        
        if(!viewModel.trainingPlansListResult.value.isNullOrEmpty()) {

            Spacer(
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 6.dp)
                    .background(color = Color.Black)
                    .height(1.dp)
                    .fillMaxWidth(0.6f)
            )

            val trainingPlansList = viewModel.trainingPlansListResult.value!!

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.8f)
            ){
                items(count = trainingPlansList.size) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = BlueGray800)
                            .clickable {
                                trainingPlanClicked.planName = trainingPlansList[it].planName
                                trainingPlanClicked.exercise = trainingPlansList[it].exercise
                                openTrainingPlansInfoDialog = true
                            },
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = trainingPlansList[it].planName,
                            color = BlueGray50,
                            fontFamily = mainFamily,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(5.dp)
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .padding(top = 6.dp, bottom = 6.dp)
                            .background(color = Color.Black)
                            .height(1.dp)
                            .fillMaxWidth()
                    )
                }
            }

            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    val intent = Intent(instance, AddNewTrainingPlanActivity::class.java)
                    instance.startActivity(intent)
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



        }else{

            IconButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                onClick = {
                    val intent = Intent(instance, AddNewTrainingPlanActivity::class.java)
                    instance.startActivity(intent)
                }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Training",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize(0.2f)
                )
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp),
                text = "Nie dodałeś jeszcze planów treningowych",
                color = BlueGray50,
                fontFamily = mainFamily,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 6.dp)
                    .background(color = Color.Black)
                    .height(1.dp)
                    .fillMaxWidth()
            )

        }






    }

}