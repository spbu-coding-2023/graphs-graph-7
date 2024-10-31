package model.keyVertices

import model.graph.Graph
import model.graph.Vertex
import java.util.*

class GraphBetweennessCentrality {
    fun getKeyVertices(graph: Graph): Map<Vertex, Float> {
        val betweennessMap = mutableMapOf<Vertex, Float>().withDefault { 0f }
        val vertices = graph.vertices.values

        for (sourceVertex in vertices) {
            val stack = Stack<Vertex>()
            val predecessors = mutableMapOf<Vertex, MutableList<Vertex>>().withDefault { mutableListOf() }
            val sourceVertexWeight = mutableMapOf<Vertex, Float>().withDefault { 0f }
            val distance = mutableMapOf<Vertex, Int>().withDefault { -1 }
            val sourceVertexDependency = mutableMapOf<Vertex, Float>().withDefault { 0f }

            sourceVertexWeight[sourceVertex] = 1f
            distance[sourceVertex] = 0

            val queue: Queue<Vertex> = LinkedList<Vertex>()
            queue.add(sourceVertex)

            while (queue.isNotEmpty()) {
                val currentVertex = queue.poll()
                stack.push(currentVertex)

                for (successorVertex in currentVertex.adjacentVertices) {
                    if (!distance.containsKey(successorVertex)) {
                        queue.offer(successorVertex)
                        distance[successorVertex] = distance.getOrDefault(currentVertex, 0) + 1
                    }
                    if (distance.getOrDefault(successorVertex, 0) == distance.getOrDefault(currentVertex, 0) + 1) {
                        sourceVertexWeight[successorVertex] = sourceVertexWeight.getOrDefault(successorVertex, 0f) + sourceVertexWeight.getOrDefault(currentVertex, 0f)
                        if (!predecessors.containsKey(successorVertex)) {
                            predecessors[successorVertex] = mutableListOf<Vertex>()
                        }
                        predecessors[successorVertex]!!.add(currentVertex)
                    }
                }
            }

            while (stack.isNotEmpty()) {
                val successorVertex = stack.pop()
                for (currentVertex in predecessors.getOrDefault(successorVertex, emptyList())) {
                    sourceVertexDependency[currentVertex] = sourceVertexDependency.getOrDefault(currentVertex, 0f) +
                            (sourceVertexWeight.getOrDefault(currentVertex, 0f) / sourceVertexWeight.getOrDefault(successorVertex, 0f)) *
                            (1 + sourceVertexDependency.getOrDefault(successorVertex, 0f))
                }
                if (successorVertex != sourceVertex) {
                    betweennessMap[successorVertex] = betweennessMap.getOrDefault(successorVertex, 0f) + sourceVertexDependency.getOrDefault(successorVertex, 0f)
                }
            }
        }

        return betweennessMap
    }
}
