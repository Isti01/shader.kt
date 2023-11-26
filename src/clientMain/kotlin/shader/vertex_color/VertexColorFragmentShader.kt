package shader.vertex_color

import com.salakheev.shaderbuilderkt.builder.debug.DebuggableShader
import com.salakheev.shaderbuilderkt.builder.types.vec.Vec3

class VertexColorFragmentShader : DebuggableShader() {
    private val outCol by varying(::Vec3)

    init {
        val color by vec4(outCol, 1.0f)
        gl_FragColor = color
    }
}