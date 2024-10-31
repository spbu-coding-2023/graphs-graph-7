package controller

import androidx.compose.ui.graphics.Color
import model.algorithms.KruskalsMST
import model.graph.Graph
import viewmodel.graph.GraphViewModel

class GraphPainterByKruskal(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val algoInitialize = KruskalsMST()
    private val tree = algoInitialize.kruskals(graph)

    fun paint() {
        for (edgeId in tree) {
            val currEdge = graph.edges[edgeId]
            graphViewModel.edgesView[currEdge]!!.color = Color.Red
        }
    }
}
