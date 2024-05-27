package model.layout

import java.util.*
import kotlin.math.*

import java.util.Random
import kotlin.random.Random as r

private const val EPSILON = 1e-16
private const val FLOAT_H = 1e-16f

private fun gradient(cost: Double, coords: Array<DoubleArray>): Array<DoubleArray> {
    val gradient = Array(coords.size) { DoubleArray(coords[0].size) { 0.0 } }

    for (i in coords.indices) {
        for (j in coords[i].indices) {
            gradient[i][j] = 2 * (coords[i][j] - cost)
        }
    }
    return gradient
}

private fun dotProduct(a: DoubleArray, b: DoubleArray): Double {
    var result = 0.0
    for (i in a.indices) {
        result += a[i] * b[i]
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
    val ss = Array(size) { DoubleArray(1) }
    for (i in 0 until size) {
        for (j in matrix[i].indices) {
            ss[i][0] += matrix[i][j] * matrix[i][j]
        }
    }
    val result = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            result[i][j] = ss[i][0] + ss[j][0] - 2 * dotProduct(matrix[i], matrix[j])
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
            result[i][j] = kotlin.math.max(sqeuclideanVar[i][j], EPSILON)
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
                exp(-sqdistance[i][j] / (2 * sigma[i] * sigma[i])) //в оригинале сигма транспонирована, тут надо подумать
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

    val rowSum = Array(size) { DoubleArray(1) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            rowSum[i][0] += esqdistanceZd[i][j]
        }
    }
    val result = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            result[i][j] = esqdistanceZd[i][j] / rowSum[i][0]
        }
    }
    return result
}

private fun pIJSymVar(pIJConditional: Array<DoubleArray>): Array<DoubleArray> {
    val size = pIJConditional.size
    val result = Array(size) { DoubleArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            result[i][j] = (pIJConditional[i][j] + pIJConditional[j][i]) / (2 * pIJConditional.size)
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
            oneOver[i][j] = 1 / (sqeuclideanVar[i][j] + 1)
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
//

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
            tmpYv[i][j] = momentum * Yv[i][j] - lr * gradientVertices[i][j]
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

    // vertices
    //sigma
    //velocities
    //cost
    //cost gradient
    //stepsize
    //update step
    //gradient step
    //cost func
    //stepsize over time
//        val Yv = Array(Y!!.size) {
//            DoubleArray(
//                Y!![0].size
//            )
//        }
//       val sigmaShared = DoubleArray(sigma.size)
//       System.arraycopy(sigma, 0, sigmaShared, 0, sigmaShared.size)

    var Yv = Array(size) {
        DoubleArray(
            outputDims
        )
        { 0.0 }
    }

    var costsVar = costVar(vertices, tmpVerticesCoords, sigma, tmpInitialLKL, tmpInitialLC, tmpInitialLR, rEps)
    var cost = costsVar.sum()

    var gradientVerticesCoords = gradient(cost, tmpVerticesCoords)

    //var stepsize = stepsize(Yv,verticesCoords,size,initial_LR)
//        val XShared = Array(matrixXShared.size) {
//            DoubleArray(
//                matrixXShared[0].size
//            )
//        }

//        for (i in matrixXShared.indices) {
//            System.arraycopy(matrixXShared[i], 0, XShared[i], 0, matrixXShared[i].size)
//        }

//        if (Y == null) {
//            val random = Random()
//            Y = Array(size) { DoubleArray(outputDims) }
//            for (i in 0 until size) {
//                for (j in 0 until outputDims) {
//                    Y[i][j] = random.nextGaussian() * initStdev
//                }
//            }
//        }
//        val verticesCoords = Array(Y.size) {
//            DoubleArray(
//                Y[0].size
//            )
//        }
//        findSigma(vertices, sigma, size, perplexity, sigmaIters, verbose)
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
        val stepsize = stepsize(Yv, tmpVerticesCoords, size, tmpInitial_LR)
        Yv = updateGradientVertices(tmpInitialMomentum, Yv, tmpInitial_LR, gradientVerticesCoords)

        stepsizeOverTime[epoch] = stepsize

        tmpVerticesCoords = updateVertices(verticesCoords, Yv)

        costsVar = costVar(vertices, tmpVerticesCoords, sigma, tmpInitialLKL, tmpInitialLC, tmpInitialLR, rEps)

        cost = costsVar.sum()

        if (java.lang.Double.isNaN(cost)) {
            //throw NaNException("Encountered NaN for cost.")
        }

//            if (verbose) {
//                if (autostop && epoch >= windowSize) {
//                    val dLastPeriod = Arrays.copyOfRange(stepsizeOverTime, epoch - windowSize, epoch)
//                    val maxStepsize = max(dLastPeriod)
//                    System.out.printf(
//                        "Epoch: %d. Cost: %.6f. Max step size of last %d: %.2e%n",
//                        epoch + 1,
//                        c,
//                        windowSize,
//                        maxStepsize
//                    )
//                } else {
//                    System.out.printf("Epoch: %d. Cost: %.6f.%n", epoch + 1, c)
//                }
//            }


        if (autostop != 0.0 && isConverged(epoch, stepsizeOverTime, tol = autostop, windowSize)) {
            if (epoch < tmplRSwitch) {
                tmplRSwitch = epoch + 1
                tmpMomentumSwitch = epoch + 1
                tmplKLSwitch = epoch + 1
                tmplCSwitch = epoch + 1
                tmplr_Switch = epoch + 1
            } else if (epoch > tmplRSwitch + windowSize) {
                converged = true
                break
            }
        }
    }
//        if (!converged) {
//            println("Warning: Did not converge!")
//        }
    return verticesCoords
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
    var tmpVertexCoords = vertexCoords
//            var Y = vertexCoords
//        if (Y == null){
//
//        }

    val sigmaShared = DoubleArray(verticesNumber)
    for (i in 0 until verticesNumber) {
        sigmaShared[i] = r.nextDouble()
    }

    //val resultSigma = findSigma(vertices, sigmaShared, verticesNumber, perplexity, sigmaIters)

    tmpVertexCoords = Array(verticesNumber) { DoubleArray(outputDims) }
    for (i in 0 until verticesNumber) {
        for (j in 0 until outputDims) {
            tmpVertexCoords[i][j] = Random().nextGaussian() * initStdev
        }
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

