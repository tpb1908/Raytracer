package raytracer.image

data class ImageColor(var r: Int, var g: Int, var b: Int) {

    companion object {
        const val MAX = 0xff
    }

}