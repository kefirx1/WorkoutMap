package pl.dev.workoutmapcompose.ui.screenDashboard

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsActivity
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansActivity
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryActivity
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.buttonsDashboard
import pl.dev.workoutmapcompose.ui.theme.mainFamily
import pl.dev.workoutmapcompose.ui.utils.DashboardProgressList
import pl.dev.workoutmapcompose.ui.utils.workoutStartDialogAlert

@Suppress("FunctionName")
@ExperimentalPagerApi
@Composable
fun MainDashboard(
    instance: MainActivity,
    viewModel: DashboardViewModel
) {

    viewModel.getUserMainViewInfo()

    var openWorkoutDashboardDialog by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState()

    if(openWorkoutDashboardDialog) {
        openWorkoutDashboardDialog = workoutStartDialogAlert(
            instance = instance,
            viewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth()
        ) {
            DashboardHorizontalPager(
                instance = instance,
                viewModel = viewModel,
                pagerState = pagerState
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp),
            activeColor = BlueGray50,
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 20.dp),
                onClick = {
                    viewModel.getTrainingPlansList()
                    if(viewModel.trainingPlansListResult.value.isNullOrEmpty()){
                        Toast.makeText(
                            instance,
                            "Musisz wpierw dodać trening",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        openWorkoutDashboardDialog = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(
                    text = "TRENUJ",
                    style = MaterialTheme.typography.caption,
                    fontSize = 40.sp
                )
            }
        }
    }
}

@Suppress("FunctionName")
@ExperimentalPagerApi
@Composable
fun DashboardHorizontalPager(
    instance: MainActivity,
    viewModel: DashboardViewModel,
    pagerState: PagerState
) {
    HorizontalPager(
        state = pagerState,
        count = 2,
        modifier = Modifier
            .fillMaxSize()
    ) { page ->
        when (page) {
            0 -> DashboardFirstPage(viewModel = viewModel)
            1 -> DashboardSecondPage(instance = instance, viewModel = viewModel)
        }

    }
}

@Suppress("FunctionName")
@Composable
fun DashboardFirstPage(
    viewModel: DashboardViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            viewModel.userMainViewInfoResult.value?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 10.dp),
                    style = MaterialTheme.typography.caption,
                    fontSize = 30.sp,
                    text = it.userName
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                style = MaterialTheme.typography.caption,
                fontSize = 25.sp,
                text = "${viewModel.userMainViewInfoResult.value?.userWeight}kg"
            )
        }
        Spacer(
            modifier = Modifier
                .background(color = Color.Black)
                .height(2.dp)
                .fillMaxWidth()
        )
    }
}

@Suppress("FunctionName")
@Composable
fun DashboardSecondPage(
    instance: MainActivity,
    viewModel: DashboardViewModel
) {

    val exerciseTypes = listOf("Klatka piersiowa", "Plecy", "Barki", "Biceps", "Triceps", "Nogi", "Przedramiona", "Brzuch")
    var exerciseTypesSelected by remember{
        mutableStateOf("Klatka piersiowa")
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    viewModel.getExercisesJSON()
    viewModel.getFullExerciseProgressHistory()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(top = 10.dp)
            ) {

                Text(
                    text = exerciseTypesSelected,
                    fontFamily = mainFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.typography.caption.color
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
                    exerciseTypes.forEach {
                        DropdownMenuItem(
                            onClick = {
                                exerciseTypesSelected = it
                                expanded = false
                                viewModel.getSpecificExercisesProgressList(muscleGroup = exerciseTypesSelected)
                                viewModel.selectedMuscleGroup.value = exerciseTypesSelected
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

            Spacer(
                modifier = Modifier
                    .height(15.dp)
                    .fillMaxWidth()
            )

            if(viewModel.exercisesProgressListResult.value != null) {
                DashboardProgressList(
                    viewModel = viewModel
                )
            }

        }
        Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                )
        Spacer(
            modifier = Modifier
                .background(color = Color.Black)
                .height(2.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    instance.startActivity(Intent(instance, WeightHistoryActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
            ) {
                Text(
                    text = "HISTORIA WAGI",
                    style = buttonsDashboard,
                    color = MaterialTheme.typography.caption.color
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    instance.startActivity(Intent(instance, TrainingPlansActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
            ) {
                Text(
                    text = "PLANY TRENINGOWE",
                    style = buttonsDashboard,
                    color = MaterialTheme.typography.caption.color
                )
            }
            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth()
                    .padding(start = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    instance.startActivity(Intent(instance, SettingsActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
            ) {
                Text(
                    text = "USTAWIENIA",
                    style = buttonsDashboard,
                    color = MaterialTheme.typography.caption.color
                )
            }

        }
    }

}