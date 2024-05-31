package controller

import model.graph.Graph
import model.community.Louvain
import viewmodel.graph.GraphViewModel

class GraphPainterByCommunity(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val finder = Louvain(graph)
    private val communities = finder.findCommunities()

    fun paint() {
        for ((i, community) in communities.withIndex()) {
            val communityColor = generateRandomColor(i * 123)
            for (vertexID in community) {
                val currVertex = graph.vertices[vertexID]
                graphViewModel.verticesView[currVertex]!!.color = communityColor
            }
        }
    }
}