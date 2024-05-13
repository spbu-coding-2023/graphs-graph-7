package databases.sqlite.dao.vertices

import org.jetbrains.exposed.dao.id.IntIdTable

object Vertices: IntIdTable("Vertices") {
    val element = varchar("element", 255)
    val community = integer("community")
}