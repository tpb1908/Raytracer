package raytracer.image

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class ReadableImage(private val image: BufferedImage) {


    companion object {
        fun fromResources(resource: String): ReadableImage {
            return ReadableImage(
                    ImageIO.read(ReadableImage::class.java.getResourceAsStream(resource))
            )
        }
    }

    fun getWidth() = image.width

    fun getHeight() = image.height

    fun readPixel(x: Int, y: Int): ImageColor {
        val color = image.getRGB(x, y)
        val r = (color and 0x00ff0000) shr 16
        val g = (color and 0x0000ff00) shr 8
        val b = (color and 0x000000ff)
        return ImageColor(r, g, b)
    }

}