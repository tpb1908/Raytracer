package raytracer.scene

import raytracer.image.ImageColor
import raytracer.image.ReadableImage

data class Texture(private var image: ReadableImage) {

    companion object {
        fun fromResource(resource: String) = Texture(ReadableImage.fromResources(resource))
    }

    fun colorAtUV(u: Double, v: Double): Color {
        val color = image.readPixel(
                Math.floor(u * image.getWidth()).toInt(),
                Math.floor(v * image.getHeight()).toInt()
        )
        return Color(color.r.toDouble() / ImageColor.MAX,
                color.g.toDouble() / ImageColor.MAX,
                color.b.toDouble() / ImageColor.MAX
        )
    }

}