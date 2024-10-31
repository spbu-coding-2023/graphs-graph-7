package viewmodel

import androidx.compose.runtime.mutableStateOf
import view.StorageType

class SaveGraphMenuViewModel(val canvasViewModel: CanvasViewModel) {
    val storageType = mutableStateOf(StorageType.FILE)
    val fileName = mutableStateOf("")
    val uri = mutableStateOf("")
    val login = mutableStateOf("")
    val password = mutableStateOf("")
}
