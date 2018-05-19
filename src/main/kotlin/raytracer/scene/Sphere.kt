package raytracer.scene

import raytracer.tracer.Ray
import java.util.*

class Sphere(private val center: Vector3, private val radius: Double, private val material: Material) : SceneObject {

    override fun getMaterial(): Material {
        return material
    }

    override fun earliestIntersection(ray: Ray): Optional<Double> {
        val cPrime = ray.origin - center
        val a = ray.direction.dot(ray.direction)
        val b = 2 * cPrime.dot(ray.direction)
        val c = cPrime.dot(cPrime) - radius * radius

        val discriminant = b * b - 4 * a * c
        if (discriminant < 0) return Optional.empty()

        val sqrt = Math.sqrt(discriminant)
        val min = Math.min(
                (-b + sqrt) / (2 * a),
                (-b - sqrt) / (2 * a)
        )

        return if (min > 0) Optional.of(min) else Optional.empty()
    }

    override fun normalAt(point: Vector3) = (point - center).normalized()
}