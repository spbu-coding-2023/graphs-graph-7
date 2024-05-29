package view.algos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex

import viewmodel.CanvasViewModel
import viewmodel.algos.BridgeFinderViewModel
import viewmodel.graph.VertexViewModel

//просто окрашиваю прилегающие вершины в красный цвет
@Composable
fun bridgeHighlighter(bridges: BridgeFinderViewModel) {
    val pairs = bridges.pairsList
    for (pair in pairs) {
        pair.first.color = Color.Red
        pair.second.color = Color.Red
    }
}