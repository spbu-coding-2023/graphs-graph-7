package databases.sqlite.dao.vertices

import org.jetbrains.exposed.dao.id.IntIdTable

object Vertices: IntIdTable("Vertices") {
    val data = varchar("data", 255)
    val community = integer("community")
}