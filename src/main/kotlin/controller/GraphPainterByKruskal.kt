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
            val verticesId = graph.edges[edgeId]?.vertices
            val currVertexFirst = graph.vertices[verticesId?.first]
            val currVertexSecond = graph.vertices[verticesId?.second]
            graphViewModel.verticesView[currVertexFirst]!!.color = Color.Red
            graphViewModel.verticesView[currVertexSecond]!!.color = Color.Red
        }
    }
}
