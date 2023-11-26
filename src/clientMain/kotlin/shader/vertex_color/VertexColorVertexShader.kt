package shader.vertex_color

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.mat.Mat4
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3

class VertexColorVertexShader : DebuggableShader() {
    private val vertexPosition by attribute(::Vec3)
    private val vertexColor by attribute(::Vec3)

    private val mvp by uniform(::Mat4)

    private var outCol by varying(::Vec3)

    init {
        outCol = vertexColor
        val coord by vec4(vertexPosition, 1.0f)
        gl_Position = mvp * coord
    }
}