package runtime.mesh

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLRenderingContext

data class VertexBuffer(
    val data: ArrayBuffer,
    val itemCount: Int,
    val name: String,
    val size: Int,
    val type: Int,
    val stride: Int = 0,
    val offset: Int = 0,
    val normalize: Boolean = false,
) {
    fun createBuffer(gl: WebGLRenderingContext): WebGLBuffer? {
        val buffer = gl.createBuffer()
        if (buffer == null) {
            console.log("Failed to create buffer")
            return null
        }
        gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, buffer)
        gl.bufferData(WebGLRenderingContext.ARRAY_BUFFER, data, WebGLRenderingContext.STATIC_DRAW)

        return buffer
    }
}