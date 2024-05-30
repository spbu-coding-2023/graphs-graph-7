package model.graph

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
            vertices[secondVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("Wrong database")
            vertices[secondVertexID]?.adjacentVertices?.add(vertices[firstVertexID]!!)
        }
        vertices[firstVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("Wrong database")
        vertices[firstVertexID]?.adjacentVertices?.add(vertices[secondVertexID]!!)

        return edges.getOrPut(edgeID) { Edge(Pair(firstVertexID,secondVertexID), weight, edgeID) }
    }
}
