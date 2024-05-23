package algorithms

import graph.model.Graph
import kotlin.math.max


class FordBellman(graph: Graph) {
    val INF = Long.MAX_VALUE
    private val verticesNumber = graph.vertices.size
    private val edgesNumber = graph.edges.size

    private val pathsLength = Array(verticesNumber) { INF }
    val pathVertices = Array(verticesNumber) { -1 }
    val pathEdges = Array(edgesNumber) { -1 }
    val resultPathVertices:MutableList<Int> = mutableListOf()
    val resultPathEdges:MutableList<Int> = mutableListOf()

    private val curGraph = graph
    var disconnectedGraphFlag = false
    var cycleFlag = false

    private fun negativeCycleBuilder(cycleFlag: Int) {
        var tmpCycleFlag = cycleFlag
        for (i in 1 until verticesNumber) {
            tmpCycleFlag = pathVertices[tmpCycleFlag-1]+1
        }
        var current = tmpCycleFlag
        var cycleEndFlag = true
        while (cycleEndFlag) {


            if (current == tmpCycleFlag && resultPathVertices.size > 1) {
                cycleEndFlag = false
                break
            }
            resultPathVertices.add(current)
            val destVertexID = current
            val sourseVertexID = pathVertices[current - 1] + 1
            for (edgeID in curGraph.vertices[sourseVertexID]!!.incidentEdges) {
                if (curGraph.edges[edgeID]!!.vertices.second == destVertexID) {
                    resultPathEdges.add(edgeID)
                    break
                }
            }
            current = pathVertices[current-1]+1
        }
    }

    fun shortestPath(startVertexID: Int, endVertexID: Int) {
        pathsLength[startVertexID - 1] = 0
        var curCycleFlag = -1
        for (i in 0 until verticesNumber) {
            curCycleFlag = -1
            for (j in 0 until edgesNumber) {
                val firstVertexPath = pathsLength[curGraph.edges[j + 1]!!.vertices.first - 1]
                var secondVertexPath = pathsLength[curGraph.edges[j + 1]!!.vertices.second - 1]
                if (firstVertexPath < INF) {
                    if (secondVertexPath > firstVertexPath + curGraph.edges[j + 1]!!.weight) {
                        pathsLength[curGraph.edges[j + 1]!!.vertices.second - 1] =
                            max(-INF, firstVertexPath + curGraph.edges[j + 1]!!.weight)
                        pathEdges[curGraph.edges[j + 1]!!.id - 1] = j + 1
                        pathVertices[curGraph.edges[j + 1]!!.vertices.second - 1] =
                            curGraph.edges[j + 1]!!.vertices.first - 1
                        curCycleFlag = curGraph.edges[j + 1]!!.vertices.second
                    }
                }
            }
        }
        if (curCycleFlag == -1) {
            if (pathsLength[endVertexID - 1] == INF) {
                disconnectedGraphFlag = true
                return
            } else {
                pathBuilder(endVertexID)
            }
        } else {
            cycleFlag=true
            negativeCycleBuilder(curCycleFlag)
        }
    }

    private fun pathBuilder(endVertexID: Int) {
        resultPathVertices.add(endVertexID)
        var tmp = endVertexID
        do {
            val destVertexID = tmp
            val sourceVertexID = pathVertices[tmp - 1] + 1
            for (edgeID:Int in curGraph.vertices[sourceVertexID]!!.incidentEdges) {
                if (curGraph.edges[edgeID]!!.vertices.second == destVertexID) {
                    resultPathEdges.add(edgeID)
                    break
                }
            }
            resultPathVertices.add(sourceVertexID)
            tmp = pathVertices[tmp-1] + 1
        } while (pathVertices[tmp-1] != -1)
    }
}



