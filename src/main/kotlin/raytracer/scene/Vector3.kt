package raytracer.scene

data class Vector3(var x: Double, var y: Double, var z: Double) {

    operator fun times(scalar: Double) = Vector3(x * scalar, y * scalar, z * scalar)

    operator fun plus(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector3) = Vector3(x - other.x, y - other.y, z - other.z)

    operator fun unaryMinus() = this.times(-1.0)

    fun dot(other: Vector3) = x * other.x + y * other.y + z * other.z

    private fun norm() = Math.sqrt(this.dot(this))

    fun normalized() = this.times(1 / norm())


    companion object {

        // Linear interpolation
        fun lerp(start: Vector3, end: Vector3, t: Double) = (start * (1 - t)) + (end * t)
    }

}