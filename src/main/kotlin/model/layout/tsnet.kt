package layout.tsnet

import graph.model.Graph
import model.layout.floydWarshall
import model.layout.normalizeLayout
import model.layout.tsnet
import viewmodel.graph.VertexViewModel

fun tsNET(graph: Graph, vertices: Collection<VertexViewModel>, perplexity: Double, learningRate: Double): Array<DoubleArray> {
    val distanceMatrix = floydWarshall(graph, vertices)
    //TODO remove, make all with Long
    val doubleDistanceMatrix = Array(distanceMatrix.size) { DoubleArray(distanceMatrix.size) }
    for (i in distanceMatrix.indices) {
        for (j in distanceMatrix[0].indices) {
            doubleDistanceMatrix[i][j] = distanceMatrix[i][j].toDouble()
        }
    }
    val coordinatesMatrix = Array(vertices.size) { DoubleArray(2) }
    val iterations = 5000
    val momentum = 0.5
    val tolerance = 1e-7
    val windowSize = 40
    val rEps = 0.05
    val lambdas2 = arrayOf(1.0, 1.2, 0.0)
    val lambdas3 = arrayOf(1.0, 0.01, 0.6)
    var resultMatrix = tsnet(
        vertices = doubleDistanceMatrix,
        perplexity = perplexity,
        vertexCoords = coordinatesMatrix,
        outputDims = 2,
        nEpochs = iterations,
        initial_LR = learningRate,
        final_LR = learningRate,
        lr_Switch = iterations / 2,
        initialMomentum = momentum,
        finalMomentum = momentum,
        momentumSwitch = iterations / 2,
        initialLKL = lambdas2[0],
        finalLKL = lambdas3[1],
        lKLSwitch = iterations / 2,
        initialLC = lambdas2[0],
        finalLC = lambdas3[1],
        lCSwitch = iterations / 2,
        initialLR = lambdas2[0],
        finalLR = lambdas3[0],
        lRSwitch = iterations / 2,
        rEps = rEps,
        autostop = tolerance,
        windowSize = windowSize,
    )
    resultMatrix = normalizeLayout(resultMatrix)
    return resultMatrix
}
