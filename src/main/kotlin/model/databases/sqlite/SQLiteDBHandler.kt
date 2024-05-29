package model.databases.sqlite

import androidx.compose.ui.graphics.Color
import model.databases.sqlite.dao.edge.Edge
import model.databases.sqlite.dao.vertices.Vertex
import model.databases.sqlite.dao.vertices.Vertices
import model.databases.sqlite.dao.verticesView.VertexView
import model.databases.sqlite.dao.verticesView.VerticesView
import androidx.compose.ui.unit.dp
import model.databases.sqlite.dao.edge.Edges
import model.graph.Graph

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction
import viewmodel.graph.GraphViewModel
import java.io.File

class SQLiteDBHandler {
    lateinit var graph: Graph
    lateinit var graphViewModel: GraphViewModel
    var vertexViewModelFlag = false
    fun open(file: File, weighted: Boolean, directed: Boolean) {
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
        val newGraph = Graph()
        newGraph.isDirected = directed

        transaction {
            Vertex.all().forEach { vertex ->
                newGraph.addVertex(vertex.id.toString().toInt(), vertex.data)
            }
            Edge.all().forEach { edge ->
                var weight = edge.weight
                if (!weighted) {
                    weight = 1L
                }
                newGraph.addEdge(
                    edge.first!!.id.toString().toInt(), edge.second!!.id.toString().toInt(),
                    weight,
                    edge.id.toString().toInt()
                )
                if (!newGraph.isDirected) {
                    newGraph.vertices[edge.second!!.id.toString().toInt()]!!.incidentEdges.add(
                        edge.id.toString().toInt()
                    )
                }
                newGraph.vertices[edge.first!!.id.toString().toInt()]!!.incidentEdges.add(edge.id.toString().toInt())
                if (VerticesView.exists()) {
                    vertexViewModelFlag = true
                }
            }

        }
        if (vertexViewModelFlag) {
            val newGraphViewModel = GraphViewModel(newGraph)
            transaction {
                newGraphViewModel.verticesView.onEach {
                    val vertex = Vertex.find { Vertices.id eq it.value.vertex.id }.first()
                    val tmp = VertexView.find { VerticesView.vertex eq vertex.id }.first()
                    it.value.x = tmp.x.dp
                    it.value.y = tmp.y.dp
                    it.key.community = vertex.community
                    val rgb = tmp.color.split(":").map { color -> color.toInt() }
                    it.value.color = Color(rgb[0], rgb[1], rgb[2])
                    it.value.radius = tmp.r.dp
                }
            }
            graphViewModel = newGraphViewModel
        }
        graph = newGraph
    }

    fun save(file: File, graph: Graph, graphView: GraphViewModel, weighted: Boolean) {
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Edges)
            SchemaUtils.create(Vertices)
            SchemaUtils.create(VerticesView)
            graph.getVertices().forEach {
                Vertex.new {
                    data = it.data
                    community = it.community
                }
            }
            graph.getEdges().forEach {
                var newWeight = it.weight
                if (!weighted) {
                    newWeight = 1L
                }
                Edge.new {
                    first = Vertex.find { Vertices.id eq it.vertices.first }.first()
                    second = Vertex.find { Vertices.id eq it.vertices.second }.first()
                    weight = newWeight
                }
            }
            graphView.verticesViewValues.forEach {
                //println("${it.radius.toString().substring(0, it.x.toString().length - 4)}")
                val xDoubled: Double = it.x.toString().substring(0, it.x.toString().length - 4).toDouble()
                val yDoubled: Double = it.y.toString().substring(0, it.x.toString().length - 4).toDouble()
                val rDoubled: Double = it.radius.toString().substring(0, it.x.toString().length - 4).toDouble()
                //println("$xDoubled,$yDoubled,$rDoubled")
                VertexView.new {
                    vertex = Vertex.find { Vertices.id eq it.vertex.id }.first()
                    color = it.color.red.toString() + "/" + it.color.green.toString() + "/" + it.color.blue.toString()
                    x = xDoubled
                    y = yDoubled
                    r = rDoubled
                }
            }
        }
    }
}