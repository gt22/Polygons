package polygons

import javafx.util.Duration
import tornadofx.millis
import tornadofx.seconds

fun Duration.toNanos(): Double {
    return toMillis() * 1e6.toInt()
}

object Params {

    val tFactor = 10.millis.toNanos()
    val animDelay = tFactor / 10
    const val rotationSpeed = 0.015
    const val growthSpeed = rotationSpeed / τ
    const val colorPeriod = 5 //Seconds
    val colorFactor = factorPerSecond() * colorPeriod
}

fun factorPerSecond() = with(Params) { rotationSpeed * 1.seconds.toNanos() / tFactor }