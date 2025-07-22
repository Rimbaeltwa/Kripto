/*package com.anadolubank.kripto.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.candlestickSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.anadolubank.kripto.domain.model.OhlcBar
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.floor

private const val PRICE_STEP = 0.00001

private val RangeProvider = object : CartesianLayerRangeProvider {
    override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore) =
        PRICE_STEP * floor(minY / PRICE_STEP)
    override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore) =
        PRICE_STEP * ceil(maxY / PRICE_STEP)
}

private val StartAxisValueFormatter =
    CartesianValueFormatter.decimal(DecimalFormat("#,##0.00000"))

private val StartAxisItemPlacer = VerticalAxis.ItemPlacer.step({ PRICE_STEP * 5 })

private val BottomAxisValueFormatter = object : CartesianValueFormatter {
    private val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
    override fun format(
        context: CartesianMeasuringContext,
        value: Double,
        verticalAxisPosition: Axis.Position.Vertical?
    ): String = try {
        val weekMs = 7L * 24 * 60 * 60 * 1000
        dateFormat.format(Date(System.currentTimeMillis() - value.toLong() * weekMs))
    } catch (_: Exception) {
        value.toInt().toString()
    }
}

private val MarkerFmt = DecimalFormat("#,##0.000000")

@Composable
fun CryptoCandlestickChart(
    ohlcBars: List<OhlcBar>,
    modifier: Modifier = Modifier
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    // 1) define two layers
    val candleLayer = rememberCandlestickCartesianLayer(rangeProvider = RangeProvider)
    val lineLayer   = rememberLineCartesianLayer(

        lineProvider =  LineCartesianLayer.LineProvider.series(vicoTheme.lineCartesianLayerColors.map { _ ->
            LineCartesianLayer.rememberLine(LineCartesianLayer.LineFill.single(fill(Color.White)), stroke = LineCartesianLayer.LineStroke.continuous(thickness = 1.dp))
        }),
        rangeProvider = RangeProvider
    )

    // 2) build a chart with both layers
    val chart = rememberCartesianChart(
        candleLayer,
        lineLayer,
        startAxis = VerticalAxis.rememberStart(
            valueFormatter = StartAxisValueFormatter,
            itemPlacer     = StartAxisItemPlacer,
            guideline      = null,
            tick           = null,
            label          = rememberTextComponent(
                color   = Color.White,
                textSize= 12.sp,
                padding = insets(end = 8.dp)
            )
        ),
        bottomAxis = HorizontalAxis.rememberBottom(
            guideline      = null,
            valueFormatter = BottomAxisValueFormatter,
            tick           = null,
            label          = rememberTextComponent(
                color    = Color.White,
                textSize = 12.sp
            )
        ),
        marker = rememberDefaultCartesianMarker(
            valueFormatter = DefaultCartesianMarker.ValueFormatter.default(MarkerFmt),
            indicator      = null,
            label          = rememberTextComponent(
                color      = Color.White,
                textSize   = 12.sp,
                background = null
            )
        )
    )

    // 3) load your data into both series
    LaunchedEffect(ohlcBars) {
        if (ohlcBars.isNotEmpty()) {
            val indices     = ohlcBars.indices.reversed().toList()
            val openValues  = ohlcBars.map { it.open }
            val closeValues = ohlcBars.map { it.close }
            val lowValues   = ohlcBars.map { it.low }
            val highValues  = ohlcBars.map { it.high }

            modelProducer.runTransaction {
                // candle series
                candlestickSeries(
                    x       = indices,
                    opening = openValues,
                    closing = closeValues,
                    low     = lowValues,
                    high    = highValues
                )
                // line series following the close prices
                lineSeries {
                    series(x = indices, y = closeValues)
                }
            }
        }
    }

    // 4) host it
    CartesianChartHost(
        chart         = chart,
        modelProducer = modelProducer,
        modifier      = modifier.height(300.dp)
    )
}*/