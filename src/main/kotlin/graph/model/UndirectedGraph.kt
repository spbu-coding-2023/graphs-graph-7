package graph.model

class Graph<V>{
    var isDirected: Boolean = false
    val vertices = hashMapOf<Int, Vertex<V>>()
    val edges = hashMapOf<Int, Edge<V>>()

    fun vertices(): Collection<Vertex<V>> = vertices.values

    fun edges(): Collection<Edge<V>> = edges.values

    fun addVertex(id: Int, v: V): Vertex<V> = vertices.getOrPut(id) { Vertex(v) }

    fun addEdge(first: Int, second: Int, weight: Long=1L, id:Int): Edge<V> {
        if (!isDirected){
            vertices[second]?.incidentEdges?.add(id) ?: throw Exception("cringe")
        }
        vertices[first]?.incidentEdges?.add(id) ?: throw Exception("cringe")
        return edges.getOrPut(id) { Edge(Pair(first,second), weight) }
    }

}
