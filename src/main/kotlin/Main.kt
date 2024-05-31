
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application

import androidx.compose.ui.window.rememberWindowState

import model.graph.Graph
import viewmodel.layouts.ForceAtlas2Layout
import view.Canvas

import viewmodel.CanvasViewModel
import java.awt.Dimension

val graph = Graph().apply {
    addVertex(1,"Thomas")
    addVertex(2, "Andrew")
    addVertex(3, "Iakov")
    addVertex(4, "John")
    addVertex(5, "Tristan")
    addVertex(6, "Arthur")
    addVertex(7, "Ryan")

    addEdge(1, 2, 1,1)
    addEdge(3, 4, 2,2)
    addEdge(1, 3, 3,3)
    addEdge(2, 3, 4,4)
    addEdge(5, 3, 5,5)
    addEdge(3, 7, 6,6)

    addVertex(8, "Pudge")
    addVertex(9,"Tiny")
    addVertex(10, "Lycan")
    addVertex(11,"Io")
    addVertex(12,"Lion")
    addVertex(13,"Sniper")
    addVertex(14,"Roshan")

    addEdge(14, 8, 6,7)
    addEdge(14, 9, 6,8)
    addEdge(14, 10, 6,9)
    addEdge(14, 11, 6,10)
    addEdge(14, 12, 6,11)
    addEdge(14, 13, 5,12)
    addEdge(14, 3, 0,13)


    addVertex(15, "1")
    addVertex(16,"2")
    addVertex(17, "3")
    addVertex(18,"4")
    addVertex(19,"5")
    addVertex(20,"6")
    addVertex(21,"7")


    addEdge(16, 15, 22,14)
    addEdge(15, 17, 1,15)
    addEdge(15, 18, 6,16)
    addEdge(19, 15, 2,17)
    addEdge(15, 21, 3,18)
    addEdge(20, 15, 5,19)
    addEdge(17, 20, 0,20)
    addEdge(14, 20, 0,21)

}
val windowSizeStart = Pair(820,640)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val canvasGraph = CanvasViewModel(graph, ForceAtlas2Layout())
        MaterialTheme {
            Canvas(canvasGraph)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        title = "ZeitNot",
        state = rememberWindowState(position = WindowPosition(Alignment.Center))
    ) {
        window.minimumSize = Dimension(windowSizeStart.first, windowSizeStart.second)
        App()
    }
}