package viewmodel

import androidx.compose.runtime.mutableStateOf

class LoadGraphMenuViewModel(val canvasViewModel: CanvasViewModel) {
    val graphName = mutableStateOf("")
}
