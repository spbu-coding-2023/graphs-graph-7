package model.algorithms

import model.graph.Graph
import model.graph.Vertex

class FindCycles {
    fun simpleCycles(graph: Graph, startingVertex: Vertex): List<Vertex> {
        val isDirected = graph.isDirected
        val cameFrom = HashMap<Vertex, Vertex>(graph.vertices.size)
        val visited = HashSet<Vertex>(graph.vertices.size)
        val stack = mutableListOf(startingVertex)

        while (stack.isNotEmpty()) {
            val currentVertex = stack.removeLast()
            visited.add(currentVertex)
            for (neighbourVertex in currentVertex.adjacentVertices) {

                if (neighbourVertex == startingVertex) {
                    cameFrom[neighbourVertex] = currentVertex
                }

                if (neighbourVertex == startingVertex && (isDirected || cameFrom[currentVertex] != startingVertex)) {
                    // found path
                    val path = mutableListOf(startingVertex)
                    var cur = cameFrom[neighbourVertex]
                    while (cur != startingVertex) {
                        if (cur == null) break
                        path.add(cur)
                        cur = cameFrom[cur]
                    }
                    path.add(startingVertex)
                    return path.reversed()
                }

                if (neighbourVertex !in visited) {
                    cameFrom[neighbourVertex] = currentVertex
                    stack.add(neighbourVertex)
                }
            }
        }

        return listOf()
    }
}