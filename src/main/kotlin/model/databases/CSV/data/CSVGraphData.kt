package model.databases.CSV.data

data class CSVGraphData(

    var isNode: Boolean,
    var id: Int,
    var name: String,
    var x: Double?,
    var y: Double?,
    var color: String?,
    var radius: Double?,
    var community: Int?,

    var from: String?,
    var to: String?,
    var weight: Long?
)