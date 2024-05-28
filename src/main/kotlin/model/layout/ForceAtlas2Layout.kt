package model.layout

import androidx.compose.ui.unit.dp
import model.graph.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.layouts.RepresentationStrategy
import org.gephi.layout.plugin.forceAtlas.ForceAtlas
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder
import org.gephi.graph.api.Edge
import org.gephi.graph.api.GraphController
import org.gephi.graph.api.GraphModel
import org.gephi.graph.api.Node
import org.gephi.layout.plugin.force.StepDisplacement
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2LayoutData
import org.gephi.project.api.ProjectController
import org.openide.util.Lookup
import viewmodel.graph.VertexViewModel
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


class ForceAtlas2Layout:RepresentationStrategy{

    override fun place(width: Double, height: Double, graphViewModel:GraphViewModel){

        val pc = Lookup.getDefault().lookup(ProjectController::class.java)
        pc.newProject()
        val graphModel = Lookup.getDefault().lookup(GraphController::class.java).graphModel
        val graph = graphModel.undirectedGraph

        val verticesMap = mutableMapOf<Int,Node>()
        for (vertex in graphViewModel.verticesViewValues){
            val v: Node =graphModel.factory().newNode(vertex.vertex.id.toString())
            v.setX(Random.nextFloat()*10)
            v.setY(Random.nextFloat()*10)
            graph.addNode(v)
            verticesMap[vertex.vertex.id]=v
        }
    //TODO добавить возможность получения информации об ориентированности графа
        for (edge in graphViewModel.edgesViewValues){
            val e: Edge = graphModel.factory().newEdge(
                verticesMap[edge.u.vertex.id],
                verticesMap[edge.v.vertex.id],
                1,
                false
            )
            graph.addEdge(e)
        }
        val layout = ForceAtlas2(null)
        layout.setGraphModel(graphModel)
        layout.initAlgo()
        layout.resetPropertiesValues()
        layout.isAdjustSizes = true
        layout.isBarnesHutOptimize = true
        layout.scalingRatio = 20.0
        layout.gravity = 2.0

        var i = 0
        while (i < 5000 && layout.canAlgo()) {
            layout.goAlgo()
            i++
        }
        layout.endAlgo()

        for (vertex in graphViewModel.verticesViewValues) {
            val v: Node = graph.getNode(vertex.vertex.id.toString())
            val x = ((width/2 + v.x()))
            val y = ((height/2 + v.y()))
            vertex.x = (x).toInt().dp
            vertex.y = (y).toInt().dp
        }
    }

    override fun highlight(vertices: Collection<VertexViewModel>) {
        TODO("Not yet implemented")
    }
}
