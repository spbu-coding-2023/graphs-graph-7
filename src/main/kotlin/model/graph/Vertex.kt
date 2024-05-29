package model.graph

class Vertex(
    var data: String,
    var incidentEdges: MutableList<Int> = mutableListOf()
){
    var id: Int = 0
    var community: Int = -1
}