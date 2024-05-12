package algorithms

import graph.model.Graph
import java.math.BigInteger
import kotlin.math.max


class FordBellman<V>(graph: Graph<V>) {
    val INF = Long.MAX_VALUE
    private val verticesNumber = graph.vertices.size
    private val edgesNumber = graph.edges.size
    private val pathsLength = Array(verticesNumber) { INF }
    private val resultPath = Array(verticesNumber) { -1 }

    fun shortestPath(graph: Graph<V>) {
        pathsLength[0] = 0
        var cycleFlag: Int
        for (i in 1 until verticesNumber) {
            cycleFlag = -1
            for (j in 1 until edgesNumber) {
                val firstVertexPath = pathsLength[graph.edges[j]!!.vertices.first]
                var secondVertexPath = pathsLength[graph.edges[j]!!.vertices.second]
                if (firstVertexPath < INF) {
                    if (secondVertexPath > firstVertexPath + graph.edges[j]!!.weight) {
                        secondVertexPath = max(-INF, INF + graph.edges[j]!!.weight)
                        resultPath[graph.edges[j]!!.vertices.second] = graph.edges[j]!!.vertices.first
                        cycleFlag = graph.edges[j]!!.vertices.second
                    }
                }
            }
        }
    }


}