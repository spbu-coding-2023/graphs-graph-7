package viewmodel

import androidx.compose.runtime.mutableStateOf

class LoadGraphMenuViewModel(val canvasViewModel: CanvasViewModel) {
    val graphName = mutableStateOf("")
    val isGraphWeighted = mutableStateOf(false)
    val isGraphDirected = mutableStateOf(false)
//    val selectedSaveType = mutableStateOf(GraphSavingType.LOCAL_FILE)
}
