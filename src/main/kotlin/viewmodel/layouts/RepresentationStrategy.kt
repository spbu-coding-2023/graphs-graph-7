package viewmodel.layouts

import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel

interface RepresentationStrategy {
    fun place(width: Double, height: Double, graph: GraphViewModel)

    fun highlight(vertices: Collection<VertexViewModel>)
}
