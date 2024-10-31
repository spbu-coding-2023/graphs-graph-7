package model.databases.neo4j

import java.io.Closeable
import model.graph.Graph
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session
import org.neo4j.driver.Values

class Neo4jRepository(uri: String, user: String, password: String) : Closeable {
    private val driver: Driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))
    private val session: Session = driver.session()

    fun addVertex(vertexId: Int, vertexData: String, vertexCommunity: Int) {
        session.writeTransaction { tx ->
            tx.run(
                "CREATE (:Vertex {id:\$id, data:\$data, community:\$community})",
                Values.parameters("id", vertexId, "data", vertexData, "community", vertexCommunity)
            )
        }
    }

    fun addDirectedEdge(firstVertexId: Int, secondVertexId: Int, weight: Float, edgeId: Int) {
        session.writeTransaction { tx ->
            tx.run(
                "MATCH (v1:Vertex {id:\$id1}) MATCH (v2:Vertex {id:\$id2}) " +
                    "CREATE (v1)-[:Edge {id:\$edgeId, weight:\$weight}]->(v2)",
                Values.parameters(
                    "id1",
                    firstVertexId,
                    "id2",
                    secondVertexId,
                    "edgeId",
                    edgeId,
                    "weight",
                    weight
                )
            )
        }
    }

    fun addEdge(firstVertexId: Int, secondVertexId: Int, weight: Float, edgeId: Int) {
        session.writeTransaction { tx ->
            tx.run(
                "MATCH (v1:Vertex {id:\$id1}) MATCH (v2:Vertex {id:\$id2}) " +
                    "MERGE (v1)-[:Edge {id:\$edgeId, weight:\$weight}]-(v2)",
                Values.parameters(
                    "id1",
                    firstVertexId,
                    "id2",
                    secondVertexId,
                    "edgeId",
                    edgeId,
                    "weight",
                    weight
                )
            )
        }
    }

    fun getGraph(): Graph {
        val graph = Graph()

        session.readTransaction { tx ->
            val verticesResult =
                tx.run(
                    "MATCH (v:Vertex) RETURN v.id AS id, v.data AS data, v.community AS community",
                )
            verticesResult.list().forEach { record ->
                val vertexId = record.get("id").asInt()
                val vertexData = record.get("data").asString()
                val vertexCommunity = record.get("community").asInt()
                graph.addVertex(vertexId, vertexData)
                graph.vertices[vertexId]!!.community = vertexCommunity
            }

            val edgesResult =
                tx.run(
                    "MATCH (v1:Vertex)-[e:Edge]->(v2:Vertex) RETURN e.id AS id, v1.id AS v1, v2.id AS v2, e.weight AS weight"
                )
            edgesResult.list().forEach { record ->
                val edgeId = record.get("id").asInt()
                val firstVertexId = record.get("v1").asInt()
                val secondVertexId = record.get("v2").asInt()
                val weight = record.get("weight").asFloat()
                graph.addEdge(firstVertexId, secondVertexId, weight, edgeId)
            }
        }

        return graph
    }

    override fun close() {
        session.close()
        driver.close()
    }
}
