package model.layout

import java.util.*
import kotlin.jvm.internal.Reflection.function
import kotlin.math.*


private const val EPSILON = 1e-16
private const val FLOAT_H = 1e-16f

//fun computeGradient(cost: Int, matrix: Array<DoubleArray>): Array<DoubleArray> {
//    val numRows = matrix.size
//    val numCols = matrix[0].size
//
//    val gradient = Array(numRows) { DoubleArray(numCols) }
//
//    for (i in 0 until numRows) {
//        for (j in 0 until numCols) {
//            val centralDifferenceX: Double =
//                (function(functionId, matrix, i + 1, j) - function(functionId, matrix, i - 1, j)) / (2 * 0.00001)
//            val centralDifferenceY: Double =
//                (function(functionId, matrix, i, j + 1) - function(functionId, matrix, i, j - 1)) / (2 * 0.00001)
//
//            gradient[i][j] = sqrt(centralDifferenceX * centralDifferenceX + centralDifferenceY * centralDifferenceY)
//        }
//    }
//
//    return gradient
//}
private fun gradient(cost: Double, coords: Array<DoubleArray>): Array<DoubleArray> {
    val gradient = Array(coords.size) { DoubleArray(coords[0].size) { 0.0 } }

    for (i in coords.indices) {
        for (j in coords[i].indices) {
            gradient[i][j] = (coords[i][j] - 0.0001*cost)
        }
    }
    return gradient
}

private fun dotProduct(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
    val result = Array(a.size) { DoubleArray(b[0].size) { 0.0} }

    for (i in a.indices) {
        for (j in b[0].indices) {
            for (k in b.indices) {
                result[i][j] += a[i][k] * b[k][j]
            }
        }
    }

    return result
}

private fun sum(a: DoubleArray): Double {
    var result = 0.0
    for (value in a) {
        result += value
    }
    return result
}

private fun min(a: DoubleArray): Double {
    var result = Double.POSITIVE_INFINITY
    for (value in a) {
        if (value < result) {
            result = value
        }
    }
    return result
}

private fun max(a: DoubleArray): Double {
    var result = Double.NEGATIVE_INFINITY
    for (value in a) {
        if (value > result) {
            result = value
        }
    }
    return result
}


private fun sqeuclideanVar(matrix: Array<DoubleArray>): Array<DoubleArray> {
    val size = matrix.size
    val ss = DoubleArray(size) { 0.0 }
    for (i in 0 until size) {
        for (j in matrix[i].indices) {
            if(matrix[i][j] * matrix[i][j]>1000){
                ss[i] +=  EPSILON
            }else{
                ss[i] += matrix[i][j] * matrix[i][j]
            }
        }
    }
    val result = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            result[i][j] = ss[i] + ss[j]
        }
    }
    val rows = matrix.size
    val cols = matrix[0].size

    val transposed = Array(cols) { DoubleArray(rows) { 0.0 } }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            transposed[j][i] = matrix[i][j]
        }
    }
    val matrixdot = dotProduct(matrix,transposed)
    for (i in 0 until size) {
        for (j in 0 until size) {
            result[i][j] -= 2*matrixdot[i][j]
        }
    }
    return result
}

private fun euclideanVar(matrix: Array<DoubleArray>): Array<DoubleArray> {
    val sqeuclideanVar = sqeuclideanVar(matrix)
    val result = Array(sqeuclideanVar.size) {
        DoubleArray(
            sqeuclideanVar[0].size
        )
    }
    for (i in sqeuclideanVar.indices) {
        for (j in sqeuclideanVar[i].indices) {
            result[i][j] = max(sqeuclideanVar[i][j], EPSILON)
            result[i][j] = result[i][j].pow(0.5)
        }
    }
    return result
}

private fun pIJConditionalVar(
    matrix: Array<DoubleArray>,
    sigma: DoubleArray
): Array<DoubleArray> { //поверим, что тут все гуд
    val size = matrix.size
    val sqdistance = Array(size) {
        DoubleArray(
            matrix[0].size
        )
    }
    for (i in 0 until size) {
        for (j in matrix[i].indices) {
            sqdistance[i][j] = matrix[i][j] * matrix[i][j]
        }
    }

    val esqdistance = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            esqdistance[i][j] =
                max(exp(-sqdistance[i][j]*sqdistance[i][j] / 2*(sigma[i] * sigma[i])), EPSILON) //в оригинале сигма транспонирована, тут надо подумать
        }
    }

    val esqdistanceZd = Array(size) {
        DoubleArray(
            size
        )
    }
    for (i in 0 until size) {
        for (j in 0 until size) {
            if (i == j) {
                esqdistanceZd[i][j] = 0.0
            } else {
                esqdistanceZd[i][j] = esqdistance[i][j]
            }
        }
    }

    val rowSum = DoubleArray(size)
    for (i in 0 until size) {
        for (j in 0 until size) {
            rowSum[i] += esqdistanceZd[i][j]
        }
    }
    val result = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            if (i!=j) {
                result[j][i] = esqdistanceZd[i][j] / rowSum[i]
            }
            else{
                result[j][i]=0.0
            }
        }
    }
    return result
}

private fun pIJSymVar(pIJConditional: Array<DoubleArray>): Array<DoubleArray> {
    val size = pIJConditional.size
    val result = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            if(i!=j){
                result[i][j] = (pIJConditional[i][j] + pIJConditional[j][i]) / (2 * pIJConditional.size)
            }
            else{
                result[i][j] = 0.0
            }

        }

    }
    return result
}

private fun qIJStudentTVar(matrix: Array<DoubleArray>): Array<DoubleArray> {
    val sqeuclideanVar = sqeuclideanVar(matrix)

    val oneOver = Array(sqeuclideanVar.size) {
        DoubleArray(
            sqeuclideanVar[0].size
        )
    }

    for (i in sqeuclideanVar.indices) {
        for (j in sqeuclideanVar[i].indices) {
            if(i!=j){
                oneOver[i][j] = 1 / (sqeuclideanVar[i][j] + 1)
            }
            else{
                oneOver[i][j]=0.0
            }

        }
    }
    val result = Array(oneOver.size) {
        DoubleArray(
            oneOver[0].size
        )
    }
    for (i in oneOver.indices) {
        for (j in oneOver[i].indices) {
            result[i][j] = oneOver[i][j] / sum(oneOver[i])
        }
    }
    return result
}

//    private fun qIJGaussianVar(matrix: Array<DoubleArray>): Array<DoubleArray> {
//        val sqeuclideanVar = sqeuclideanVar(matrix)
//        val result = Array(sqeuclideanVar.size) {
//            DoubleArray(
//                sqeuclideanVar[0].size
//            )
//        }
//        for (i in sqeuclideanVar.indices) {
//            for (j in sqeuclideanVar[i].indices) {
//                result[i][j] = exp(-sqeuclideanVar[i][j]) / sum(exp(-sqeuclideanVar[i]))
//            }
//        }
//        return result
//    }

private fun costVar(
    matrixX: Array<DoubleArray>,
    matrixY: Array<DoubleArray>,
    sigma: DoubleArray,
    lKL: Double,
    lC: Double,
    lR: Double,
    rEps: Double
): DoubleArray {
    val size = matrixX.size

    val pIJConditional = pIJConditionalVar(matrixX, sigma)

    val pIJ = pIJSymVar(pIJConditional)
    val pIJSafe = Array(pIJ.size) {
        DoubleArray(
            pIJ[0].size
        )
    }
    for (i in pIJ.indices) {
        for (j in pIJ[i].indices) {
            pIJSafe[i][j] = kotlin.math.max(pIJ[i][j], EPSILON)
        }
    }

    val qIJ = qIJStudentTVar(matrixY)
    val qIJSafe = Array(qIJ.size) {
        DoubleArray(
            qIJ[0].size
        )
    }
    for (i in qIJ.indices) {
        for (j in qIJ[i].indices) {
            qIJSafe[i][j] = kotlin.math.max(qIJ[i][j], EPSILON)
        }
    }
    //Kullback-Leibler term
    val kl = DoubleArray(pIJ.size)
    for (i in pIJ.indices) {
        for (j in pIJ[i].indices) {
            kl[i] += pIJ[i][j] * ln(pIJSafe[i][j] / qIJSafe[i][j])
        }
    }
    //Compression term
    val compression = DoubleArray(matrixY.size)

    for (i in matrixY.indices) {
        for (j in matrixY[i].indices) {
            compression[i] += matrixY[i][j] * matrixY[i][j]
        }
        compression[i] *= (1 / (2 * size)).toDouble()
    }
    //Repulsion term
    val repulsion = DoubleArray(matrixY.size)

    for (i in matrixY.indices) { //тут походу конвертер ошибся, там нули в диагонали !!
        for (j in matrixY[i].indices) {
            if (i == j) {
                continue
            } else {
                repulsion[i] -= ln(euclideanVar(matrixY)[i][j] + rEps)
            }
        }
        repulsion[i] *= (1 / (2 * size * size)).toDouble()
    }
    //Sum of all terms. почему-то тут размер kl берется но вроде норм
    val cost = DoubleArray(kl.size)
    for (i in kl.indices) {

        cost[i] =
            (lKL / (lKL + lC + lR)) * kl[i] + ((lC / (lKL + lC + lR)) * compression[i]) + ((lR / (lKL + lC + lR)) * repulsion[i])

    }
    return cost
}

//TODO если данный вариант функции работать не будет, то удалить, и выбирать значения рандомно
//@Throws(SigmaTooLowException::class)
//private fun findSigma(
//    matrixX: Array<DoubleArray>,
//    sigma: DoubleArray,
//    size: Int,
//    perplexity: Double,
//    sigmaIters: Int,
//): DoubleArray {
//    //val target = ln(perplexity)
//
//    var matrixP = pIJConditionalVar(matrixX, sigma)
//    for (i in matrixP.indices) {
//        for (j in matrixP[i].indices) {
//            matrixP[i][j] = max(matrixP[i][j], EPSILON)
//        }
//    }
//
//    var entropy = DoubleArray(matrixP.size) { 0.0 }
//    for (i in matrixP.indices) {
//        for (j in matrixP[i].indices) {
//            entropy[i] -= matrixP[i][j] * ln(matrixP[i][j])
//        }
//    }
//
//    val sigmin = DoubleArray(sigma.size)
//    Arrays.fill(sigmin, sqrt(EPSILON))
//
//    val sigmax = DoubleArray(sigma.size)
//    Arrays.fill(sigmax, Double.POSITIVE_INFINITY)
//
//    for (i in 0 until sigmaIters) {
//        val e = entropy.clone()
//
//        for (j in e.indices) {
//            if (java.lang.Double.isNaN(exp(e[j]))) {
//                //throw SigmaTooLowException("Invalid sigmas. The perplexity is probably too low.")
//                println("sigma exception")
//            }
//        }
//
//        //эээ это супер странно. Почему-то в цикле фор условие на выход. причем такое же, какое в его голове
////            if (i == sigmaIters - 1) {
////                break
////            }
//
//        for (j in entropy.indices) {
//            if (entropy[j] < ln(perplexity)) {
//                sigma[j] = (sigma[j] + sigmax[j]) / 2
//            } else {
//                sigma[j] = (sigma[j] + sigmin[j]) / 2
//            }
//        }
//        matrixP = pIJConditionalVar(matrixX, sigma)
//        for (m in matrixP.indices) {
//            for (j in matrixP[i].indices) {
//                matrixP[m][j] = max(matrixP[m][j], EPSILON)
//            }
//        }
//        entropy = DoubleArray(matrixP.size)
//        for (j in matrixP.indices) {
//            for (k in matrixP[j].indices) {
//                entropy[j] -= matrixP[j][k] * ln(matrixP[j][k])
//            }
//        }
//    }
//
//    return sigma
//}


private fun isConverged(epoch: Int, stepsizeOverTime: DoubleArray, tol: Double = 1e-8, windowSize: Int): Boolean {
    if (epoch > windowSize) {
        val maxStepsize = max(Arrays.copyOfRange(stepsizeOverTime, epoch - windowSize, epoch))
        return maxStepsize < tol
    }
    return false
}

private fun updateGradientVertices(
    momentum: Double,
    Yv: Array<DoubleArray>,
    lr: Double,
    gradientVertices: Array<DoubleArray>,
): Array<DoubleArray> {
    val tmpYv = Array(Yv.size) { DoubleArray(Yv[0].size) { 0.0 } }
    for (i in Yv.indices) {
        for (j in Yv[0].indices) {
            tmpYv[i][j] = (momentum * Yv[i][j] - lr * gradientVertices[i][j])
        }
    }
    return tmpYv
}

private fun updateVertices(
    verticesCoords: Array<DoubleArray>,
    Yv: Array<DoubleArray>,
): Array<DoubleArray> {
    val tmpVerticesCoords = Array(verticesCoords.size) { DoubleArray(verticesCoords[0].size) { 0.0 } }
    for (i in verticesCoords.indices) {
        for (j in verticesCoords[0].indices) {
            tmpVerticesCoords[i][j] = Yv[i][j] + verticesCoords[i][j]
        }
    }
    return tmpVerticesCoords
}

//    private fun getCost(){
//
//    }
private fun stepsize(
    Yv: Array<DoubleArray>,
    verticesCoords: Array<DoubleArray>,
    size: Int,
    initial_LR: Double
): Double {
    var sumYv = 0.0
    for (i in Yv.indices) {
        var rowSum = 0.0
        for (j in 0 until Yv[i].size) {
            rowSum += Yv[i][j] * Yv[i][j]
        }
        sumYv += sqrt(rowSum)
    }
    var maxY = Double.NEGATIVE_INFINITY
    var minY = Double.POSITIVE_INFINITY
    for (i in verticesCoords.indices) {
        for (j in verticesCoords[i].indices) {
            if (verticesCoords[i][j] > maxY) {
                maxY = verticesCoords[i][j]
            }
            if (verticesCoords[i][j] < minY) {
                minY = verticesCoords[i][j]
            }
        }
    }
    if ((maxY - minY) == 0.0) {
        return sumYv / (size * initial_LR)
    }
    return sumYv / (size * initial_LR * (maxY - minY))
}

// @Throws(NaNException::class)
private fun findY(
    vertices: Array<DoubleArray>,
    verticesCoords: Array<DoubleArray>,
    sigma: DoubleArray,
    size: Int,
    outputDims: Int,
    nEpochs: Int,

    initial_LR: Double,
    final_LR: Double,
    lr_Switch: Int,
    initStdev: Double,

    initialMomentum: Double,
    finalMomentum: Double,
    momentumSwitch: Int,

    initialLKL: Double,
    finalLKL: Double,
    lKLSwitch: Int,

    initialLC: Double,
    finalLC: Double,
    lCSwitch: Int,

    initialLR: Double,
    finalLR: Double,
    lRSwitch: Int,

    rEps: Double,
    autostop: Double = 0.0,
    windowSize: Int = 10,
): Array<DoubleArray> {
    var tmpInitial_LR = initial_LR
    var tmpInitialLKL = initialLKL
    var tmpInitialLC = initialLC
    var tmpInitialLR = initialLR
    var tmpInitialMomentum = initialMomentum
    var tmpVerticesCoords = verticesCoords
    var tmplRSwitch = lRSwitch
    var tmpMomentumSwitch = momentumSwitch
    var tmplKLSwitch = lKLSwitch
    var tmplCSwitch = lCSwitch
    var tmplr_Switch = lr_Switch
    var Yv = Array(size) {
        DoubleArray(
            outputDims
        )
        { 0.0 }
    }
    var costsVar = costVar(vertices, tmpVerticesCoords, sigma, tmpInitialLKL, tmpInitialLC, tmpInitialLR, rEps)
    var cost = costsVar.sum()
    var gradientVerticesCoords = gradient(cost, tmpVerticesCoords)
    var converged = false
    val stepsizeOverTime = DoubleArray(nEpochs) { 0.0 }
    for (epoch in 0 until nEpochs) {
        if (epoch == tmplRSwitch) {
            tmpInitialLR = finalLR
        }
        if (epoch == tmpMomentumSwitch) {
            tmpInitialMomentum = finalMomentum
        }
        if (epoch == tmplKLSwitch) {
            tmpInitialLKL = finalLKL
        }
        if (epoch == tmplCSwitch) {
            tmpInitialLC = finalLC
        }
        if (epoch == lr_Switch) {
            tmpInitial_LR = final_LR
        }
        if (epoch == 166){
            println("!")
        }
        val Yvcopy = Yv.copyOf()
        val stepsize = stepsize(Yv, tmpVerticesCoords, size, tmpInitial_LR)
        Yv = updateGradientVertices(tmpInitialMomentum, Yvcopy, tmpInitial_LR, gradientVerticesCoords)

        stepsizeOverTime[epoch] = stepsize

        tmpVerticesCoords = updateVertices(tmpVerticesCoords, Yv)

        costsVar = costVar(vertices, tmpVerticesCoords, sigma, tmpInitialLKL, tmpInitialLC, tmpInitialLR, rEps)

        cost = costsVar.sum()

        if (java.lang.Double.isNaN(cost)) {
            println("cost nan")
        }

//        if (autostop != 0.0 && isConverged(epoch, stepsizeOverTime, tol = autostop, windowSize)) {
//            if (epoch < tmplRSwitch) {
//                tmplRSwitch = epoch + 1
//                tmpMomentumSwitch = epoch + 1
//                tmplKLSwitch = epoch + 1
//                tmplCSwitch = epoch + 1
//                tmplr_Switch = epoch + 1
//            } else if (epoch > tmplRSwitch + windowSize) {
//                converged = true
//                break
//            }
//        }
    }
//        if (!converged) {
//            println("Warning: Did not converge!")
//        }
    return tmpVerticesCoords
}


//@Throws(SigmaTooLowException::class, NaNException::class)
fun tsnet(
    vertices: Array<DoubleArray>,
    perplexity: Double = 30.0,
    vertexCoords: Array<DoubleArray>,
    outputDims: Int = 2,
    nEpochs: Int = 1000,
    initial_LR: Double = 10.0,
    final_LR: Double = 4.0,
    lr_Switch: Int,
    initStdev: Double = 1e-4,
    sigmaIters: Int = 50,
    initialMomentum: Double = 0.5,
    finalMomentum: Double = 0.8,
    momentumSwitch: Int = 250,
    initialLKL: Double,
    finalLKL: Double,
    lKLSwitch: Int,
    initialLC: Double,
    finalLC: Double,
    lCSwitch: Int,
    initialLR: Double,
    finalLR: Double,
    lRSwitch: Int,
    rEps: Double = 1.0,
    //randomSeed: Long,
    autostop: Double = 0.0,
    windowSize: Int = 10,
): Array<DoubleArray> {
//        val random = Random(randomSeed)
    val verticesNumber = vertices.size
    val tmpVertexCoords = vertexCoords
//            var Y = vertexCoords
//        if (Y == null){
//
//        }

    val sigmaShared = DoubleArray(verticesNumber)
    for (i in 0 until verticesNumber) {
        sigmaShared[i] = 1*initStdev
    }

     //val resultSigma = findSigma(vertices, sigmaShared, verticesNumber, perplexity, sigmaIters)
    val arrayX = listOf(0.8877868, 0.719826, 0.6496161, 0.07604706, 0.99572206, 0.5173684, 0.5476957, 0.914038, 0.70197463, 0.9344275, 0.26876843, 0.7583832, 0.5694686, 0.3793757, 0.050505042, 0.81860334, 0.5099501, 0.12667817, 0.35858333, 0.23174864, 0.1717661, 0.09181601, 0.8031458, 0.85746044, 0.59837216, 0.7359834, 0.24821913, 0.78177935, 0.24334568, 0.6560409, 0.63474923, 0.6508926, 0.7754333, 0.8773301, 0.84899324, 0.9259963, 0.29843342, 0.6370809, 0.9936, 0.7755677, 0.30031812, 0.12194365, 0.78652906, 0.9416703, 0.1402629, 0.72065157, 0.36217737, 0.39602166, 0.7865537, 0.59102446, 0.093559325, 0.862325, 0.4948461, 0.9028656, 0.48782748, 0.6293899, 0.54457, 0.67088157, 0.3145256, 0.17732465, 0.23284346, 0.8653852, 0.9339095, 0.7800066, 0.93296236, 0.1437251, 0.57570344, 0.29885578, 0.62687504, 0.9342139, 0.36093986, 0.9456436, 0.006687641, 0.8866653, 0.2994938, 0.21389258, 0.21727318, 0.78286356, 0.46346807, 0.8473081, 0.87207294, 0.939841, 0.30431348, 0.7700489, 0.68121755, 0.60265774, 0.33209383, 0.50226, 0.5515162, 0.4688288, 0.46612936, 0.83839446, 0.7202488, 0.20590514, 0.7980565, 0.5152367, 0.025948763, 0.39693534, 0.42241353, 0.20930386)
    val arrayY = listOf(   0.5190474, 0.49463326, 0.040546298, 0.75455016, 0.13642162, 0.746919, 0.22172397, 0.980136, 0.23330867, 0.10406339, 0.17573154, 0.15045297, 0.55304515, 0.8467596, 0.11867458, 0.32903373, 0.08220005, 0.415942, 0.6094675, 0.2532512, 0.14145565, 0.39778012, 0.12669581, 0.6300604, 0.4942264, 0.9591165, 0.3160069, 0.71204656, 0.055157244, 0.8075544, 0.29758126, 0.9338358, 0.20659924, 0.014750004, 0.3918125, 0.3044026, 0.8883903, 0.20698202, 0.06875056, 0.10905433, 0.9119101, 0.08991647, 0.25299704, 0.34231967, 0.47753054, 0.9156098, 0.8175104, 0.73652786, 0.5748825, 0.6020008, 0.35431778, 0.6422682, 0.7684196, 0.016645193, 0.836469, 0.034950018, 0.45083147, 0.8783559, 0.78202266, 0.3001116, 0.42771947, 0.3820411, 0.6003016, 0.5787465, 0.05456972, 0.07650632, 0.9695297, 0.79903495, 0.09730768, 0.71509016, 0.5308475, 0.44671923, 0.6553544, 0.97780097, 0.8216821, 0.6970999, 0.2347765, 0.92712796, 0.13496572, 0.24685109, 0.75632614, 0.35475725, 0.95662385, 0.034765244, 0.36570406, 0.692805, 0.84138566, 0.6437715, 0.013035536, 0.0017493367, 0.56804615, 0.87296957, 0.3362627, 0.7401788, 0.6443292, 0.6792885, 0.99379253, 0.29265207, 0.75369954, 0.38831192
    )
    for (i in 0 until verticesNumber){
        tmpVertexCoords[i][0]=arrayX[i]
        tmpVertexCoords[i][1]=arrayY[i]
    }

    return findY(
        vertices = vertices,
        verticesCoords = tmpVertexCoords,
        sigma = sigmaShared,
        size = verticesNumber,
        outputDims = outputDims,
        nEpochs = nEpochs,
        initial_LR = initial_LR,
        final_LR = final_LR,
        lr_Switch = lr_Switch,
        initStdev = initStdev,
        initialMomentum = initialMomentum,
        finalMomentum = finalMomentum,
        momentumSwitch = momentumSwitch,
        initialLKL = initialLKL,
        finalLKL = finalLKL,
        lKLSwitch = lKLSwitch,
        initialLC = initialLC,
        finalLC = finalLC,
        lCSwitch = lCSwitch,
        initialLR = initialLR,
        finalLR = finalLR,
        lRSwitch = lRSwitch,
        rEps = rEps,
        autostop = autostop,
        windowSize = windowSize,
    )
}

