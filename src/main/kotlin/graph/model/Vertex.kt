package graph.model

import algorithms.KruskalsMST
import androidx.compose.runtime.MutableDoubleState

class Vertex<V> (
    var element: V,
    var incidentEdges: MutableList<Int> = mutableListOf()
){
    var id: Int = 0
    var community: Int = -1
}