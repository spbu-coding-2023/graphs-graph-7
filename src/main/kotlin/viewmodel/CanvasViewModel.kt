package viewmodel

import androidx.compose.runtime.mutableStateOf
import model.graph.Graph
import viewmodel.algos.BridgeFinderViewModel
import viewmodel.graph.GraphViewModel
import viewmodel.layouts.RepresentationStrategy

class CanvasViewModel(var graph: Graph, var representationStrategy: RepresentationStrategy) {
    val showVerticesLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
    var graphViewModel = GraphViewModel(graph)
    val bridges = BridgeFinderViewModel(graph, graphViewModel)

    private val _isOpenLoadGraph = mutableStateOf(false)
    var isOpenLoadGraph: Boolean
        get() = _isOpenLoadGraph.value
        set(value) {
            _isOpenLoadGraph.value = value
        }

    init {
        representationStrategy.place(1920.0, 1080.0, graphViewModel)
    }

    fun switchLayout(newLayout: RepresentationStrategy) {
        representationStrategy = newLayout
        representationStrategy.place(1920.0, 1080.0, graphViewModel)
    }
}
