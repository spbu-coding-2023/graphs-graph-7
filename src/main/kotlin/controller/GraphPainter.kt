package controller

import androidx.compose.ui.graphics.Color
import graph.model.Graph
import model.community.Louvain
import viewmodel.graph.GraphViewModel
import kotlin.random.Random

class GraphPainter(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val finder = Louvain(graph)
    private val communities = finder.findCommunities()

    fun paint() {
        for ((i, community) in communities.withIndex()) {
            val communityColor = generateRandomColor(i * 123)
            for (vertexID in community) {
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