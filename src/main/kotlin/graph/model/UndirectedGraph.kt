package graph.model

class Graph<V>{
    open var isDirected: Boolean = false
    private val vertices = hashMapOf<Int, Vertex<V>>()
    private val edges = hashMapOf<Int, Edge<V>>()

    fun vertices(): Collection<Vertex<V>> = vertices.values

    fun edges(): Collection<Edge<V>> = edges.values

    fun addVertex(id: Int, v: V): Vertex<V> = vertices.getOrPut(id) { Vertex(v, -1) }

    fun addEdge(u: Int, v: Int, weight: Long=1L, id:Int): Edge<V> {
        val first = addVertex(u,vertices[u]?.element ?: throw Exception("cringe") )
        val second = addVertex(v, vertices[v]?.element?: throw Exception("cringe") )
        if (!isDirected){
            second.incidentEdges.add(id)
        }
        first.incidentEdges.add(id)
        return edges.getOrPut(id) { Edge(Pair(first,second), weight) }
    }

}
