package controller

import model.graph.Graph
import viewmodel.graph.GraphViewModel
import algorithms.FindCycles

class GraphPainterByCycles(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val algoInitialize = FindCycles()
    private val cycles = algoInitialize.simpleCycles(graph)

    fun paint() {
        for ((i, cycle) in cycles.withIndex()) {
            val cycleColor = generateRandomColor(i * 451)
            for (vertex in cycle) {
                graphViewModel.verticesView[vertex]!!.color = cycleColor
            }
        }
    }
}
