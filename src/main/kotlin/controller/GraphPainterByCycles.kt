package controller

import model.graph.Graph
import model.graph.Vertex
import viewmodel.graph.GraphViewModel
import model.algorithms.FindCycles

class GraphPainterByCycles(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val algoInitialize = FindCycles()
    private val cycles = mutableListOf<List<Vertex>>()
    private val vertices = graph.getVertices()

    fun paint() {
        for(vertex in vertices){
            cycles.add(algoInitialize.simpleCycles(graph, vertex))
        }
        for ((i, cycle) in cycles.withIndex()) {
            val cycleColor = generateRandomColor(i * 451)
            for (vertex in cycle) {
                graphViewModel.verticesView[vertex]!!.color = cycleColor
            }
        }
    }
}
