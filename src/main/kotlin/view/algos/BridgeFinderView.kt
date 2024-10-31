package view.algos

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import viewmodel.algos.BridgeFinderViewModel

// просто окрашиваю прилегающие вершины в красный цвет
@Composable
fun bridgeHighlighter(bridges: BridgeFinderViewModel) {
    val pairs = bridges.pairsList
    for (pair in pairs) {
        pair.first.color = Color.Red
        pair.second.color = Color.Red
    }
}
