package com.giftech.terbit.presentation.ui.pages.graph

import android.text.Layout
import android.text.TextUtils
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import com.giftech.terbit.presentation.ui.theme.dark_CustomColor2
import com.giftech.terbit.presentation.util.toTypeface
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultColors
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions

val defaultChartColors =
    @Composable {
        listOf(
            MaterialTheme.colorScheme.primary,
            dark_CustomColor2,
        )
    }

@Composable
fun rememberChartStyle(
    chartColors: List<Color> = defaultChartColors(),
): ChartStyle {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography.toTypeface()
    val isSystemInDarkTheme = isSystemInDarkTheme()
    
    return remember(
        chartColors,
        isSystemInDarkTheme,
    ) {
        val defaultColors = if (isSystemInDarkTheme) DefaultColors.Dark else DefaultColors.Light
        
        ChartStyle(
            ChartStyle.Axis(
                axisLabelColor = colorScheme.outline,
                axisLineColor = colorScheme.outlineVariant,
                axisTickColor = colorScheme.outlineVariant,
                axisGuidelineColor = colorScheme.outlineVariant.copy(0.5f),
                
                axisLabelTypeface = typography.labelSmall,
                
                axisLabelTextSize = 10.sp,
            ),
            ChartStyle.ColumnChart(
                columns = chartColors.map { chartColor ->
                    LineComponent(
                        color = chartColor.toArgb(),
                        thicknessDp = 10f,
                        shape = Shapes.pillShape,
                    )
                },
                dataLabel = TextComponent.Builder().apply {
                    color = colorScheme.outline.copy(0.75f).toArgb()
                    typeface = typography.labelSmall
                    textSizeSp = 10f
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    textAlignment = Layout.Alignment.ALIGN_CENTER
                    margins = MutableDimensions(
                        0f,
                        0f,
                        0f,
                        4f,
                    )
                }.build(),
            ),
            ChartStyle.LineChart(
                lines = chartColors.map { chartColor ->
                    LineChart.LineSpec(
                        lineColor = chartColor.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            Brush.verticalGradient(
                                listOf(
                                    chartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    chartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                ),
                            ),
                        ),
                    )
                },
            ),
            ChartStyle.Marker(),
            Color(defaultColors.elevationOverlayColor),
        )
    }
}