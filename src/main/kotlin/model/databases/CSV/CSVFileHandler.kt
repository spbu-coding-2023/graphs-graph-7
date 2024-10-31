package model.databases.CSV

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.github.blackmo18.grass.dsl.grass
import java.io.File
import model.databases.CSV.data.CSVGraphData
import model.databases.CSV.data.VertexViewData
import model.graph.Graph
import viewmodel.graph.GraphViewModel

@ExperimentalStdlibApi
class CSVFileHandler {

    fun save(file: File, graphViewModel: GraphViewModel) {
        val data: MutableList<MutableList<String>> = mutableListOf(mutableListOf())
        graphViewModel.addVerticesToData(data)
        graphViewModel.addEdgesToData(data)

        val csvWriter = csvWriter { delimiter = ',' }
        val header =
            listOf(
                "isNode",
                "name",
                "id",
                "x",
                "y",
                "color",
                "radius",
                "community",
                "from",
                "to",
                "weight"
            )

        csvWriter.writeAll(listOf(header), file)
        csvWriter.writeAll(data, file, append = true)
    }

    fun open(file: File): Pair<Graph, GraphViewModel?> {
        try {
            val reader = csvReader { skipEmptyLine = true }
            val csvContents = reader.readAllWithHeader(file)
            val data = grass<CSVGraphData>().harvest(csvContents)

            val vertices = hashMapOf<Int, VertexViewData>()
            val newGraph = Graph()
            data.onEach {
                if (it.isNode) {
                    newGraph.addVertex(it.id, it.name)
                    val rgb: List<Float> =
                        it.color?.split("/")?.map { color -> color.toFloat() } ?: listOf(0f, 0f, 0f)
                    val vertex =
                        VertexViewData(
                            it.x,
                            it.y,
                            it.community ?: -1,
                            it.radius ?: 2.5,
                            Color(rgb[0], rgb[1], rgb[2])
                        )
                    vertices[it.id] = vertex
                }
            }
            data.onEach {
                if (!it.isNode)
                    newGraph.addEdge(it.from!!.toInt(), it.to!!.toInt(), it.weight!!, it.id)
            }

            val newGraphView = GraphViewModel(newGraph)
            newGraphView.verticesViewValues.onEach {
                val vertex = vertices[it.vertex.id]!!
                vertex.x?.let { x -> it.x }
                vertex.y?.let { y -> it.y }
                it.vertex.community = vertex.community
                it.radius = vertex.radius?.dp ?: 2.5.dp
                it.color = vertex.color
            }

            return newGraph to newGraphView
        } catch (e: Exception) {
            return Graph() to null
        }
    }

    private fun GraphViewModel.addVerticesToData(data: MutableList<MutableList<String>>) {
        verticesViewValues.onEach {
            val csvRow =
                mutableListOf(
                    "true",
                    it.vertex.id.toString(),
                    it.vertex.data,
                    it.x.toString(),
                    it.y.toString(),
                    it.color.red.toString() +
                        "/" +
                        it.color.green.toString() +
                        "/" +
                        it.color.blue.toString(),
                    it.radius.toString(),
                    it.vertex.community.toString(),
                    "",
                    "",
                    ""
                )
            data.add(csvRow)
        }
    }

    private fun GraphViewModel.addEdgesToData(data: MutableList<MutableList<String>>) {
        edgesViewValues.onEach {
            val csvRow =
                mutableListOf(
                    "false",
                    it.e.id.toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    it.u.vertex.id.toString(),
                    it.v.vertex.id.toString(),
                    it.weight
                )
            data.add(csvRow)
        }
    }
}
