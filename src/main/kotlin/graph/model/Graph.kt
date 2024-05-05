package graph.model

abstract class Graph<V, E> {
    abstract val vertices: Collection<Vertex<V>>
    abstract val edges: Collection<Edge<E, V>>

    abstract fun addVertex(v: V): Vertex<V>
    abstract fun addEdge(u: V, v: V, e: E): Edge<E, V>
}