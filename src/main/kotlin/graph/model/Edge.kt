package graph.model

abstract class Edge<E, V> {
    abstract var element: E
    abstract val vertices: Pair<Vertex<V>, Vertex<V>>
    fun incident(v: Vertex<V>) = v == vertices.first || v == vertices.second
}