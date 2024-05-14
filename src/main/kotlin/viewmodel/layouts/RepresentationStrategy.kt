package viewmodel.layouts

import viewmodel.graph.VertexViewModel

interface RepresentationStrategy {
    fun place(width: Double, height: Double, vertices: Collection<VertexViewModel>)
    fun highlight(vertices: Collection<VertexViewModel>)
}