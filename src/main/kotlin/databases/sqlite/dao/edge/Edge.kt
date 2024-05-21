package databases.sqlite.dao.edge

import databases.sqlite.dao.vertices.Vertex
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Edge(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<Edge>(Edges)
    var first by Vertex optionalReferencedOn Edges.first
    var second by Vertex optionalReferencedOn Edges.second
    var weight by Edges.weight
}