package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.TrainingPlansActivity
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
            .padding(10.dp)
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





    }

}