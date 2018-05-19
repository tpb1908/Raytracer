package raytracer.scene

import raytracer.tracer.Ray
import java.util.*

interface SceneObject {

    fun getMaterial(): Material

    fun earliestIntersection(ray: Ray): Optional<Double>

    fun normalAt(point: Vector3): Vector3
}