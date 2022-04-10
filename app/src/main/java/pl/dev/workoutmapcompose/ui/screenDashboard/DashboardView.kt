package pl.dev.workoutmapcompose.ui.screenDashboard

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import pl.dev.workoutmapcompose.MainActivity
import pl.dev.workoutmapcompose.SettingsActivity
import pl.dev.workoutmapcompose.ui.theme.*

@ExperimentalPagerApi
@Composable
fun MainDashboard(
    instance: MainActivity,
    viewModel: DashboardViewModel
) {
    viewModel.getUserMainViewInfo()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth()
        ) {
            DashboardHorizontalPager(
                instance = instance,
                viewModel = viewModel
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxSize(),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                )
            ) {
                Text(
                    text = "TRENUJ",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 40.sp
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun DashboardHorizontalPager(
    instance: MainActivity,
    viewModel: DashboardViewModel
) {
    HorizontalPager(
        count = 2,
        modifier = Modifier
            .fillMaxSize()
    ) { page ->
        when (page) {
            0 -> FirstPage(instance = instance, viewModel = viewModel)
            1 -> SecondPage(instance = instance, viewModel = viewModel)
        }
    }
}

@Composable
fun FirstPage(
    instance: MainActivity,
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
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 30.sp,
                    text = it.userName
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                color = BlueGray100,
                fontFamily = mainFamily,
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

@Composable
fun SecondPage(
    instance: MainActivity,
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
                .fillMaxHeight(0.8f)
        ) {

        }
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
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray500
                ),
            ) {
                Text(
                    text = "PLANY TRENINGOWE",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 20.sp
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                onClick = {
                    val intent = Intent(instance, SettingsActivity::class.java)
                    instance.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray500
                ),
            ) {
                Text(
                    text = "USTAWIENIA",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 20.sp
                )
            }

        }
    }

}