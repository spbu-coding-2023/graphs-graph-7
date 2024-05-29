package viewmodel

import androidx.compose.runtime.mutableStateOf
import model.graph.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.layouts.RepresentationStrategy

class CanvasViewModel(var graph: Graph, private val representationStrategy: RepresentationStrategy,) {
    val showVerticesLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
    var graphViewModel = GraphViewModel(graph)

    private val _isOpenLoadGraph = mutableStateOf(false)
    var isOpenLoadGraph: Boolean
        get() = _isOpenLoadGraph.value
        set(value) {
            _isOpenLoadGraph.value = value
        }

    init {
        representationStrategy.place(1280.0, 860.0, graphViewModel)

    }
}