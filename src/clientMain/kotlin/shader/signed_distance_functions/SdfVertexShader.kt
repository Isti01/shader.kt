package shader.signed_distance_functions

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3

class SdfVertexShader : DebuggableShader() {
    private val vertexPosition by attribute(::Vec3)

    init {
        val pos by vec4(vertexPosition, 1.0f)
        gl_Position = pos
    }
}