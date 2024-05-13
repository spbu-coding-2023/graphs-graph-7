package graph.model

class Graph{
    var isDirected: Boolean = false
    val vertices = hashMapOf<Int, Vertex>()
    val edges = hashMapOf<Int, Edge>()

    fun vertices(): Collection<Vertex> = vertices.values

    fun edges(): Collection<Edge> = edges.values

    fun addVertex(id: Int, v: String): Vertex = vertices.getOrPut(id) { Vertex(v) }

    fun addEdge(firstVertexID: Int, secondVertexID: Int, weight: Long=1L, edgeID:Int): Edge {
        if (!isDirected){
            vertices[secondVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("cringe")
        }
        vertices[firstVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("cringe")
        return edges.getOrPut(edgeID) { Edge(Pair(firstVertexID,secondVertexID), weight, edgeID) }
    }

}
