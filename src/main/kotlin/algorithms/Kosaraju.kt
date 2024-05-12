package algorithms

import graph.model.Graph

class Kosaraju<V>(private val graph: Graph<V>) {
    private val used = hashMapOf<Int, Boolean>()
    private val order = mutableListOf<Int>()
    private val component = mutableListOf<Int>()

    private fun dfs1(vertexID: Int) {
        used[vertexID] = true
        val vertex = graph.vertices[vertexID] ?: return
        for (edgeID in vertex.incidentEdges) {
            val edge = graph.edges[edgeID] ?: continue
            val nextVertexID = if (vertexID == edge.vertices.first) edge.vertices.second else edge.vertices.first
            if (used[nextVertexID] != true) {
                dfs1(nextVertexID)
            }
        }
        order.add(vertexID)
    }



}
