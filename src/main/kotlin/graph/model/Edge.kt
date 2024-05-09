package graph.model

class Edge<V> (
    val vertices: Pair<Int, Int>,
    open var weight: Long = 1L,
){
    open fun incident(v: Int) = v == vertices.first || v == vertices.second
}