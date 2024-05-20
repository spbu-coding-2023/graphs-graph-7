package algorithms

import graph.model.Graph

class Djikstra(private val graph: Graph, private val startVertexID: Int) {
    private val distance = hashMapOf<Int, Long>()
    private val visited = hashMapOf<Int, Boolean>()
    private val from = hashMapOf<Int, Int>()

    fun findShortestPaths() {
        val n = graph.vertices.size

        for ((id, _) in graph.vertices) {
            distance[id] = Long.MAX_VALUE
            from[id] = -1
        }

        distance[startVertexID] = 0

        for (i in 0 until n) {

            var nearest = -1
            for ((vertexID, _) in graph.vertices) {
                if (!visited.getOrDefault(vertexID, false) && (nearest == -1 || distance[vertexID]!! < distance[nearest]!!)) {
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

    fun reconstructPath(endVertexID: Int): List<Int> {
        val path = mutableListOf<Int>()
        var finish = endVertexID

        while (finish != startVertexID) {
            path.add(finish)
            if (finish == -1) {
                path.clear()
                return path
            }
            finish = from[finish] ?: break
        }

        path.add(startVertexID)
        path.reverse()
        return path
    }
}