package graph.model

class Vertex<V> (
    var data: V,
    var incidentEdges: MutableList<Int> = mutableListOf()
){
    var id: Int = 0
    var community: Int = -1
}