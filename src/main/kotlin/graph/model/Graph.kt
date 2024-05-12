package graph.model

class Graph<V>{
    var isDirected: Boolean = false
    val vertices = hashMapOf<Int, Vertex<V>>()
    val edges = hashMapOf<Int, Edge<V>>()

    fun vertices(): Collection<Vertex<V>> = vertices.values

    fun edges(): Collection<Edge<V>> = edges.values

    fun addVertex(id: Int, v: V): Vertex<V> = vertices.getOrPut(id) { Vertex(v) }

    fun addEdge(firstVertexID: Int, secondVertexID: Int, weight: Long=1L, edgeID:Int): Edge<V> {
        if (!isDirected){
            vertices[secondVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("cringe")
        }
        vertices[firstVertexID]?.incidentEdges?.add(edgeID) ?: throw Exception("cringe")
        return edges.getOrPut(edgeID) { Edge(Pair(firstVertexID,secondVertexID), weight, edgeID) }
    }

}
