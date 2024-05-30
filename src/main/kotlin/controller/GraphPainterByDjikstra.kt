package controller

import model.algorithms.Djikstra
import model.graph.Graph
import viewmodel.graph.GraphViewModel

class GraphPainterByDjikstra(
    private val graph: Graph,
    private val graphViewModel: GraphViewModel,
    private val startIdx: Int,
    private val endIdx: Int
) {
    private val pathFinder = Djikstra(graph, startIdx)
    private val path = pathFinder.findShortestPaths()
    val currPath = pathFinder.reconstructPath(endIdx)

    fun paint() {
        val vertexColor = generateRandomColor(startIdx * 123)
        for (vertexID in currPath) {
            val currVertex = graph.vertices[vertexID]
            graphViewModel.verticesView[currVertex]!!.color = vertexColor
        }
    }
}