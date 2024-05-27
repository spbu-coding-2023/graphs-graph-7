package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import graph.model.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.layouts.RepresentationStrategy

class CanvasViewModel(graph: Graph, private val representationStrategy: RepresentationStrategy,) {
    val showVerticesLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
    val graphViewModel = GraphViewModel(graph)

    init {
        representationStrategy.place(800.0, 600.0, graphViewModel.verticesViewValues)
    }

    fun resetGraphView() {
        representationStrategy.place(800.0, 600.0, graphViewModel.verticesViewValues)
        graphViewModel.verticesViewValues.forEach{ v -> v.color = Color.Blue}
    }

    fun setVerticesColor() {
        representationStrategy.highlight(graphViewModel.verticesViewValues)
    }
}