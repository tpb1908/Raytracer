package raytracer.tracer

import raytracer.scene.Vector3

data class Ray(var origin: Vector3, var direction: Vector3) {

    fun at(t: Double) = origin + direction * t

}