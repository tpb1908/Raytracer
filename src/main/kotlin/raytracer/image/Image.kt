package raytracer.image

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.Closeable
import java.nio.file.Path
import javax.imageio.ImageIO

class Image(w: Int, h: Int) : Closeable {
    private val image: BufferedImage = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    private val graphics: Graphics2D

    init {
        graphics = image.createGraphics()
    }

    fun plotPixel(x: Int, y: Int, color: ImageColor) {
        graphics.paint = Color(color.r, color.g, color.b)
        graphics.fillRect(x, y, 1, 1)
    }

    fun save(path: Path) {
        ImageIO.write(image, "PNG", path.toFile())
    }

    override fun close() {
        graphics.dispose()
    }
}
