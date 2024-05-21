package viewmodel.graph

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import graph.model.Edge
import graph.model.Graph
import graph.model.Vertex


class GraphViewModel(
    private val graph: Graph,

) {
    val verticesView: HashMap<Vertex,VertexViewModel> = hashMapOf()
    init {
        graph.getVertices().forEach { vertex ->
            verticesView[vertex] = VertexViewModel(0.dp, 0.dp, Color.Black, vertex)
        }
    }

    private val edgesView: HashMap<Edge, EdgeViewModel> = hashMapOf()
    init {
        graph.getEdges().forEach { edge ->
            val fst = verticesView[graph.vertices[edge.vertices.first]]
                ?: throw IllegalStateException("VertexView for vertex with id: ${edge.vertices.first} not found")
            val snd = verticesView[graph.vertices[edge.vertices.second]]
                ?: throw IllegalStateException("VertexView for vertex with id: ${edge.vertices.second} not found")
            val currentEdgeView = EdgeViewModel(fst, snd, edge)
            edgesView[edge]=currentEdgeView
        }

    }


    val verticesViewValues: Collection<VertexViewModel>
        get() = verticesView.values

    val edgesViewValues: Collection<EdgeViewModel>
        get() = edgesView.values

}



