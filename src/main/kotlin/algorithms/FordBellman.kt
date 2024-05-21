package algorithms

import model.graph.Graph
import java.math.BigInteger
import kotlin.math.max


class FordBellman(graph: Graph) {
    val INF = Long.MAX_VALUE
    private val verticesNumber = graph.vertices.size
    private val edgesNumber = graph.edges.size
    private val pathsLength = Array(verticesNumber) { INF }
    private val resultPath = Array(verticesNumber) { -1 }

    fun shortestPath(graph: Graph) {
        pathsLength[0] = 0
        var cycleFlag = -1
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
        negativeCycleCheck(graph, cycleFlag)
    }

    private fun negativeCycleCheck(graph: Graph, cycleFlag: Int) {
        if (cycleFlag == -1) {
            println("No negative cycles")
        } else {
            var tmpCycleFlag = cycleFlag
            for (i in 1 until verticesNumber) {
                tmpCycleFlag = resultPath[tmpCycleFlag]
            }
            val path: MutableList<Int> = mutableListOf()
            var current = tmpCycleFlag
            var cycleEndFlag = true
            while(cycleEndFlag){
                path.add(current)
                if (current == tmpCycleFlag && path.size >1){
                    cycleEndFlag = false
                }
                current=resultPath[current]
            }
            println("Negative cycle:")
            path.forEach {
                print("$it ")
            }
        }
    }

}