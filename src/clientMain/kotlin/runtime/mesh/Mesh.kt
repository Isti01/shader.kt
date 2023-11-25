package runtime.mesh

import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext

class Mesh private constructor(private val data: List<Pair<VertexBuffer, WebGLBuffer>>) {
    fun render(gl: WebGLRenderingContext, program: WebGLProgram) {
        gl.useProgram(program)
        for (vertexData in data) {
            val buffer = vertexData.first
            val bytes = vertexData.second
            gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, bytes)
            val attribute = gl.getAttribLocation(program, buffer.name)
            gl.vertexAttribPointer(
                attribute,
                buffer.size,
                buffer.type,
                buffer.normalize,
                buffer.stride,
                buffer.offset
            )
        }
        gl.drawArrays(WebGLRenderingContext.TRIANGLES, 0, data[0].first.itemCount)
    }

    companion object {
        fun create(data: List<VertexBuffer>, gl: WebGLRenderingContext): Mesh? {
            if (data.isEmpty() || !data.all { it.itemCount == data[0].itemCount }) {
                throw IllegalArgumentException("The vertex count must be greater than 0 and equal among buffers")
            }
            val dataWithBuffers = data.map { Pair(it, it.createBuffer(gl)) }
            if (dataWithBuffers.any { it.second == null }) {
                console.log("Failed to allocate buffers")
                dataWithBuffers.forEach { gl.deleteBuffer(it.second) }
                return null
            }
            return Mesh(dataWithBuffers.map { Pair(it.first, it.second!!) })
        }
    }
}