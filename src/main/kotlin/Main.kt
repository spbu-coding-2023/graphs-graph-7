import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import graph.model.Graph
import view.Canvas
import viewmodel.CanvasViewModel
import viewmodel.layouts.CircularLayout

val graph = Graph().apply {
    addVertex(1,"Thomas Shelby")
    addVertex(2, "Andrew Tate")
    addVertex(3, "Iakov")
    addVertex(4, "John Shelby")
    addVertex(5, "Tristan Tate")
    addVertex(6, "Arthur Shelby")
    addVertex(7, "Ryan Gosling")

    addEdge(1, 2, 1,1)
    addEdge(3, 4, 2,2)
    addEdge(1, 3, 3,3)
    addEdge(2, 4, 4,4)
    addEdge(2, 5, 5,5)
    addEdge(5, 7, 6,6)

    addVertex(8, "Pudge")
    addVertex(9,"Tiny")
    addVertex(10, "Lycan")
    addVertex(11,"Io")
    addVertex(12,"Lion")
    addVertex(13,"Sniper")
    addVertex(14,"Roshan")

    addEdge(14, 8, 7,7)
    addEdge(14, 9, 8,8)
    addEdge(14, 10, 9,9)
    addEdge(14, 11, 10,10)
    addEdge(14, 12, 11,11)
    addEdge(14, 13, 12,12)

    addEdge(14, 3, 0,13)
}
@Composable
@Preview
fun App() {
    MaterialTheme {
        Canvas(CanvasViewModel(graph, CircularLayout()))
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}