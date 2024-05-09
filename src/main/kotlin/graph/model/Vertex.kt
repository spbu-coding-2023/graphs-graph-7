package graph.model

import algorithms.KruskalsMST
import androidx.compose.runtime.MutableDoubleState

class Vertex<V> (
    var element: V,
    var community: Int = -1,
    var incidentEdges: MutableList<Int> = mutableListOf()
){}