package raytracer.tracer

import raytracer.scene.*

class RayTracer(val scene: Scene, private val w: Int, private val h: Int) {


    companion object {
        const val NUM_BOUNCES = 5
        const val NUM_SAMPLES_PER_PIXEL_PER_DIRECTION = 2
        const val NUM_SAMPLES_PER_PIXEL = NUM_SAMPLES_PER_PIXEL_PER_DIRECTION *
                NUM_SAMPLES_PER_PIXEL_PER_DIRECTION
    }

    fun tracedValueAtPixel(x: Int, y: Int): Color {
        val xt = x.toDouble() / w
        val yt = (h.toDouble() - y - 1) / h

        val dx = 1.0 / (w * NUM_SAMPLES_PER_PIXEL_PER_DIRECTION)
        val dy = 1.0 / (h * NUM_SAMPLES_PER_PIXEL_PER_DIRECTION)

        var color = Color.BLACK
        for (xi in 0 until NUM_SAMPLES_PER_PIXEL_PER_DIRECTION) {
            for (yi in 0 until NUM_SAMPLES_PER_PIXEL_PER_DIRECTION) {
                color += tracedValueAtPositionOnImagePlane(
                        xt + dx * xi,
                        yt + dy * yi
                )
            }

        }
        return (color / NUM_SAMPLES_PER_PIXEL.toDouble()).clamped()
    }

    private fun tracedValueAtPositionOnImagePlane(xt: Double, yt: Double): Color {
        val top = Vector3.lerp(
                scene.imagePlane.topLeft,
                scene.imagePlane.topRight,
                xt
        )

        val bottom = Vector3.lerp(
                scene.imagePlane.bottomLeft,
                scene.imagePlane.bottomRight,
                xt
        )

        val point = Vector3.lerp(bottom, top, yt)
        val ray = Ray(
                point,
                point.minus(scene.camera)
        )

        return colorFromAnyObjectHit(NUM_BOUNCES, ray).clamped()
    }

    private fun colorFromAnyObjectHit(numBounces: Int, ray: Ray): Color {
        return scene.objects.map { obj ->
            obj.earliestIntersection(ray)
                    .map { t ->
                        RayCastHit(
                                obj,
                                t.toDouble(),
                                obj.normalAt(ray.at(t))
                        )
                    }
        }
                .filter { it.isPresent }
                .map { it.get() }
                .minBy { it.t }?.let { hit ->
                    val point = ray.at(hit.t)
                    val view = ray.direction.unaryMinus().normalized()

                    var color = phongLightingAtPoint(
                            scene,
                            hit.obj,
                            point,
                            hit.normal,
                            view
                    )

                    if (numBounces > 0) {
                        val reflection = hit.normal * view.dot(hit.normal) * 2.0 - view

                        val reflectedColor = colorFromAnyObjectHit(
                                numBounces - 1,
                                Ray(point, reflection)
                        )
                        color += reflectedColor * hit.obj.getMaterial().kReflection
                    }

                    color
                } ?: Color.BLACK


    }

    private fun phongLightingAtPoint(scene: Scene,
                                     obj: SceneObject,
                                     point: Vector3,
                                     normal: Vector3,
                                     view: Vector3): Color {
        val material = obj.getMaterial()

        val lightContributions = scene
                .lights
                .filter { light -> (light.position - point).dot(normal) > 0 }
                .filter { light -> !isPointInShadowFromLight(scene, obj, point, light) }
                .map { light ->
                    val l = (light.position - point).normalized()
                    val r = (normal * (l.dot(normal) * 2.0)) - l

                    val diffuse = light.intensityDiffuse *
                            material.getDiffuseColor(normal) *
                            l.dot(normal)

                    val specular = light.intensitySpecular *
                            material.kSpecular *
                            Math.pow(r.dot(view), material.alpha.toDouble())

                    diffuse + specular
                }.fold(Color.BLACK, Color::plus)

        val ambient = material.kAmbient * scene.ambientLight

        return ambient + lightContributions
    }

    private fun isPointInShadowFromLight(scene: Scene,
                                         objectToExclude: SceneObject,
                                         point: Vector3,
                                         light: Light): Boolean {
        val direction = light.position.minus(point)
        val shadowRay = Ray(point, direction)

        return scene.objects.filter { it !== objectToExclude }
                .map {
                    it.earliestIntersection(shadowRay).map { t ->
                        RayCastHit(
                                it,
                                t.toDouble(),
                                Vector3(0.0, 0.0, 0.0)
                        )
                    }
                }
                .any { it.isPresent && it.get().t <= 1 }
    }

}