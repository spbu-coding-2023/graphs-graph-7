package algorithms

import graph.model.Graph

class Djikstra(private val graph: Graph) {
    private val distance = hashMapOf<Int, Long>()
    private val visited = hashMapOf<Int, Boolean>()
    private val from = hashMapOf<Int, Int>()

    fun findShortestPaths(startVertexID: Int) {
        val n = graph.vertices.size

        for ((id, _) in graph.vertices) {
            distance[id] = Long.MAX_VALUE
            from[id] = -1
        }

        distance[startVertexID] = 0

        for (i in 0 until n) {

            var nearest = -1
            for ((vertexID, _) in graph.vertices) {
                if (!visited.getOrDefault(vertexID, false) && (nearest == -1 || distance[vertexID]!! < distance[vertexID]!!)) {
                    nearest = vertexID
                }
            }
            visited[nearest] = true

            if (distance[nearest] == Long.MAX_VALUE) break

            for (edgeID in graph.vertices[nearest]!!.incidentEdges) {
                val edge = graph.edges[edgeID]!!

                val to = if (nearest == edge.vertices.first) edge.vertices.second else edge.vertices.first
                val weight = edge.weight

                if (distance[nearest]!! + weight < distance[to]!!) {
                    distance[to] = distance[nearest]!! + weight
                    from[to] = nearest
                }
            }
        }
    }
}