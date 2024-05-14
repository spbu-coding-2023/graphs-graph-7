package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import graph.model.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.layouts.RepresentationStrategy

class CanvasViewModel(graph: Graph, private val representationStrategy: RepresentationStrategy,) {
    private val showVerticesLabels = mutableStateOf(false)
    private val showEdgesLabels = mutableStateOf(false)
    private val graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels)

    init {
        representationStrategy.place(800.0, 600.0, graphViewModel.verticesViewValues)
    }

    fun resetGraphView() {
        representationStrategy.place(800.0, 600.0, graphViewModel.verticesViewValues)
        graphViewModel.verticesViewValues.forEach{ v -> v.color = Color.Gray}
    }

    fun setVerticesColor() {
        representationStrategy.highlight(graphViewModel.verticesViewValues)
    }
}