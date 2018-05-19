package raytracer.tracer

import raytracer.scene.SceneObject
import raytracer.scene.Vector3

data class RayCastHit(val obj: SceneObject, val t: Double, val normal: Vector3)