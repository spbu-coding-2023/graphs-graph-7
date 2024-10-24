package model.graph

class Edge(
    val vertices: Pair<Int, Int>,
    var weight: Float = 1f,
    var id: Int = 1,
) {
    // fun incident(v: Int) = v == vertices.first || v == vertices.second
}
