package model.graph

class Edge(
    val vertices: Pair<Int, Int>,
    var weight: Long = 1L,
    var id: Int = 1,
) {
    //fun incident(v: Int) = v == vertices.first || v == vertices.second
}