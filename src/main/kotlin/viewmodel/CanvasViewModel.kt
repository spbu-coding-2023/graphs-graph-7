package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graph.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.layouts.RepresentationStrategy

class CanvasViewModel(graph: Graph, private val representationStrategy: RepresentationStrategy,) {
    val showVerticesLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
    val graphViewModel = GraphViewModel(graph)

    init {
        representationStrategy.place(1280.0, 860.0, graphViewModel)
    }
}