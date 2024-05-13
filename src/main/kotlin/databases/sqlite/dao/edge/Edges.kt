package databases.sqlite.dao.edge

import databases.sqlite.dao.vertices.Vertices
import org.jetbrains.exposed.dao.id.IntIdTable

object Edges: IntIdTable("Edge") {
    val element = varchar("element", 255)
    val first = reference("first", Vertices).nullable()
    val second = reference("second", Vertices).nullable()
    val weight = varchar("weight", 0).nullable()
}