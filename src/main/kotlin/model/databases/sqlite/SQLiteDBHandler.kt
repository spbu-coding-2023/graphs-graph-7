package model.databases.sqlite

import androidx.compose.ui.graphics.Color
import model.databases.sqlite.dao.edge.Edge
import model.databases.sqlite.dao.vertices.Vertex
import model.databases.sqlite.dao.vertices.Vertices
import model.databases.sqlite.dao.verticesView.VertexView
import model.databases.sqlite.dao.verticesView.VerticesView
import androidx.compose.ui.unit.dp
import model.graph.Graph

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction
import viewmodel.graph.GraphViewModel
import java.io.File

class SQLiteDBHandler {
    fun open(file: File, weighted: Boolean, directed: Boolean) {
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
        val newGraph = Graph()
        newGraph.isDirected=directed
        var vertexViewModelFlag = false
        transaction {
            Vertex.all().forEach { vertex ->
                newGraph.addVertex(vertex.id.toString().toInt(), vertex.data)
            }
            Edge.all().forEach { edge ->
                newGraph.addEdge(
                    edge.first!!.id.toString().toInt(), edge.second!!.id.toString().toInt(),
                    edge.weight, edge.id.toString().toInt()
                )
                if (!newGraph.isDirected) {
                    newGraph.vertices[edge.second!!.id.toString().toInt()]!!.incidentEdges.add(
                        edge.id.toString().toInt()
                    )
                }
                newGraph.vertices[edge.first!!.id.toString().toInt()]!!.incidentEdges.add(edge.id.toString().toInt())
            }
            if (VerticesView.exists()) {
                vertexViewModelFlag = true
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
        }

    }

    fun save(file: File) {
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")

    }
}