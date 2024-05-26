package model.layout

import graph.model.Graph
import viewmodel.graph.VertexViewModel
import kotlin.math.min

fun floydWarshall(graph: Graph, vertices: Collection<VertexViewModel>,): Array<LongArray> {
    val numberOfVertices = vertices.size
    val resultMatrix = Array(vertices.size) { LongArray(vertices.size){Long.MAX_VALUE} }
    vertices.forEach { vertexViewModel ->
        val edges = vertexViewModel.vertex.incidentEdges
        for (edge in edges) {
            val firstVertex = graph.edges[edge]!!.vertices.first-1
            val secondVertex = graph.edges[edge]!!.vertices.second-1
            resultMatrix[firstVertex][secondVertex] = graph.edges[edge]!!.weight
        }
    }
    for (k in 0..numberOfVertices) {
        for (i in 0..numberOfVertices) {
            for (j in 0..numberOfVertices) {
                if (resultMatrix[i][k] < Long.MAX_VALUE && resultMatrix[k][j] < Long.MAX_VALUE)
                    resultMatrix[i][j] = min(resultMatrix[i][j], resultMatrix[i][k] + resultMatrix[k][j])
            }
        }
    }
    return resultMatrix
}