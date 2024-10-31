package model.databases.sqlite.dao.edge

import model.databases.sqlite.dao.vertices.Vertices
import org.jetbrains.exposed.dao.id.IntIdTable

object Edges : IntIdTable("Edges") {
    val first = reference("first", Vertices).nullable()
    val second = reference("second", Vertices).nullable()
    val weight = float("weight")
}
