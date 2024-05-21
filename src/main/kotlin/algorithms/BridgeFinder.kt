package algorithms

import graph.model.Graph
import java.awt.geom.Point2D.distance
import kotlin.math.min

class BridgeFinder(graph: Graph) {
    private val arraySize = graph.vertices.size
    private val visitedVertices = Array(arraySize) {false}
    private val timeIn = Array(arraySize) {0}
    private val fUp = Array(arraySize) {0}
    val bridges = mutableListOf<Int>()

    fun findBridges(graph: Graph){
        var timer = 0
        fun isBridge(edgeID: Int): Int?{
            val destination = graph.edges[edgeID]?.vertices?.second ?: throw Exception("Incorrect Database")
            val bridge = graph.edges[edgeID]
            val bridges = graph.vertices[bridge?.vertices?.first]?.incidentEdges ?: throw Exception("Incorrect Database")
            for (curBridge in bridges) {
                if (graph.edges[curBridge]!!.vertices.second==destination && curBridge!=edgeID){
                    return null
                }
            }
            return edgeID
        }

        fun dfs(vertexID: Int, parent: Int = -1){
            visitedVertices[vertexID] = true
            timeIn[vertexID] = timer++
            fUp[vertexID] = timer++
            val incidentEdgesID = graph.vertices[vertexID]!!.incidentEdges
            for (edgeID in incidentEdgesID){
                val edge = graph.edges[edgeID]!!.vertices
                val currentVertex = graph.vertices[vertexID]
                val newVertexID = if (currentVertex == graph.vertices[edge.first]){
                    edge.first
                }
                else{
                    edge.second
                }
                if (newVertexID == parent) continue
                if (visitedVertices[newVertexID]){
                    fUp[vertexID] = min(timeIn[newVertexID], fUp[vertexID])
                }
                else{
                    dfs(newVertexID, vertexID)
                    fUp[vertexID] = min(fUp[newVertexID], fUp[vertexID])
                    if(fUp[vertexID] > timeIn[newVertexID]){
                        if (isBridge(edgeID)!=null){
                            bridges.add(edgeID)
                        }
                    }
                }
            }
        }

        for (i in arraySize - 1 downTo 0){
            if (visitedVertices[i]){
                dfs(i)
            }
        }
    }
}