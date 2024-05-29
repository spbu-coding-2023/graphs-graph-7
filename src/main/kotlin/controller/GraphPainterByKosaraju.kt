package controller

import androidx.compose.ui.graphics.Color
import model.algorithms.Kosaraju
import model.graph.Graph
import model.community.Louvain
import viewmodel.graph.GraphViewModel
import kotlin.random.Random

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

    private fun generateRandomColor(base: Int): Color {
        val mRandom = Random(base)
        val red: Int = (base + mRandom.nextInt(256)) / 2
        val green: Int = (base + mRandom.nextInt(256)) / 2
        val blue: Int = (base + mRandom.nextInt(256)) / 2
        return Color(red, green, blue)
    }
}