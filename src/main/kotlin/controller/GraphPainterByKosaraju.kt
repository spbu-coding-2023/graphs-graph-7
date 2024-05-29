package controller

import model.algorithms.Kosaraju
import model.graph.Graph
import viewmodel.graph.GraphViewModel

class GraphPainterByKosaraju(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val finder = Kosaraju(graph)
    private val components = finder.findStronglyConnectedComponents()

    fun paint() {
        for ((i, component) in components.withIndex()) {
            val communityColor = generateRandomColor(i * 123)
            for (vertexID in component) {
                val currVertex = graph.vertices[vertexID]
                graphViewModel.verticesView[currVertex]!!.color=communityColor
            }
        }
    }
}