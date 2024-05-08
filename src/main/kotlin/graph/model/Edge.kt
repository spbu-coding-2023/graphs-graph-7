package graph.model

abstract class Edge<E, V> {
    abstract var element: E
    open var weight: Long = 1L
    open var isDirected: Boolean = false
    abstract val vertices: Pair<Vertex<V>, Vertex<V>>
    open fun incident(v: Vertex<V>) = v == vertices.first || v == vertices.second
}