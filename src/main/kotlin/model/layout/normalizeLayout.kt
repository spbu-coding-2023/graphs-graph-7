package model.layout

import kotlin.math.abs


fun normalizeLayout(verticesCoordinates: Array<DoubleArray>): Array<DoubleArray> {
    val tmpVerticesCoordinates = Array(verticesCoordinates.size) { DoubleArray(verticesCoordinates[0].size) }
    for (i in verticesCoordinates.indices) {
        System.arraycopy(verticesCoordinates[i], 0, tmpVerticesCoordinates[i], 0, verticesCoordinates[0].size)
    }

    // Translate so that smallest values for both x and y are 0.
    for (dim in verticesCoordinates[0].indices) {
        var minVal = Double.MAX_VALUE
        for (i in tmpVerticesCoordinates.indices) {
            if (tmpVerticesCoordinates[i][dim] < minVal) {
                minVal = tmpVerticesCoordinates[i][dim]
            }
        }
        for (i in tmpVerticesCoordinates.indices) {
            tmpVerticesCoordinates[i][dim] -= minVal
        }
    }

    // Scale so that max(max(x, y)) = 1 (while keeping the same aspect ratio!)
    var maxVal = Double.MIN_VALUE
    for (i in tmpVerticesCoordinates.indices) {
        for (j in tmpVerticesCoordinates[i].indices) {
            if (abs(tmpVerticesCoordinates[i][j]) > maxVal) {
                maxVal = abs(tmpVerticesCoordinates[i][j])
            }
        }
    }
    val scaling = 1 / maxVal
    for (i in tmpVerticesCoordinates.indices) {
        for (j in tmpVerticesCoordinates[i].indices) {
            tmpVerticesCoordinates[i][j] *= scaling
        }
    }
    return tmpVerticesCoordinates
}
