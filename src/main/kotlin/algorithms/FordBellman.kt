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
    private val curGraph = graph
    val edgesPath = mutableListOf<Int>()
    val verticesPath = mutableListOf<Int>()
    fun shortestPath() {
        pathsLength[0] = 0
        var cycleFlag = -1
        for (i in 1 until verticesNumber) {
            cycleFlag = -1
            for (j in 1 until edgesNumber) {
                val firstVertexPath = pathsLength[curGraph.edges[j]!!.vertices.first]
                var secondVertexPath = pathsLength[curGraph.edges[j]!!.vertices.second]
                if (firstVertexPath < INF) {
                    if (secondVertexPath > firstVertexPath + curGraph.edges[j]!!.weight) {
                        secondVertexPath = max(-INF, INF + curGraph.edges[j]!!.weight)
                        resultPath[curGraph.edges[j]!!.vertices.second] = curGraph.edges[j]!!.vertices.first
                        cycleFlag = curGraph.edges[j]!!.vertices.second
                    }
                }
            }
        }
        if (cycleFlag==-1){

        }
        else{
            negativeCycleCheck()
        }

    }

    private fun negativeCycleCheck() {
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