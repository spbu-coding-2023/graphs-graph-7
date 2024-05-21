package model.databases.sqlite.dao.verticesView

import model.databases.sqlite.dao.vertices.Vertices
import org.jetbrains.exposed.dao.id.IntIdTable

object VerticesView: IntIdTable("VerticesView") {
    val vertex = reference("vertex", Vertices).nullable()
    val x = double("x")
    val y = double("y")
    val r = double("r")
    val color = varchar("color", 255)
}