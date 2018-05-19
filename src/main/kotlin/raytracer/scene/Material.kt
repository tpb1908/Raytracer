package raytracer.scene

data class Material(var kAmbient: Color,
                    private var kDiffuse: Color,
                    var kSpecular: Color,
                    var kReflection: Color,
                    var alpha: Int,
                    private var texture: Texture?) {

    fun getDiffuseColor(normal: Vector3): Color {
        val u = 0.5 + Math.atan2(normal.z, normal.x) / (2 * Math.PI)
        val v = 0.5 - Math.asin(normal.y) / Math.PI
        return texture?.colorAtUV(u, v) ?: kDiffuse
    }

}