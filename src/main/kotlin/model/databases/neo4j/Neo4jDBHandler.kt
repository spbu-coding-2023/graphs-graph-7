package model.databases.neo4j

import graph.model.Graph

class Neo4jHandler(private val repository: Neo4jRepository) {

    fun saveGraphToNeo4j(graph: Graph) {
        for (vertex in graph.getVertices()) {
            repository.addVertex(vertex.id, vertex.data, vertex.community)
        }

        if (graph.isDirected) {
            for (edge in graph.getEdges()) {
                repository.addDirectedEdge(edge.vertices.first, edge.vertices.second, edge.weight, edge.id)
            }

        } else {
            for (edge in graph.getEdges()) {
                repository.addEdge(edge.vertices.first, edge.vertices.second, edge.weight, edge.id)
            }
        }
    }

    fun loadGraphFromNeo4j(): Graph {
        val graph = repository.getGraph()
        return graph
    }
}