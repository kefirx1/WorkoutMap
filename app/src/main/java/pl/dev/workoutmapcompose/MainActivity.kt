package pl.dev.workoutmapcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import pl.dev.workoutmapcompose.MainActivity.Companion.viewModel
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.ui.theme.*

class MainActivity : ComponentActivity(){
    companion object{
        lateinit var viewModel: WMViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OptIn(ExperimentalPagerApi::class)
    override fun onResume() {
        super.onResume()
        Log.e("TAG", "RESUME")

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)

        setContent {
            if(!viewModel.userExist()){

                Log.e("TAG", "User not exist")
                //REGISTER USER
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }else{
                MainDashboard(this)
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainDashboard(
    instance: MainActivity,
) {
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
                instance = instance
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
                ),
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
) {
    HorizontalPager(
        count = 2,
        modifier = Modifier
            .fillMaxSize()
    ) { page ->
        when (page) {
            0 -> FirstPage(instance = instance)
            1 -> SecondPage(instance = instance)
        }
    }
}

@Composable
fun FirstPage(
    instance: MainActivity
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        val firstInfo = viewModel.getUserFirstPageInfo()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(start = 10.dp),
                color = BlueGray50,
                fontFamily = mainFamily,
                fontSize = 30.sp,
                text = firstInfo.userName
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                color = BlueGray100,
                fontFamily = mainFamily,
                fontSize = 25.sp,
                text = "${firstInfo.userWeight}kg"
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
    instance: MainActivity
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


@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainDashboard(MainActivity())
}
