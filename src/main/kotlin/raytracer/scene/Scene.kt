package raytracer.scene

data class Scene(var camera: Vector3,
                 var imagePlane: ImagePlane,
                 var ambientLight: Color,
                 var lights: List<Light>,
                 var objects: List<SceneObject>)