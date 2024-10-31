package controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import model.graph.Graph
import model.keyVertices.GraphBetweennessCentrality
import viewmodel.graph.GraphViewModel

class GraphSizerByCentrality(private val graph: Graph, private val graphViewModel: GraphViewModel) {
    private val algoInitialize = GraphBetweennessCentrality()
    private val centrality = algoInitialize.getKeyVertices(graph)
    
    fun changeSize() {
        for (vertex in centrality.keys) {
            graphViewModel.verticesView[vertex]!!.radius = centrality[vertex]!!.dp
            graphViewModel.verticesView[vertex]!!.x += 1.dp
        }
    }
}
