package model.databases.CSV.data

import androidx.compose.ui.graphics.Color

data class VertexViewData(

    var x: Double?,
    var y: Double?,
    var community: Int = -1,
    var radius: Double?,
    var color: Color
)