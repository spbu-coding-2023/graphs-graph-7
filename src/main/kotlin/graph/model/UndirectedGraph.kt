package graph.model

class Graph<V,E>{
    open var isDirected: Boolean = false
    private val vertices = hashMapOf<V, Vertex<V>>()
    private val edges = hashMapOf<E, Edge<E,V>>()

    fun vertices(): Collection<Vertex<V>> = vertices.values

    fun edges(): Collection<Edge<E,V>> = edges.values

    fun addVertex(v: V): Vertex<V> = vertices.getOrPut(v) { Vertex(v, -1) }

    fun addEdge(u: V, v: V, e: E, weight: Long=1L,): Edge<E,V> {
        val first = addVertex(u)
        val second = addVertex(v)
        return edges.getOrPut(e) { Edge(e, Pair(first,second), weight) }
    }

}
