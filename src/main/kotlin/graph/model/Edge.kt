package graph.model

class Edge<V> (
    val vertices: Pair<Vertex<V>, Vertex<V>>,
    open var weight: Long = 1L,
){
    open fun incident(v: Vertex<V>) = v == vertices.first || v == vertices.second
}