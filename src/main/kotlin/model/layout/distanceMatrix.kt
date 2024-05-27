package model.layout

import graph.model.Graph
import viewmodel.graph.VertexViewModel
import kotlin.math.min

fun floydWarshall(graph: Graph, vertices: Collection<VertexViewModel>,): Array<LongArray> {
    val numberOfVertices = vertices.size
    val resultMatrix = Array(vertices.size) { LongArray(vertices.size){Long.MAX_VALUE} }
    var maxDistance = Long.MIN_VALUE
    vertices.forEach { vertexViewModel ->
        val edges = vertexViewModel.vertex.incidentEdges
        for (edge in edges) {
            val firstVertex = graph.edges[edge]!!.vertices.first-1
            val secondVertex = graph.edges[edge]!!.vertices.second-1
            resultMatrix[firstVertex][secondVertex] = graph.edges[edge]!!.weight
        }
    }
    for (k in 0 until numberOfVertices) {
        for (i in 0 until numberOfVertices) {
            for (j in 0 until numberOfVertices) {
                if (resultMatrix[i][j]!=Long.MAX_VALUE){
                    maxDistance = kotlin.math.max(resultMatrix[i][j], maxDistance)
                }
                if (resultMatrix[i][k] < Long.MAX_VALUE && resultMatrix[k][j] < Long.MAX_VALUE)
                    resultMatrix[i][j] = min(resultMatrix[i][j], resultMatrix[i][k] + resultMatrix[k][j])

            }
        }
    }
    for (i in resultMatrix.indices){
        for (j in resultMatrix[i].indices){
            if (resultMatrix[i][j] == Long.MAX_VALUE){
                resultMatrix[i][j] = maxDistance*15
            }
        }
    }
    return resultMatrix
}