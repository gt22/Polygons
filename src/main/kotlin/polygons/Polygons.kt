package polygons

import javafx.animation.AnimationTimer
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import polygons.Styles.Companion.primaryCanvas
import tornadofx.*
import kotlin.math.*

class MainScreen : View() {
    override val root = hbox {
        primaryStage.width = 800.0
        primaryStage.height = 600.0
        addClass(primaryCanvas)
        c = canvas {
            widthProperty().bind(primaryStage.widthProperty())
            heightProperty().bind(primaryStage.heightProperty())
        }
        setOnMouseClicked {
            primaryStage.isFullScreen = !primaryStage.isFullScreen
        }
    }

    private lateinit var c: Canvas


    init {
        PolyDrawer(c).start()
    }
}

const val π = PI
const val τ = 2 * π

typealias Point = Pair<Double, Double>

fun getIthPoint(n: Double, i: Double, φ: Double): Point {
    var ii = i
    if (ii > n) {
        ii -= n
    }
    val θ = ((ii * τ) / n) + φ
    return cos(θ) to sin(θ)
}

fun GraphicsContext.drawRegularPolygon(n: Double, φ: Double) {
    val fn = floor(n).toInt()
    val cn = ceil(n).toInt()
    val k = n - fn
    val points = Array(fn) { getIthPoint(n, it.toDouble() + k, φ) }
    val (xn, yn) = getIthPoint(n, n + k - 1, φ)
    for (i in 0 .. points.lastIndex) {
        val (x1, y1) = points[i]
        strokeLine(x1, y1, xn, yn)
        for(j in 0 .. points.lastIndex) {
            val (x2, y2) = points[j]
                strokeLine(x1, y1, x2, y2)
        }
    }
}


val Double.degrees
    get() = Math.toRadians(this)

val Int.degrees
    get() = toDouble().degrees


fun Double.round(precision: Int): Double {
    val t = Math.pow(10.0, precision.toDouble())
    return Math.round(this * t).toDouble() / t
}


class PolyDrawer(val c: Canvas) : AnimationTimer() {

    var prevTime = System.nanoTime()
    var φ = 0.0
    var n = 1.0
    override fun handle(now: Long) {
        with(Params) {
            if (now - prevTime > animDelay) {
                val dt = (now - prevTime) / tFactor
                prevTime = now
                n += growthSpeed * dt
                φ += rotationSpeed * dt
                with(c.graphicsContext2D) {
                    save()
                    clear()

                    val ox = c.width / 2
                    val oy = c.height / 2
                    val r = c.height * 0.5 * 0.75

                    color = c("#99AAB5")
                    fillText("n = ${n.round(3)}", ox, oy - (r * 1.1))

                    translate(ox, oy)
                    sizeScale(r)
                    lineWidth *= 2

                    strokeCircle(0.0, 0.0, 1.0)

                    color = Color.hsb((φ * 360 / colorFactor) % 360, 1.0, 1.0)

                    drawRegularPolygon(n, φ)

                    restore()
                }
            }
        }
    }

    init {
        c.graphicsContext2D.textAlign = TextAlignment.CENTER
    }

}


class Polygons : App(MainScreen::class, Styles::class)