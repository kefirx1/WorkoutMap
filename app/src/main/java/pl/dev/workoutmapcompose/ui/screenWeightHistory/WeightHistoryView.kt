package pl.dev.workoutmapcompose.ui.screenWeightHistory


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot
import pl.dev.workoutmapcompose.Convert
import pl.dev.workoutmapcompose.WeightHistoryActivity
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.ui.components.DialogAlerts
import pl.dev.workoutmapcompose.ui.theme.*

fun exitSettings(
    instance: WeightHistoryActivity
){
    //TODO
    instance.finish()
}

@Composable
fun MainWeightHistoryView(
    instance: WeightHistoryActivity,
    viewModel: WeightHistoryViewModel
) {

    viewModel.getWeightHistory()

    var openInsertWeightDialog by remember {
        mutableStateOf(false)
    }
    var openRowWeightDialog by remember {
        mutableStateOf(false)
    }

    var clickedRowWeightHistory by remember {
        mutableStateOf(WeightHistory("", 0))
    }

    if(openInsertWeightDialog) {
        openInsertWeightDialog = DialogAlerts.insertNewWeightDialogAlert(
            instance = instance,
            viewModel = viewModel
        )
    }

    if(openRowWeightDialog) {
        openRowWeightDialog = DialogAlerts.onWeightHistoryRowClickDialogAlert(
            instance = instance,
            viewModel = viewModel,
            weightHistory = clickedRowWeightHistory
        )
    }

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
                text = "HISTORIA WAGI",
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

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            viewModel.weightHistoryResult.value?.let {
                if(it.isNotEmpty()){
                    WeightHistoryLineGraph(arrayListOf(transformToListOfDataPoint(it)))
                }else{
                    WeightHistoryLineGraph(arrayListOf(arrayListOf(DataPoint(0f, 0f))))
                }
            }

            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
            )

            Button(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.5f)
                    .padding(end = 4.dp)
                    .shadow(ambientColor = Color.Black, shape = RectangleShape, elevation = 10.dp),
                onClick = {
                    openInsertWeightDialog = true
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

            Spacer(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 6.dp)
                    .background(color = Color.Black)
                    .height(2.dp)
                    .fillMaxWidth()
            )



            if(!viewModel.weightHistoryResult.value.isNullOrEmpty()) {

                val sortedWeightHistoryList = viewModel.weightHistoryResult.value!!.sortedByDescending {
                    it.weighingDate
                }

                LazyColumn{
                    items(count = sortedWeightHistoryList.size) {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    openRowWeightDialog = true
                                    clickedRowWeightHistory = sortedWeightHistoryList[it]
                                },
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = Convert.convertTimeInSecToDateString(sortedWeightHistoryList[it].weighingDate),
                                color = BlueGray50,
                                fontFamily = mainFamily,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "${sortedWeightHistoryList[it].weight}kg",
                                color = BlueGray50,
                                fontFamily = mainFamily,
                                fontSize = 20.sp
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
            }




        }
    }
}

@Composable
fun WeightHistoryLineGraph(lines: ArrayList<ArrayList<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    dataPoints = lines[0],
                    connection = LinePlot.Connection(color = BlueGray900),
                    intersection = LinePlot.Intersection(color = BlueGray500),
                    highlight = LinePlot.Highlight(color = Purple200),
                )
            ),
            grid = LinePlot.Grid(Teal200, steps = 10),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onSelection = { xLine, points ->

        }
    )
}

private fun transformToListOfDataPoint(
    weightHistoryList: ArrayList<WeightHistory>
): ArrayList<DataPoint> {

    val dataPointsList: ArrayList<DataPoint> = ArrayList()

    val sortedWeightHistoryList = weightHistoryList.sortedBy {
        it.weighingDate
    }

    var iterator = 1f

    sortedWeightHistoryList.forEach {
        dataPointsList.add(DataPoint(iterator, it.weight.toFloat()))
        iterator++
    }

    return dataPointsList
}