package polygons

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Paint


fun GraphicsContext.strokeCircle(x: Double, y: Double, r: Double) {
    val R = 2 * r
    strokeOval(x - r, y - r, R, R)
}

fun GraphicsContext.fillCircle(x: Double, y: Double, r: Double) {
    val R = 2 * r
    fillOval(x - r, y - r, R, R)
}

fun GraphicsContext.clear(color: Paint) {
    val b = fill
    fill = color
    fillRect(0.0, 0.0, canvas.width, canvas.height)
    fill = b
}

fun GraphicsContext.clear() {
    clearRect(0.0, 0.0, canvas.width, canvas.height)
}

fun GraphicsContext.sizeScale(s: Double) {
    scale(s, s)
    lineWidth = 1 / s
}

fun GraphicsContext.sizeScale(s: Int) = sizeScale(s.toDouble())

var GraphicsContext.color: Paint?
    get() = if(fill != stroke) null else fill
    set(value) {
        fill = value
        stroke = value
    }