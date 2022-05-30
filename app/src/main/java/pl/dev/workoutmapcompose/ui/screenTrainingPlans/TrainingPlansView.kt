package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanActivity
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.ui.utils.HeaderComponent
import pl.dev.workoutmapcompose.ui.utils.trainingPlansInfoDialogAlert

@Suppress("FunctionName")
@Composable
fun MainTrainingPlansView(
    instance: TrainingPlansActivity,
    viewModel: TrainingPlansViewModel
) {

    viewModel.getTrainingPlansList()

    val trainingPlanClicked by remember {
        mutableStateOf(TrainingPlan("", listOf(), 0))
    }

    var openTrainingPlansInfoDialog by remember {
        mutableStateOf(false)
    }

    if(openTrainingPlansInfoDialog) {
        openTrainingPlansInfoDialog = trainingPlansInfoDialogAlert(
            viewModel = viewModel,
            trainingPlan = trainingPlanClicked
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
            screenName = "PLAN TRENINGOWY",
            instance = instance
        )


        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )

        if(!viewModel.trainingPlansListResult.value.isNullOrEmpty()) {

            val trainingPlansList = viewModel.trainingPlansListResult.value!!

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(start = 10.dp, end = 10.dp)
            ){
                items(count = trainingPlansList.size) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(ambientColor = Color.Black, elevation = 4.dp)
                            .background(color = MaterialTheme.colors.primary)
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
                                trainingPlanClicked.planName = trainingPlansList[it].planName
                                trainingPlanClicked.exercise = trainingPlansList[it].exercise
                                trainingPlanClicked.assignedDay = trainingPlansList[it].assignedDay
                                openTrainingPlansInfoDialog = true
                            },
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = trainingPlansList[it].planName,
                            style = MaterialTheme.typography.caption,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .padding(5.dp)
                        )

                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "info",
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

            Button(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    instance.startActivity(Intent(instance, AddNewTrainingPlanActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
            ) {
                Text(
                    text = "DODAJ NOWY PLAN TRENINGOWY",
                    style = MaterialTheme.typography.caption,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }


        }else{


            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.1f)
            )

            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.5f)
                    .padding(start = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    instance.startActivity(Intent(instance, AddNewTrainingPlanActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
            ) {
                Text(
                    text = "DODAJ PIERWSZY PLAN TRENINGOWY",
                    style = MaterialTheme.typography.caption,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.2f)
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 20.dp),
                text = "Nie dodałeś jeszcze planów treningowych",
                style = MaterialTheme.typography.caption,
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