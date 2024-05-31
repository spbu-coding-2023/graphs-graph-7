package model.algorithms

import model.graph.Graph
import java.awt.geom.Point2D.distance
import kotlin.math.min

class BridgeFinder(graph: Graph) {
    private val arraySize = graph.vertices.size
    private val visitedVertices = Array(arraySize) { false }
    private val timeIn = Array(arraySize) { -1 }
    private val fUp = Array(arraySize) { -1 }
    val bridges = mutableListOf<Int>()
    private val curGraph = graph

    fun findBridges() {
        var timer = 0

        fun isBridge(edgeID: Int): Int? {
            val destination = curGraph.edges[edgeID]?.vertices?.second ?: throw Exception("Incorrect Database")
            val bridge = curGraph.edges[edgeID]
            val bridges =
                curGraph.vertices[bridge?.vertices?.first]?.incidentEdges ?: throw Exception("Incorrect Database")
            for (curBridge in bridges) {
                if (curGraph.edges[curBridge]!!.vertices.second == destination && curBridge != edgeID) {
                    return null
                }
            }
            return edgeID
        }

        fun dfs(vertexID: Int, parent: Int = -1) {
            visitedVertices[vertexID] = true
            timer++
            timeIn[vertexID] = timer
            fUp[vertexID] = timer
            val incidentEdgesID = curGraph.vertices[vertexID + 1]!!.incidentEdges
            for (edgeID in incidentEdgesID) {
                val edge = curGraph.edges[edgeID]!!.vertices
                val newVertexID = if (vertexID == edge.first - 1) {
                    edge.second - 1
                } else {
                    edge.first - 1
                }

                if (newVertexID == parent) continue

                if (visitedVertices[newVertexID]) {
                    fUp[vertexID] = min(timeIn[newVertexID], fUp[vertexID])
                } else {
                    dfs(newVertexID, vertexID)
                    fUp[vertexID] = min(fUp[newVertexID], fUp[vertexID])
                    if (fUp[newVertexID] > timeIn[vertexID]) {
                        if (isBridge(edgeID) != null) {
                            bridges.add(edgeID)
                        }
                    }
                }
            }
        }

        for (i in 1..arraySize) {
            if (!visitedVertices[i - 1]) {
                dfs(i - 1)
            }
        }
    }
}