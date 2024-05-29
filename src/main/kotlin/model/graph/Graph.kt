package model.graph

import model.graph.Edge
import model.graph.Vertex

class Graph{
    var isDirected: Boolean = false
    val vertices = hashMapOf<Int, Vertex>()
    val edges = hashMapOf<Int, Edge>()

    fun getVertices(): Collection<Vertex> = vertices.values

    fun getEdges(): Collection<Edge> = edges.values

    fun addVertex(id: Int, v: String): Vertex {
        val newVertex = Vertex(v)
        newVertex.id = id
        return vertices.getOrPut(id) { newVertex }
    }

    fun addEdge(firstVertexID: Int, secondVertexID: Int, weight: Long=1L, edgeID:Int): Edge {
        if (!isDirected){
            vertices[secondVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("cringe")
        }
        vertices[firstVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("cringe")
        return edges.getOrPut(edgeID) { Edge(Pair(firstVertexID,secondVertexID), weight, edgeID) }
    }

}
