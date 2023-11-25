package shader

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3

class DomainDistortionVertexShader : DebuggableShader() {
    private val vertexPosition by attribute(::Vec3)

    init {
        val pos by vec4(vertexPosition, 1.0f)
        gl_Position = pos
    }
}
