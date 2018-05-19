package raytracer.scene

data class Color(var r: Double, var g: Double, var b: Double) {

    companion object {
        const val MAX = 1.0
        val BLACK = Color(0.0, 0.0, 0.0)
    }

    operator fun times(num: Double) = Color(r * num, g * num, b * num)

    operator fun times(other: Color) = Color(r * other.r, g * other.g, b * other.b)

    operator fun div(num: Double) = Color(r / num, g / num, b / num)

    operator fun div(other: Color) = Color(r / other.r, g / other.g, b / other.b)

    operator fun plus(other: Color) = Color(r + other.r, g + other.g, b + other.g)

    fun clamped() = Color(
            if (r < 0) 0.0 else if (r > MAX) MAX else r,
            if (g < 0) 0.0 else if (g > MAX) MAX else g,
            if (b < 0) 0.0 else if (b > MAX) MAX else b
    )
}