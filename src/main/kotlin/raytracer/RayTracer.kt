package raytracer

import raytracer.image.Image
import raytracer.image.ImageColor
import raytracer.scene.*
import raytracer.tracer.RayTracer
import java.io.IOException
import java.nio.file.Paths
import java.util.*


object RayTracer {
    private const val W = 1920
    private const val H = 1080

    private val EARTH_TEXTURE = readTexture("/earth.jpg")

    private val SCENE = Scene(
            Vector3(0.0, 0.0, 2.0),
            ImagePlane(
                    Vector3(-1.92, 1.08, -0.5),
                    Vector3(1.92, 1.08, -0.5),
                    Vector3(-1.92, -1.08, -0.5),
                    Vector3(1.92, -1.08, -0.5)
            ),
            Color(0.5, 0.5, 0.5),
            Arrays.asList(
                    Light(
                            Vector3(-5.0, 1.0, 0.5),
                            Color(0.8, 0.8, 0.8),
                            Color(0.8, 0.8, 0.8)
                    ),
                    Light(
                            Vector3(5.0, -1.0, 0.5),
                            Color(0.7, 0.7, 0.7),
                            Color(0.8, 0.8, 0.8)
                    )
            ),
            Arrays.asList(
                    Sphere(
                            Vector3(0.0, 0.0, -1.2),
                            0.4,
                            Material(
                                    Color(0.2, 0.1, 0.1),
                                    Color(0.4, 0.1, 0.1),
                                    Color(0.7, 0.7, 0.7),
                                    Color(0.9, 0.5, 0.5),
                                    100,
                                    EARTH_TEXTURE
                            )
                    ),
                    Sphere(
                            Vector3(-1.0, 0.0, -0.8),
                            0.2,
                            Material(
                                    Color(0.1, 0.2, 0.1),
                                    Color(0.5, 0.9, 0.5),
                                    Color(0.7, 0.7, 0.7),
                                    Color(0.3, 0.5, 0.2),
                                    25,
                                    null
                            )
                    ),
                    Sphere(
                            Vector3(1.0, 0.0, -0.8),
                            0.2,
                            Material(
                                    Color(0.1, 0.1, 0.2),
                                    Color(0.5, 0.5, 0.9),
                                    Color(0.7, 0.7, 0.7),
                                    Color(0.2, 0.3, 0.5),
                                    50, null
                            )
                    ),
                    Sphere(
                            Vector3(0.0, 0.7, -0.8),
                            0.2,
                            Material(
                                    Color(0.1, 0.1, 0.2),
                                    Color(0.9, 0.5, 0.5),
                                    Color(0.7, 0.7, 0.7),
                                    Color(0.5, 0.2, 0.3),
                                    50,
                                    EARTH_TEXTURE
                            )
                    ),
                    Sphere(
                            Vector3(0.0, -0.7, -0.8),
                            0.2,
                            Material(
                                    Color(0.1, 0.1, 0.2),
                                    Color(0.9, 0.5, 0.9),
                                    Color(0.7, 0.7, 0.7),
                                    Color(0.5, 0.2, 0.5),
                                    50, null
                            )
                    )
            )
    )

    private fun readTexture(filename: String): Texture {
        try {
            return Texture.fromResource(filename)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val tracer = RayTracer(SCENE, W, H)

        Image(W, H).use({ image ->
            for (x in 0 until W)
                for (y in 0 until H) {
                    val color = tracer.tracedValueAtPixel(x, y)
                    image.plotPixel(x, y, colorToImageColor(color))
                }

            image.save(Paths.get("test.png"))
        })
    }

    private fun colorToImageColor(color: Color): ImageColor {
        return ImageColor(
                (color.r * ImageColor.MAX).toInt(),
                (color.g * ImageColor.MAX).toInt(),
                (color.b * ImageColor.MAX).toInt()
        )
    }
}