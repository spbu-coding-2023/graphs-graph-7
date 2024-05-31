package viewmodel.algos

import model.algorithms.BridgeFinder
import model.graph.Graph
import org.gephi.graph.api.GraphView
import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel

class BridgeFinderViewModel(graph: Graph, graphView: GraphViewModel) {
    val bridgeFinder = BridgeFinder(graph)
    val pairsList = mutableListOf<Pair<VertexViewModel, VertexViewModel>>()
    init{
        bridgeFinder.findBridges()
        val edges = bridgeFinder.bridges
        edges.forEach { bridge ->
            val key = graph.edges[bridge]
            val firstEnd: VertexViewModel = graphView.edgesView[key]!!.u
            val secondEnd: VertexViewModel = graphView.edgesView[key]!!.v
            pairsList.add(Pair(firstEnd, secondEnd))
        }
    }



}