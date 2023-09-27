package com.giftech.terbit.ui.pages.graph

import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberEndAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

@Composable
fun LineChart(
    entries: List<List<ChartEntry>>,
    xLabels: List<String>,
    yLabels: List<String>,
    labelFormatter: MarkerLabelFormatter,
    modifier: Modifier = Modifier,
) {
    ProvideChartStyle(rememberChartStyle()) {
        val chartEntryModelProducer = ChartEntryModelProducer(entries)
        
        val defaultLines = currentChartStyle.lineChart.lines
        
        val xAxis = rememberBottomAxis(
            guideline = null,
            label = axisLabelComponent(
                verticalMargin = 4.dp,
            ),
            valueFormatter = { value, _ ->
                xLabels[value.toInt()]
            },
        )
        val yAxis = rememberEndAxis(
            axis = null,
            guideline = axisGuidelineComponent(
                shape = Shapes.rectShape,
            ),
            tick = null,
            label = axisLabelComponent(
                horizontalMargin = 4.dp,
            ),
            itemPlacer = AxisItemPlacer.Vertical.default(yLabels.size),
            valueFormatter = { value, _ ->
                yLabels[value.toInt()]
            },
        )
        Chart(
            chart = lineChart(
                lines = remember(defaultLines) {
                    defaultLines.map { defaultLine ->
                        defaultLine.copy(
                            lineBackgroundShader = null,
                            lineThicknessDp = 2f,
                            pointSizeDp = 10f,
                            point = ShapeComponent(
                                shape = Shapes.pillShape,
                                color = defaultLine.lineColor,
                                strokeWidthDp = 1f,
                                strokeColor = Color.White.toArgb(),
                            ),
                            pointConnector = DefaultPointConnector(
                                cubicStrength = 0f,
                            ),
                        )
                    }
                },
                axisValuesOverrider = object : AxisValuesOverrider<ChartEntryModel> {
                    override fun getMinY(model: ChartEntryModel): Float = 0f
                    override fun getMaxY(model: ChartEntryModel): Float =
                        yLabels.lastIndex.toFloat()
                },
            ),
            chartModelProducer = chartEntryModelProducer,
            bottomAxis = xAxis,
            endAxis = yAxis,
            marker = rememberMarker(
                labelFormatter = labelFormatter,
            ),
            chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
            diffAnimationSpec = snap(),
            modifier = modifier
                .height(220.dp),
        )
    }
}